package com.surveyor.survey.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.surveyor.survey.entities.Answer


@Repository
interface AnswerRepository : JpaRepository<Answer, Long>