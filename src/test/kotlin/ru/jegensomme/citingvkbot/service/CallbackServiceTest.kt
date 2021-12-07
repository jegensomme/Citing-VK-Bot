package ru.jegensomme.citingvkbot.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate
import ru.jegensomme.citingvkbot.AbstractCallbackTest
import ru.jegensomme.citingvkbot.TestApplicationConfig.Companion.PROPERTIES
import ru.jegensomme.citingvkbot.error.SecretMismatchException
import ru.jegensomme.citingvkbot.error.SendMessageException

internal class CallbackServiceTest(
    @Autowired private val service: CallbackService,
    @Autowired restTemplate: RestTemplate,
) : AbstractCallbackTest(restTemplate)
{
    @Test
    fun confirm() {
        assertEquals(service.confirm(CONFIRMATION), PROPERTIES.confirmation)
    }

    @Test
    fun reply() {
        prepareMockServer(SEND_MESSAGE, HttpStatus.OK, emptyMap<String, Any>())
        assertEquals(service.reply(MESSAGE_NEW), "ok")
        mockServer.verify()
    }

    @Test
    fun replyWithInvalidSecret() {
        assertThrows(SecretMismatchException::class.java) { service.reply(CALLBACK_WITH_INVALID_SECRET) }
    }

    @Test
    fun replyWithErrorResponse() {
        prepareMockServer(SEND_MESSAGE, HttpStatus.OK, mapOf("error" to ERROR_RESPONSE))
        assertThrows(SendMessageException::class.java) { service.reply(MESSAGE_NEW) }
        mockServer.verify()
    }
}