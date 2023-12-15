package com.surveyor.survey.controllers

import com.surveyor.survey.services.QuestionService
import com.surveyor.survey.services.SurveyService
import java.util.*
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.validation.BindingResult
import javax.validation.Valid 
import com.surveyor.survey.models.SurveyRequest
import com.surveyor.survey.models.SurveyDTO
import com.fasterxml.jackson.databind.ObjectMapper
import com.surveyor.survey.entities.Answer
import com.surveyor.survey.mappers.SurveyMapper


@RestController
@RequestMapping("/survey")
class SurveyController(private val surveyService: SurveyService,
private val surveyMapper: SurveyMapper) {
    @GetMapping("/{id}")
    fun getSurveyResponse(@PathVariable id: Long): ResponseEntity<String>{
        var responsesDTO: SurveyDTO;
        try {
            var survey = this.surveyService.findById(id);
            responsesDTO = this.surveyMapper.toSurveyDTO(survey);
        }
        catch(e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.surveyMapper.toJsonString(responsesDTO));
    }

    @PostMapping("/{id}")
    fun postSurveyResponse(
        @PathVariable id: Long,
        @Valid
        @RequestBody 
        surveyRequest: SurveyRequest, 
        bindingResult: BindingResult): ResponseEntity<String>{
            var responses: MutableList<Answer>;
            var responsesDTO: SurveyDTO;
            
            if(bindingResult.hasErrors()){
                val errorMessage = bindingResult.allErrors.joinToString { it.defaultMessage ?: "something is wrong with your request" };
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request: $errorMessage");
            }
            try {
                responses =  this.surveyService.handleSurveyRequest(id, surveyRequest);
                responsesDTO = this.surveyMapper.toSurveyDTO(responses);
            }
            catch(e: Exception) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
            }
            return ResponseEntity.status(HttpStatus.OK).body(this.surveyMapper.toJsonString(responsesDTO));
        }
}
