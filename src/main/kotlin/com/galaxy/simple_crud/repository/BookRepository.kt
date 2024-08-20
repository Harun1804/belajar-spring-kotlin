package com.galaxy.simple_crud.repository

import com.galaxy.simple_crud.models.Book
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository: JpaRepository<Book, Long> {
    fun findAllByOrderByTitleAsc(pageable: Pageable): Page<Book>
}