package ru.jegensomme.citingvkbot.to

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Class describing a private message
 *
 * @see <a href="https://vk.com/dev/objects/message</a>
 */
data class NewMessage(
    val id: Long,

    @JsonProperty("date")
    val date: Long,

    @JsonProperty("from_id")
    val fromId: Long,

    @JsonProperty("peer_id")
    val peerId: Long,

    val text: String,

    @JsonProperty("random_id")
    val randomId: Int,

    @JsonProperty("conversation_message_id")
    val conversationMessageId: Long
)
