package ru.jegensomme.citingvkbot

import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.client.ExpectedCount
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
import org.springframework.web.client.RestTemplate
import ru.jegensomme.citingvkbot.TestApplicationConfig.Companion.PROPERTIES
import ru.jegensomme.citingvkbot.to.Callback
import ru.jegensomme.citingvkbot.to.ErrorResponse
import ru.jegensomme.citingvkbot.to.EventType
import ru.jegensomme.citingvkbot.to.SendMessage
import ru.jegensomme.citingvkbot.util.CallbackMethod.MESSAGE_SEND
import ru.jegensomme.citingvkbot.util.toParametersMap
import ru.jegensomme.citingvkbot.util.writeValue
import java.time.Instant

@SpringBootTest
@ActiveProfiles("test")
@Import(TestApplicationConfig::class)
abstract class AbstractCallbackTest(
    private val restTemplate: RestTemplate,
) {
    protected lateinit var mockServer: MockRestServiceServer

    @BeforeEach
    fun init() {
        mockServer = MockRestServiceServer.createServer(restTemplate)
    }

    fun prepareMockServer(sendMessage: SendMessage, status: HttpStatus, body: Any) {
        val uri = MESSAGE_SEND.createUri(sendMessage.toParametersMap())
        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(uri))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
            .andRespond(MockRestResponseCreators.withStatus(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(writeValue(body)))
    }

    protected companion object TestData {
        @JvmStatic
        protected val CONFIRMATION: Callback

        @JvmStatic
        protected val MESSAGE_NEW: Callback

        @JvmStatic
        protected val CALLBACK_WITH_INVALID_SECRET: Callback

        @JvmStatic
        protected val SEND_MESSAGE: SendMessage

        @JvmStatic
        protected val ERROR_RESPONSE = ErrorResponse(5, "")

        init {
            val messageInfo: Map<String, Any> = mapOf(
                "id" to 0,
                "date" to Instant.now().epochSecond,
                "from_id" to 0,
                "peer_id" to 1,
                "text" to "text",
                "random_id" to 0,
                "conversation_message_id" to 0
            )
            val messageNewObject = mapOf("message" to messageInfo, "client_info" to mapOf())
            CONFIRMATION = Callback(EventType.CONFIRMATION, null, 0, "", "secret" )
            MESSAGE_NEW = Callback(EventType.MESSAGE_NEW, messageNewObject, 0, "", "secret" )
            CALLBACK_WITH_INVALID_SECRET = Callback(EventType.MESSAGE_NEW, messageNewObject, 0, "", "invalid" )
            SEND_MESSAGE = SendMessage("Вы сказали: text", 1, PROPERTIES.accessToken, PROPERTIES.apiVersion, messageNewObject.hashCode(), MESSAGE_NEW.groupId)
        }
    }
}