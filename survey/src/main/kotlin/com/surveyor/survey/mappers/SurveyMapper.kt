package com.surveyor.survey.mappers

import com.fasterxml.jackson.databind.ObjectMapper
import com.surveyor.survey.entities.Answer
import com.surveyor.survey.entities.Question
import com.surveyor.survey.entities.Survey
import com.surveyor.survey.models.QuestionAnswerDTO
import com.surveyor.survey.models.QuestionAnswerPairDTO
import com.surveyor.survey.models.SurveyDTO
import org.springframework.stereotype.Component
import java.util.Optional

@Component
class SurveyMapper(private val objectMapper: ObjectMapper) {
    private var questionToAnswers = HashMap<Int, MutableList<String>>()
    private var questionIdToQuestion = HashMap<Int, Question>()
    private var questionAnswersDTO: MutableList<QuestionAnswerPairDTO> = mutableListOf()

    fun toSurveyDTO(survey: Survey): SurveyDTO {
        questionAnswersDTO.clear();
        for (i in 0 until survey.questions.size) {
            var question = survey.questions.get(i);
            questionIdToQuestion.putIfAbsent(question.id, question)
            for (j in 0 until question.availableAnswers.size) {
                var answer = question.availableAnswers.get(j);
                if (questionToAnswers[question.id] != null) {
                    questionToAnswers[question.id]!!.add(answer.answerOption.value)
                } else {
                    questionToAnswers[question.id] = mutableListOf(answer.answerOption.value)
                }
            }
        }

        for ((questionId, values) in questionToAnswers.entries) {
            questionAnswersDTO.add(
                    QuestionAnswerPairDTO(
                            id = questionId,
                            description = questionIdToQuestion[questionId]!!.statement,
                            responses = values
                    )
            )
        }

        clearServiceData();
        return SurveyDTO(
                survey.id,
                survey.name,
                QuestionAnswerDTO(questionAnswersDTO)
        )
    }

    fun toSurveyDTO(answersList: List<Answer>): SurveyDTO {
        for (answer in answersList) {
            questionIdToQuestion.putIfAbsent(
                    answer.questionAnswer.question.id,
                    answer.questionAnswer.question
            )
            if (questionToAnswers[answer.questionAnswer.question.id] != null) {
                questionToAnswers[answer.questionAnswer.question.id]!!.add(
                        answer.questionAnswer.answerOption.value
                )
            } else {
                questionToAnswers[answer.questionAnswer.question.id] =
                        mutableListOf(answer.questionAnswer.answerOption.value)
            }
        }

        for ((questionId, values) in questionToAnswers.entries) {
            questionAnswersDTO.add(
                    QuestionAnswerPairDTO(
                            id = questionId,
                            description = questionIdToQuestion[questionId]!!.statement,
                            responses = values
                    )
            )
        }

        clearServiceData();
        return SurveyDTO(
                answersList[0].survey.id,
                answersList[0].survey.name,
                QuestionAnswerDTO(questionAnswersDTO)
        )
    }

    fun toJsonString(dto: SurveyDTO): String {
        return objectMapper.writeValueAsString(dto)
    }

    fun jsonToDTO(jsonResponse: String): SurveyDTO {
        return objectMapper.readValue(jsonResponse, SurveyDTO::class.java);
    }

    private fun clearServiceData(){
        questionToAnswers.clear();
        questionIdToQuestion.clear();
    }
}
