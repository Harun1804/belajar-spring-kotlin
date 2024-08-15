package com.galaxy.simple_crud.dtos

import jakarta.validation.constraints.NotBlank

data class BookDto(
    @NotBlank(message = "Title is required")
    val title: String,

    @NotBlank(message = "Author is required")
    val author: String,

    @NotBlank(message = "Publisher is required")
    val publisher: String,

    val publishYear: Int,

    val price: Long
) {
}