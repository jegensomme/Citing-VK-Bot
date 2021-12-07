package ru.jegensomme.citingvkbot.to

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Callback API event
 *
 * @see <a href="https://vk.com/dev/groups_events">https://vk.com/dev/groups_events</a>
 */
data class Callback (
    val type: EventType,

    @JsonProperty("object")
    val obj: Map<String, Any>?,

    @JsonProperty("group_id")
    val groupId: Long,

    @JsonProperty("event_id")
    val eventId: String,

    val secret: String
)
