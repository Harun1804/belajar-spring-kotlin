package com.galaxy.simple_crud.services

import com.galaxy.simple_crud.dtos.UserDto
import com.galaxy.simple_crud.models.User
import com.galaxy.simple_crud.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val hashService: HashService
) {
    fun findAll(): List<User> {
        val users = mutableListOf<User>()
        userRepository.findAll().forEach { user -> users.add(user) }
        return users
    }

    fun findById(id: Long): User? {
        return userRepository.findById(id).orElseThrow { NoSuchElementException("User not found") }
    }

    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun store(userDto: UserDto) {
        val user = User(
            email = userDto.email,
            password = hashService.hashBcrypt(userDto.password),
            role = userDto.role
        )
        userRepository.save(user)
    }

    fun update(userDto: UserDto, id: Long) {
        val checkUser = userRepository.findById(id).orElseThrow { NoSuchElementException("User not found") }
        if (checkUser != null) {
            val user = User(
                id = id,
                email = userDto.email,
                password = hashService.hashBcrypt(userDto.password),
                role = userDto.role
            )
            userRepository.save(user)
        }
    }

    fun delete(id: Long) {
        val checkUser = userRepository.findById(id).orElseThrow { NoSuchElementException("User not found") }
        if (checkUser != null) {
            userRepository.deleteById(id)
        }
    }
}