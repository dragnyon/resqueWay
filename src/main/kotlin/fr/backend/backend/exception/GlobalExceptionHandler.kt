package fr.backend.backend.exception

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

@Hidden
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(
        ex: ResourceNotFoundException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val httpStatus = HttpStatus.NOT_FOUND

        val errorResponse = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = httpStatus.value(),
            error = httpStatus.reasonPhrase,
            message = ex.message ?: "Resource not found",
            path = request.getDescription(false).removePrefix("uri=")
        )


        return ResponseEntity(errorResponse, httpStatus)
    }


}