package ru.jegensomme.citingvkbot.web

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.jegensomme.citingvkbot.to.Callback
import ru.jegensomme.citingvkbot.to.EventType
import ru.jegensomme.citingvkbot.service.CallbackService

@RestController
@RequestMapping(consumes = [ MediaType.APPLICATION_JSON_VALUE ])
class CallbackController(
    private val service: CallbackService
) {
    @PostMapping("/")
    fun handle(@RequestBody callback: Callback): ResponseEntity<*> = when (callback.type) {
        EventType.CONFIRMATION -> {
            LOG.info("Handle confirmation event=${callback}")
            ResponseEntity.ok(service.confirm(callback))
        }
        EventType.MESSAGE_NEW -> {
            LOG.info("Handle message_new event=${callback}")
            ResponseEntity.ok(service.reply(callback))
        }
    }

    companion object {
        @JvmField val LOG: Logger = LoggerFactory.getLogger(CallbackController::class.java)
    }
}