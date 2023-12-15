package com.surveyor.survey

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.ResultActions


@SpringBootTest
@AutoConfigureMockMvc
class SurveyApplicationTests @Autowired constructor(val mockMvc: MockMvc) {

    @Test
    fun testGetSurvey() {
        mockMvc.perform(MockMvcRequestBuilders.get("/survey/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk);
    }

    @Test
    fun testGetSurveyNotFound() {
        mockMvc.perform(MockMvcRequestBuilders.get("/survey/100")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}
