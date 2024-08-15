package com.galaxy.simple_crud.extendable

import org.springframework.http.ResponseEntity

open class BaseController {
    data class ResponseFormatter(
        val status: Boolean = true,
        val code: Int = 200,
        val message: String = "",
        val data: Any? = null
    )

    fun handleRequest(action: () -> ResponseEntity<ResponseFormatter>): ResponseEntity<ResponseFormatter> {
        return try {
            action()
        } catch (exception: NoSuchElementException) {
            ResponseEntity.status(404).body(
                ResponseFormatter(
                    status = false,
                    code = 404,
                    message = exception.message ?: "Not Found"
                )
            )
        } catch (exception: Exception) {
            ResponseEntity.status(500).body(
                ResponseFormatter(
                    status = false,
                    code = 500,
                    message = exception.message ?: "An error occurred"
                )
            )
        }
    }
}