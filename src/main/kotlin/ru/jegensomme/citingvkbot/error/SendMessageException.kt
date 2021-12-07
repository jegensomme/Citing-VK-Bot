package ru.jegensomme.citingvkbot.error

import org.springframework.http.HttpStatus
import ru.jegensomme.citingvkbot.to.ErrorResponse

class SendMessageException(error: ErrorResponse)
    : AppException(HttpStatus.INTERNAL_SERVER_ERROR, "${error.code}: ${error.message}")