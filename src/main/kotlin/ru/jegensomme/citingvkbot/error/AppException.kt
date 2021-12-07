package ru.jegensomme.citingvkbot.error

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

open class AppException(status: HttpStatus, message: String) : ResponseStatusException(status, message)