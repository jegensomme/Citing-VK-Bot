package ru.jegensomme.citingvkbot.web

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.client.RestTemplate
import ru.jegensomme.citingvkbot.AbstractCallbackTest
import ru.jegensomme.citingvkbot.TestApplicationConfig.Companion.PROPERTIES
import ru.jegensomme.citingvkbot.util.writeValue

@AutoConfigureMockMvc
internal class CallbackControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired restTemplate: RestTemplate,
) : AbstractCallbackTest(restTemplate)
{
    @Test
    fun handleConfirmation() {
        perform(MockMvcRequestBuilders.post("/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValue(CONFIRMATION)))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect { assertEquals(PROPERTIES.confirmation, it.response.contentAsString) }
    }

    @Test
    fun handleMessageNew() {
        prepareMockServer(SEND_MESSAGE, HttpStatus.OK, emptyMap<String, Any>())
        perform(MockMvcRequestBuilders.post("/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValue(MESSAGE_NEW)))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect { assertEquals("ok", it.response.contentAsString) }
        mockServer.verify()
    }

    @Test
    fun handleWithInvalidSecret() {
        perform(MockMvcRequestBuilders.post("/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValue(CALLBACK_WITH_INVALID_SECRET)))
            .andDo(print())
            .andExpect(status().isForbidden)
    }

    @Test
    fun handleWithErrorResponse() {
        prepareMockServer(SEND_MESSAGE, HttpStatus.OK, mapOf("error" to ERROR_RESPONSE))
        perform(MockMvcRequestBuilders.post("/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValue(MESSAGE_NEW)))
            .andDo(print())
            .andExpect(status().isInternalServerError)
        mockServer.verify()
    }

    @Throws(Exception::class)
    private fun perform(builder: MockHttpServletRequestBuilder): ResultActions {
        return mockMvc.perform(builder)
    }
}