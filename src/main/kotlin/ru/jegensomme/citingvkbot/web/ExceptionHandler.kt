package ru.jegensomme.citingvkbot.web

import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.jegensomme.citingvkbot.error.AppException

@RestControllerAdvice
class ExceptionHandler(
    private val errorAttributes: ErrorAttributes
) : ResponseEntityExceptionHandler()
{
    @ExceptionHandler(AppException::class)
    fun appException(request: WebRequest, ex: AppException): ResponseEntity<*>? {
        val body = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.of(MESSAGE))
        val status = ex.status
        body["message"] = ex.message
        body["status"] = status.value()
        body["error"] = status.reasonPhrase
        body["message"] = ex.message
        return ResponseEntity.status(status).body(body)
    }
}