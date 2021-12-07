package ru.jegensomme.citingvkbot.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import ru.jegensomme.citingvkbot.util.setMapper

@Configuration
class ApplicationConfig {
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

    @Autowired
    fun storeObjectMapper(objectMapper: ObjectMapper) {
        setMapper(objectMapper)
    }
}