package com.galaxy.simple_crud.controllers

import com.galaxy.simple_crud.dtos.UserDto
import com.galaxy.simple_crud.extendable.BaseController
import com.galaxy.simple_crud.services.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
  private val userService: UserService
) : BaseController() {
    @GetMapping
    fun index(): ResponseEntity<ResponseFormatter> {
        return handleRequest {
            ResponseEntity.ok(
                ResponseFormatter(
                    status = true,
                    code = 200,
                    data = userService.findAll(),
                    message = "Success get all users"
                )
            )
        }
    }
    @GetMapping("/find-by-email/{email}")
    fun findByEmail(@PathVariable("email") email: String): ResponseEntity<ResponseFormatter> {
        return handleRequest {
            val user = userService.findByEmail(email)
            ResponseEntity.ok(
                ResponseFormatter(
                    status = true,
                    code = 200,
                    data = user,
                    message = "Success get user by email"
                )
            )
        }
    }

    @GetMapping("/{id}")
    fun show(@PathVariable("id") id: Long): ResponseEntity<ResponseFormatter> {
        return handleRequest {
            val user = userService.findById(id)
            ResponseEntity.ok(
                ResponseFormatter(
                    status = true,
                    code = 200,
                    data = user,
                    message = "Success get user by id"
                )
            )
        }
    }

    @PostMapping
    fun store(@Valid @RequestBody userDto: UserDto): ResponseEntity<ResponseFormatter> {
        return handleRequest {
            userService.store(userDto)
            ResponseEntity.ok(
                ResponseFormatter(
                    status = true,
                    code = 200,
                    message = "Success create user"
                )
            )
        }
    }

    @PutMapping("/{id}")
    fun update(@Valid @RequestBody userDto: UserDto, @PathVariable("id") id: Long): ResponseEntity<ResponseFormatter> {
        return handleRequest {
            userService.update(userDto, id)
            ResponseEntity.ok(
                ResponseFormatter(
                    status = true,
                    code = 200,
                    message = "Success updated user"
                )
            )
        }
    }

    @DeleteMapping("/{id}")
    fun destroy(@PathVariable("id") id: Long): ResponseEntity<ResponseFormatter> {
        return handleRequest {
            userService.delete(id)
            ResponseEntity.ok(
                ResponseFormatter(
                    status = true,
                    code = 200,
                    message = "Success deleted user"
                )
            )
        }
    }
}