package com.surveyor.survey.entities
import jakarta.persistence.*
import com.fasterxml.jackson.annotation.JsonIgnore

@Entity
@Table(name = "answer")
data class Answer (

   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="id", insertable = false, updatable = false)
   val id: Int? = 0,

   @ManyToOne
   @JoinColumn(name = "user_id", referencedColumnName = "id")
   val user: User,


   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "question_answer_id", referencedColumnName = "id")
   val questionAnswer: QuestionAnswer,


   @JsonIgnore
   @ManyToOne(cascade =  [CascadeType.MERGE])
   @JoinColumn(name = "survey_id")
   val survey: Survey,

)