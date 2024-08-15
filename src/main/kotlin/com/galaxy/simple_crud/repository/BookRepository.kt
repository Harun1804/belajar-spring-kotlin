package com.galaxy.simple_crud.repository

import com.galaxy.simple_crud.models.Book
import org.springframework.data.repository.CrudRepository

interface BookRepository: CrudRepository<Book, Long> {
}