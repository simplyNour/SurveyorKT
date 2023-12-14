package com.surveyor.survey.entities
import jakarta.persistence.*
import java.sql.Date

@Entity
@Table(name = "user")
data class User (
    @Id @GeneratedValue
    val id: Int,

    val email: String,
)