package com.surveyor.survey.entities
import jakarta.persistence.*
import java.sql.Date
import com.fasterxml.jackson.annotation.JsonManagedReference

@Entity
@Table(name = "survey")
data class Survey (
    @Id @GeneratedValue
    @Column(name="id", insertable = false, updatable = false)
    val id: Int,

    val created_at: Date,
    var name: String,

    @JsonManagedReference
    @OneToMany(mappedBy = "survey", fetch = FetchType.EAGER)
    val questions: List<Question> = mutableListOf()
)