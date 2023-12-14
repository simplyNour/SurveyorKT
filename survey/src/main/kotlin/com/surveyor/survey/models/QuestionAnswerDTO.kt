package com.surveyor.survey.models
import jakarta.persistence.*

data class QuestionAnswerPairDTO(
    val id: Int,
    val description: String,
    val responses: List<String>,
);

data class QuestionAnswerDTO(
    val questionAnswers: List<QuestionAnswerPairDTO>,
);
