package me.illyc.corresp.config

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.security.auth.login.LoginException

@ControllerAdvice
class ExceptionsHandler {

    @ExceptionHandler(LoginException::class)
    fun handleException(ex: LoginException) = ResponseEntity(ex.message, HttpStatus.UNAUTHORIZED)

//    @ExceptionHandler(Exception::class)
//    fun handleException(ex: Exception) = ResponseEntity(ex.message, HttpStatus.INTERNAL_SERVER_ERROR)
}