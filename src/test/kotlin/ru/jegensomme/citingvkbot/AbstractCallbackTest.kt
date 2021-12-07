package ru.jegensomme.citingvkbot

import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.client.ExpectedCount
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
import org.springframework.web.client.RestTemplate
import ru.jegensomme.citingvkbot.config.CallbackProperties
import ru.jegensomme.citingvkbot.service.CallbackService
import ru.jegensomme.citingvkbot.to.Callback
import ru.jegensomme.citingvkbot.to.ErrorResponse
import ru.jegensomme.citingvkbot.to.EventType
import ru.jegensomme.citingvkbot.to.SendMessage
import ru.jegensomme.citingvkbot.util.CallbackMethod.MESSAGE_SEND
import ru.jegensomme.citingvkbot.util.toParametersMap
import ru.jegensomme.citingvkbot.util.writeValue
import java.time.Instant

@SpringBootTest
@Import(AbstractCallbackTest.TestApplicationConfig::class)
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

    @TestConfiguration
    class TestApplicationConfig {
        @Bean
        fun callbackService(@Autowired restTemplate: RestTemplate): CallbackService {
            return CallbackService(restTemplate, properties)
        }
    }

    protected companion object TestData {
        @JvmStatic
        protected val properties = CallbackProperties()

        @JvmStatic
        protected val confirmation: Callback

        @JvmStatic
        protected val messageNew: Callback

        @JvmStatic
        protected val callbackWithInvalidSecret: Callback

        @JvmStatic
        protected val sendMessage: SendMessage

        @JvmStatic
        protected val errorResponse = ErrorResponse(5, "")

        init {
            properties.accessToken = "access_token"
            properties.confirmation = "confirmation"
            properties.secret = "secret"
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
            confirmation = Callback(EventType.CONFIRMATION, null, 0, "", "secret" )
            messageNew = Callback(EventType.MESSAGE_NEW, messageNewObject, 0, "", "secret" )
            callbackWithInvalidSecret = Callback(EventType.MESSAGE_NEW, messageNewObject, 0, "", "invalid" )
            sendMessage = SendMessage("Вы сказали: text", 1, properties.accessToken, properties.apiVersion, messageNewObject.hashCode(), messageNew.groupId)
        }
    }
}