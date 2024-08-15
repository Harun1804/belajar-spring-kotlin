package com.galaxy.simple_crud.models

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "books")
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val author: String,

    @Column(nullable = false)
    val publisher: String,

    @Column(name = "publish_year")
    val publishYear: Int = 0,

    @Column()
    val price: Long = 0,

    @Column()
    val filename: String? = null,

    @Column()
    val filepath: String? = null,

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