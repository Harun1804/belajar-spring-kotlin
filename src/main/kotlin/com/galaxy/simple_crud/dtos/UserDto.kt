package com.galaxy.simple_crud.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class UserDto(
    @NotBlank(message = "Email is required")
    val email: String,

    @NotBlank(message = "Password is required")
    val password: String,

    @NotNull(message = "Role is required")
    val role: String
)