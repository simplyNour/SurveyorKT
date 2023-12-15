package com.surveyor.survey.entities

import jakarta.persistence.*
import com.fasterxml.jackson.annotation.JsonIgnore

@Entity
@Table(name = "question_answer")
data class QuestionAnswer(
        @Id @GeneratedValue 
        val id: Int,

        @JsonIgnore
        @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        @JoinColumn(name = "question_id", referencedColumnName = "id", insertable = false, updatable = false)
        val question: Question,
        
        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "answer_option_id", referencedColumnName = "id", insertable = false, updatable = false)
        val answerOption: AnswerOption
)
