package ru.jegensomme.citingvkbot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import ru.jegensomme.citingvkbot.config.CallbackProperties

@TestConfiguration
class TestApplicationConfig {

    @Bean
    fun callbackProperties(@Autowired restTemplate: RestTemplate): CallbackProperties {
        return PROPERTIES
    }

    companion object {
        @JvmStatic val PROPERTIES = CallbackProperties()

        init {
            PROPERTIES.accessToken = "access_token"
            PROPERTIES.confirmation = "confirmation"
            PROPERTIES.secret = "secret"
        }
    }
}