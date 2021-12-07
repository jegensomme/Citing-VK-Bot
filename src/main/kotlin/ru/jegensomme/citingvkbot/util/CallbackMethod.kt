package ru.jegensomme.citingvkbot.util

import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

enum class CallbackMethod(private val method: String) {
    MESSAGE_SEND("https://api.vk.com/method/messages.send");

    fun createUri(params: LinkedMultiValueMap<String, String>): URI {
        return ServletUriComponentsBuilder
            .fromHttpUrl(method)
            .queryParams(params)
            .build().toUri()
    }
}