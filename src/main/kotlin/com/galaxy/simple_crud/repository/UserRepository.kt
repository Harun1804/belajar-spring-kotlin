package com.galaxy.simple_crud.repository

import com.galaxy.simple_crud.models.User
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<User, Long> {
    fun findByEmail(email: String): User?
}