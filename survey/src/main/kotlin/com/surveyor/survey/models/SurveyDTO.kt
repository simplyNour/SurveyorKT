package com.surveyor.survey.models
import jakarta.persistence.*

data class SurveyDTO(
    val id: Int,
    val name: String,
    val questionsAndAnswers: QuestionAnswerDTO
);
