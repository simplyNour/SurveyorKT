package com.surveyor.survey.models
import jakarta.persistence.*

data class UserDTO(
    val id: Int,
    val email: String,
);
