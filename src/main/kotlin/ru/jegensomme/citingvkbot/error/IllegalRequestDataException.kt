package ru.jegensomme.citingvkbot.error

import org.springframework.http.HttpStatus

class IllegalRequestDataException : AppException(HttpStatus.UNPROCESSABLE_ENTITY, "Illegal request data")