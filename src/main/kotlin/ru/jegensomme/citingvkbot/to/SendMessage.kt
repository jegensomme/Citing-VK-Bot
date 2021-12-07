package ru.jegensomme.citingvkbot.to

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Class describing the parameters for sending a response message
 *
 * @see <a href="https://vk.com/dev/messages.send</a>
 */
data class SendMessage(
    val message: String,

    @JsonProperty("peer_id")
    val peerId: Long,

    @JsonProperty("access_token")
    val accessToken: String,

    @JsonProperty("v")
    val apiVersion: String,

    @JsonProperty("random_id")
    val randomId: Int,

    @JsonProperty("group_id")
    val groupId: Long,
)
