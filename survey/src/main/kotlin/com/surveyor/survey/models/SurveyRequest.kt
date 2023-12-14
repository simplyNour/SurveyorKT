package com.surveyor.survey.models

import java.time.LocalDateTime
import java.util.*

data class QuestionResponse(
    val questionId: Int,
    val ReponseIds: Array<Long>
)

public data class SurveyRequest(
        val userEmail: String,
        val questionReponses: Array<QuestionResponse>,
)
