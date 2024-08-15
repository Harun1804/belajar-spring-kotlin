package com.galaxy.simple_crud.services

import com.galaxy.simple_crud.dtos.BookDto
import com.galaxy.simple_crud.models.Book
import com.galaxy.simple_crud.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {
    fun findAll(): List<Book> {
        val books = mutableListOf<Book>()
        bookRepository.findAll().forEach { book -> books.add(book) }
        return books
    }

    fun findById(id: Long): Book {
        return bookRepository.findById(id).orElseThrow { NoSuchElementException("Book not found") }
    }

    fun store(bookDto: BookDto) {
        val book = Book(
            title = bookDto.title,
            author = bookDto.author,
            publisher = bookDto.publisher,
            publishYear = bookDto.publishYear,
            price = bookDto.price
        )
        bookRepository.save(book)
    }

    fun update(bookDto: BookDto, id: Long) {
        val checkBook = bookRepository.findById(id).orElseThrow { NoSuchElementException("Book not found")  }
        if (checkBook != null) {
            val book = Book(
                id = id,
                title = bookDto.title,
                author = bookDto.author,
                publisher = bookDto.publisher,
                publishYear = bookDto.publishYear,
                price = bookDto.price
            )

            bookRepository.save(book)
        }
    }

    fun delete(id: Long) {
        val checkBook = bookRepository.findById(id).orElseThrow { NoSuchElementException("Book not found")  }
        if (checkBook != null) {
            bookRepository.deleteById(id)
        }
    }
}