package ru.jegensomme.citingvkbot.error

import org.springframework.http.HttpStatus

class SecretMismatchException : AppException(HttpStatus.FORBIDDEN, "Secret does not match")