package com.galaxy.simple_crud.dtos

import jakarta.validation.constraints.NotBlank

data class LoginDto(
    @NotBlank(message = "Email is required")
    val email: String,

    @NotBlank(message = "Password is required")
    val password: String
)