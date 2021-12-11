package ru.jegensomme.citingvkbot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import javax.validation.constraints.NotBlank

@Component
@PropertySource(value = [ "file:/\${APP_ROOT}/config/callback.properties" ])
@ConfigurationProperties(prefix = "callback")
@Profile("production")
class CallbackProperties {
    @NotBlank lateinit var accessToken: String
    @NotBlank lateinit var confirmation: String
    @NotBlank lateinit var secret: String
    val apiVersion: String = "5.131"
}