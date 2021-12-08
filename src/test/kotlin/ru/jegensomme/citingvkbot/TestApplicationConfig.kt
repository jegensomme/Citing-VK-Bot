package ru.jegensomme.citingvkbot

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import ru.jegensomme.citingvkbot.config.CallbackProperties

@TestConfiguration
@Profile("test")
class TestApplicationConfig {

    @Bean
    fun callbackProperties(): CallbackProperties {
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