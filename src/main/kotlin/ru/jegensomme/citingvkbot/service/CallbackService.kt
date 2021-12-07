package ru.jegensomme.citingvkbot.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.jegensomme.citingvkbot.config.CallbackProperties
import ru.jegensomme.citingvkbot.error.IllegalRequestDataException
import ru.jegensomme.citingvkbot.to.Callback
import ru.jegensomme.citingvkbot.to.NewMessage
import ru.jegensomme.citingvkbot.to.SendMessage
import ru.jegensomme.citingvkbot.util.*
import ru.jegensomme.citingvkbot.util.CallbackMethod.MESSAGE_SEND
import kotlin.math.min

private const val MAX_MESSAGE_LENGTH = 4096

@Service
class CallbackService(
    private val restTemplate: RestTemplate,
    private val properties: CallbackProperties
) {
    fun confirm(callback: Callback): String {
        validateSecretKey(callback, properties)
        return properties.confirmation
    }

    fun reply(callback: Callback): String {
        validateSecretKey(callback, properties)
        val newMessage = parseNewMessage(callback.obj)
        val sendMessages = createReplyMessage(newMessage, callback).split()
        sendMessages.forEach { send(it) }
        return "ok"
    }

    private fun parseNewMessage(obj: Map<*, *>?): NewMessage {
        val messageInfo = obj?.get("message") as? Map<*, *> ?: throw IllegalRequestDataException()
        return messageInfo.convert(NewMessage::class.java)
    }

    private fun createReplyMessage(newMessage: NewMessage, callback: Callback): SendMessage {
        return SendMessage(
            "Вы сказали: ${newMessage.text}",
            newMessage.peerId,
            properties.accessToken,
            properties.apiVersion,
            callback.obj.hashCode(),
            callback.groupId
        )
    }

    private fun send(sendMessage: SendMessage) {
        val uri = MESSAGE_SEND.createUri(sendMessage.toParametersMap())
        validateSendResponse(restTemplate.postForEntity(uri, null, Map::class.java))
    }

    private fun SendMessage.split(): Array<SendMessage> {
        if (message.length <= MAX_MESSAGE_LENGTH) return arrayOf(this)
        val size = message.length / MAX_MESSAGE_LENGTH + 1
        val result = arrayOfNulls<SendMessage>(size)
        for (i in 0 until size) {
            val sub = message.substring(i * MAX_MESSAGE_LENGTH until min((i + 1) * MAX_MESSAGE_LENGTH, message.length))
            result[i] = SendMessage(sub, peerId, accessToken, apiVersion, 31 * randomId + (i to sub).hashCode(), groupId)
        }
        return result.requireNoNulls()
    }
}