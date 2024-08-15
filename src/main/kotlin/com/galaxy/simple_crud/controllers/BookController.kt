package com.galaxy.simple_crud.controllers

import com.galaxy.simple_crud.dtos.BookDto
import com.galaxy.simple_crud.extendable.BaseController
import com.galaxy.simple_crud.services.BookService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController(
    private val bookService: BookService
): BaseController() {
    @GetMapping
    fun index(): ResponseEntity<ResponseFormatter> {
        return handleRequest {
            ResponseEntity.ok(
                ResponseFormatter(
                    status = true,
                    code = 200,
                    data = bookService.findAll(),
                    message = "Success get all books"
                )
            )
        }
    }

    @GetMapping("/{id}")
    fun show(@PathVariable("id") id: Long): ResponseEntity<ResponseFormatter> {
        return handleRequest {
            val book = bookService.findById(id)
            ResponseEntity.ok(
                ResponseFormatter(
                    status = true,
                    code = 200,
                    data = book,
                    message = "Success get book by id"
                )
            )

        }
    }

    @PostMapping
    fun store(@Valid @ModelAttribute bookDto: BookDto): ResponseEntity<ResponseFormatter> {
        return handleRequest {
            bookService.store(bookDto)
            ResponseEntity.ok(
                ResponseFormatter(
                    status = true,
                    code = 200,
                    message = "Success create book"
                )
            )
        }
    }

    @PutMapping("/{id}")
    fun update(@Valid @ModelAttribute bookDto: BookDto, @PathVariable("id") id: Long): ResponseEntity<ResponseFormatter> {
        return handleRequest {
            bookService.update(bookDto, id)
            ResponseEntity.ok(
                ResponseFormatter(
                    status = true,
                    code = 200,
                    message = "Success updated book"
                )
            )
        }
    }

    @DeleteMapping("/{id}")
    fun destroy(@PathVariable("id") id: Long): ResponseEntity<ResponseFormatter> {
        return handleRequest {
            bookService.destroy(id)
            ResponseEntity.ok(
                ResponseFormatter(
                    status = true,
                    code = 200,
                    message = "Success deleted book"
                )
            )
        }
    }
}