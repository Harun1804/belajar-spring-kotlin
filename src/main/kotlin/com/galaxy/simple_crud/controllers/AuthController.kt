package com.galaxy.simple_crud.controllers

import com.galaxy.simple_crud.dtos.UserDto
import com.galaxy.simple_crud.extendable.BaseController
import com.galaxy.simple_crud.services.HashService
import com.galaxy.simple_crud.services.JwtService
import com.galaxy.simple_crud.services.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userService: UserService,
    private val hashService: HashService,
    private val jwtService: JwtService
) : BaseController() {

    @PostMapping("/login")
    fun login(@Valid @RequestBody userDto: UserDto): ResponseEntity<ResponseFormatter> {
        return handleRequest {
            val user = userService.findByEmail(userDto.email)
            if (user != null && hashService.checkBcrypt(userDto.password, user.password)) {
                ResponseEntity.ok(
                    ResponseFormatter(
                        status = true,
                        code = 200,
                        data = mapOf("token" to jwtService.createToken(user)),
                        message = "Success login"
                    )
                )
            } else {
                ResponseEntity.status(401).body(
                    ResponseFormatter(
                        status = false,
                        code = 401,
                        message = "Unauthorized"
                    )
                )
            }
        }
    }

    @PostMapping("/register")
    fun register(@Valid @RequestBody userDto: UserDto): ResponseEntity<ResponseFormatter> {
        return handleRequest {
            if (userService.findByEmail(userDto.email) != null) {
                ResponseEntity.status(400).body(
                    ResponseFormatter(
                        status = false,
                        code = 400,
                        message = "Email already exists"
                    )
                )
            }

            userService.store(userDto)

            ResponseEntity.ok(
                ResponseFormatter(
                    status = true,
                    code = 200,
                    message = "Success registering user"
                )
            )
        }
    }
}