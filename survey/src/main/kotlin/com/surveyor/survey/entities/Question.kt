package com.surveyor.survey.entities
import jakarta.persistence.*
import java.sql.Date
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonBackReference

@Entity
@Table(name = "question")
data class Question (
    @Id @GeneratedValue
    val id: Int,

    @JsonIgnore
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "survey_id", referencedColumnName = "id", insertable = false, updatable = false)
    val survey: Survey,


    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    var availableAnswers: MutableList<QuestionAnswer> = mutableListOf(),
    var statement: String,
    var min: Int,
    var max: Int,
)