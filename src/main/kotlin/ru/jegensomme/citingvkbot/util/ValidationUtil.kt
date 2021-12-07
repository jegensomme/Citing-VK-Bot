package ru.jegensomme.citingvkbot.util

import org.springframework.http.ResponseEntity
import ru.jegensomme.citingvkbot.config.CallbackProperties
import ru.jegensomme.citingvkbot.error.SecretMismatchException
import ru.jegensomme.citingvkbot.error.SendMessageException
import ru.jegensomme.citingvkbot.to.Callback
import ru.jegensomme.citingvkbot.to.ErrorResponse

fun validateSecretKey(callback: Callback, properties: CallbackProperties) {
    if (callback.secret != properties.secret) throw SecretMismatchException()
}

fun validateSendResponse(sendResponse: ResponseEntity<Map<*, *>>) {
    sendResponse.body?.get("error")?.run { throw SendMessageException(convert(ErrorResponse::class.java)) }
}