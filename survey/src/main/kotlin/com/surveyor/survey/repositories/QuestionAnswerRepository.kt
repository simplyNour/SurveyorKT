package com.surveyor.survey.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import com.surveyor.survey.entities.Answer
import com.surveyor.survey.entities.QuestionAnswer


@Repository
interface QuestionAnswerRepository : JpaRepository<QuestionAnswer, Long>{
    // @Query("SELECT qa FROM QuestionAnswer qa WHERE qa.question.id = :questionId AND qa.answer.id = :answerId")
    // fun findByQuestionId(@Param("questionId") questionId: Long, @Param("answerId") answerId: Long): QuestionAnswer?
}