package com.surveyor.survey.entities
import jakarta.persistence.*
import java.sql.Date

@Entity
@Table(name = "type")
data class Type (
    @Id @GeneratedValue
    val id: Int,

    val type_value: String,
)