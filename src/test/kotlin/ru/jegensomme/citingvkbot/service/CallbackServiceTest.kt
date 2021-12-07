package ru.jegensomme.citingvkbot.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate
import ru.jegensomme.citingvkbot.AbstractCallbackTest
import ru.jegensomme.citingvkbot.error.SecretMismatchException
import ru.jegensomme.citingvkbot.error.SendMessageException

internal class CallbackServiceTest(
    @Autowired private val service: CallbackService,
    @Autowired restTemplate: RestTemplate,
) : AbstractCallbackTest(restTemplate)
{
    @Test
    fun confirm() {
        assertEquals(service.confirm(confirmation), properties.confirmation)
    }

    @Test
    fun reply() {
        prepareMockServer(sendMessage, HttpStatus.OK, emptyMap<String, Any>())
        assertEquals(service.reply(messageNew), "ok")
        mockServer.verify()
    }

    @Test
    fun replyWithInvalidSecret() {
        assertThrows(SecretMismatchException::class.java) { service.reply(callbackWithInvalidSecret) }
    }

    @Test
    fun replyWithErrorResponse() {
        prepareMockServer(sendMessage, HttpStatus.OK, mapOf("error" to errorResponse))
        assertThrows(SendMessageException::class.java) { service.reply(messageNew) }
        mockServer.verify()
    }
}