package ru.jegensomme.citingvkbot.to

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Callback API event type
 *
 * @see <a href="https://vk.com/dev/groups_event</a>
 */
enum class EventType {
    @JsonProperty("confirmation")
    CONFIRMATION,

    @JsonProperty("message_new")
    MESSAGE_NEW
}