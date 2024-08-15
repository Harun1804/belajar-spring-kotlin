package com.galaxy.simple_crud.services

import com.galaxy.simple_crud.dtos.BookDto
import com.galaxy.simple_crud.models.Book
import com.galaxy.simple_crud.repository.BookRepository
import com.galaxy.simple_crud.utils.FileUpload
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val fileUpload: FileUpload
) {
    private val uploadPath = "/media/img/books/"

    fun findAll(): List<Book> {
        val books = mutableListOf<Book>()
        bookRepository.findAll().forEach { book -> books.add(book) }
        return books
    }

    fun findById(id: Long): Book {
        return bookRepository.findById(id).orElseThrow { NoSuchElementException("Book not found") }
    }

    fun store(bookDto: BookDto) {
        val filename = fileUpload.storeImage(bookDto.thumbnail, uploadPath)
        val book = Book(
            title = bookDto.title,
            author = bookDto.author,
            publisher = bookDto.publisher,
            publishYear = bookDto.publishYear,
            price = bookDto.price,
            filename = filename,
            filepath = uploadPath
        )
        bookRepository.save(book)
    }

    fun update(bookDto: BookDto, id: Long) {
        val checkBook = bookRepository.findById(id).orElseThrow { NoSuchElementException("Book not found") }
        if (checkBook != null) {
            val book = if (bookDto.thumbnail != null) {
                val filename = fileUpload.updateImage(
                    bookDto.thumbnail,
                    uploadPath,
                    checkBook.filepath.toString() + checkBook.filename.toString()
                )
                Book(
                    id = id,
                    title = bookDto.title,
                    author = bookDto.author,
                    publisher = bookDto.publisher,
                    publishYear = bookDto.publishYear,
                    price = bookDto.price,
                    filename = filename,
                    filepath = uploadPath
                )
            } else {
                Book(
                    id = id,
                    title = bookDto.title,
                    author = bookDto.author,
                    publisher = bookDto.publisher,
                    publishYear = bookDto.publishYear,
                    price = bookDto.price
                )
            }
            bookRepository.save(book)
        }
    }

    fun destroy(id: Long) {
        val checkBook = bookRepository.findById(id).orElseThrow { NoSuchElementException("Book not found") }
        if (checkBook != null) {
            fileUpload.removeImage(checkBook.filepath.toString() + checkBook.filename.toString())
            bookRepository.deleteById(id)
        }
    }
}