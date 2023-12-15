package com.surveyor.survey.services

import com.surveyor.survey.entities.Answer
import com.surveyor.survey.entities.QuestionAnswer
import com.surveyor.survey.entities.Survey
import com.surveyor.survey.entities.User
import com.surveyor.survey.models.SurveyRequest
import com.surveyor.survey.repository.AnswerRepository
import com.surveyor.survey.repository.QuestionAnswerRepository
import com.surveyor.survey.repository.QuestionRepository
import com.surveyor.survey.repository.SurveyRepository
import jakarta.transaction.Transactional
import java.util.Optional
import org.springframework.stereotype.Service

@Service
class SurveyService(
        private val surveyRepo: SurveyRepository,
        private val userService: UserService,
        private val questionRepo: QuestionRepository,
        private val answerRepo: AnswerRepository,
        private val questionAnswerRepo: QuestionAnswerRepository
) {

    private var survey: Optional<Survey> = Optional.empty()
    private var user: Optional<User> = Optional.empty()
    private var questionToAnswers = HashMap<Int, List<QuestionAnswer>>()
    private var answersById = HashMap<Int, QuestionAnswer>()

    fun findById(id: Long): Survey {
        return surveyRepo.findById(id).orElseThrow();
    }

    fun handleSurveyRequest(surveyId: Long, request: SurveyRequest): MutableList<Answer> {
        this.validateRequest(surveyId, request)
        if (!this.user.isEmpty() && !this.survey.isEmpty()) {
            return this.saveSurveyResponse(request);
        } else {
            throw Exception()
        }
    }

    private fun validateRequest(surveyId: Long, request: SurveyRequest) {
        val user = this.userService.findByEmail(request.userEmail)
        if (!user.isPresent()) {
            throw Exception("No users with this email")
        }

        val survey = this.surveyRepo.findById(surveyId)
        if (!survey.isPresent()) {
            throw Exception("Survey doesn't exist")
        }

        val questionConstraintsById = HashMap<Int, List<Int>>()

        for (question in survey.get().questions) {
            questionToAnswers.putIfAbsent(question.id, question.availableAnswers.toList())
            for (availableAnswer in question.availableAnswers.toList()) {
                answersById.putIfAbsent(availableAnswer.id, availableAnswer)
            }
            questionConstraintsById.putIfAbsent(
                    question.id,
                    listOf<Int>(question.min, question.max)
            )
        }

        for (answer in request.questionReponses) {
            if (!questionToAnswers.containsKey(answer.questionId)) {
                throw Exception("Invalid Question")
            } else {
                val min: Int =
                        questionConstraintsById.get(answer.questionId)?.getOrNull(0)
                                ?: Int.MAX_VALUE

                val max: Int =
                        (questionConstraintsById.get(answer.questionId)?.getOrNull(1)
                                ?: Int.MIN_VALUE)

                if (!questionConstraintsById.containsKey(answer.questionId) ||
                                (min > answer.ReponseIds.size) ||
                                (max < answer.ReponseIds.size)
                ) {
                    throw Exception("Violation of question constraints")
                }

                for (questionAnswerId in answer.ReponseIds) {
                    val availableAnswersIds: MutableSet<Long> = mutableSetOf()
                    availableAnswersIds.addAll(
                            questionToAnswers.get(answer.questionId)!!.map { it.id.toLong() }
                    )
                    if (!availableAnswersIds.contains(questionAnswerId)) {
                        throw Exception("Invalid answer to question")
                    }
                }
            }
        }

        this.survey = survey
        this.user = user
    }

    @Transactional
    private fun saveSurveyResponse(request: SurveyRequest): MutableList<Answer> {
        var answers: MutableList<Answer> = mutableListOf()
        for (questionResponse in request.questionReponses) {
            for (answer in questionResponse.ReponseIds) {
                if (answersById.containsKey(answer.toInt())) {
                    answers.add(
                            Answer(
                                    user = this.user.get(),
                                    questionAnswer = answersById.get(answer.toInt())!!,
                                    survey = this.survey.get()
                            )
                    )
                }
            }
        }

        return this.answerRepo.saveAll(answers);
    }

    private fun validateUser() {}

    private fun validateSurvey() {}

    private fun validateQuestion() {}

    private fun validateQuestionConstraints() {}

    private fun validateAnswers() {}
}
