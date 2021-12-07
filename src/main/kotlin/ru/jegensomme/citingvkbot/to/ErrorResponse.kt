package ru.jegensomme.citingvkbot.to

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * VK API methods error
 *
 * @see <a href="https://vk.com/dev/errors</a>
 */
data class ErrorResponse(
    @JsonProperty("error_code")
    val code: Int,

    @JsonProperty("error_msg")
    val message: String
)
