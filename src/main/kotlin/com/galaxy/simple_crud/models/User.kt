package com.galaxy.simple_crud.models

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column()
    val email: String,

    @Column()
    val password: String,

    @Column()
    val role: String,

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date = Date(),

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date? = null,
) {
    @PreUpdate
    protected fun onUpdate() {
        updatedAt = Date();
    }
}

enum class Role {
    ADMIN,
    USER
}