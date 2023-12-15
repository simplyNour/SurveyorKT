package com.surveyor.survey.services

import com.surveyor.survey.repository.QuestionRepository
import com.surveyor.survey.entities.AnswerOption
import com.surveyor.survey.entities.Question
import com.surveyor.survey.entities.QuestionAnswer
import org.springframework.stereotype.Service
import java.util.Optional
import kotlin.jvm.optionals.getOrNull

@Service
class QuestionService(private val questionRepo: QuestionRepository) {

    fun verifyQuestionExists(id: Int): Boolean {
        return questionRepo.findById(id.toLong()).isPresent();
    }

    fun getQuestionContraints(id: Int): List<Array<Int>>{
        val x: List<Array<Int>> = questionRepo.getQuestionContraints(id);
        return x;
    }

    fun getAvailableAnswers(id: Int):  MutableList<QuestionAnswer>{
       val question = questionRepo.findById(id.toLong());
       if(question.isPresent()){
        return question.get().availableAnswers;
       }

       return mutableListOf();
    }

    fun add(){

    }
}
