package com.surveyor.survey.entities
import jakarta.persistence.*
import com.surveyor.survey.entities.Question
import com.fasterxml.jackson.annotation.JsonIgnore

@Entity
@Table(name = "answer_option")
data class AnswerOption (
    @Id @GeneratedValue
    val id: Int,
    
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    val type: Type,

    @JsonIgnore
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", referencedColumnName = "id")
    val survey: Survey,

    var value: String,

    // @JsonIgnore
    // @ManyToMany(mappedBy = "availableAnswers", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    // var answerTo: MutableList<QuestionAnswer> = mutableListOf()
    @JsonIgnore
    @OneToMany(mappedBy = "answerOption")
    val questionAnswers: Set<QuestionAnswer> = emptySet()
)