package com.surveyor.survey.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import com.surveyor.survey.entities.Question

@Repository
interface QuestionRepository : JpaRepository<Question, Long> {
    @Query("select q.min, q.max from Question q where q.id=:id")
    fun getQuestionContraints(id: Int): List<Array<Int>>;
}
