package com.surveyor.survey.repository

import com.surveyor.survey.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import org.springframework.data.jpa.repository.Query

@Repository interface UserRepository : JpaRepository<User, Long>{
    @Query("SELECT u FROM User u WHERE u.email=:email")
    fun findByEmail(email: String): Optional<User>;
}
