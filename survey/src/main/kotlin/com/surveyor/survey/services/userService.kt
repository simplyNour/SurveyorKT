package com.surveyor.survey.services

import com.surveyor.survey.repository.UserRepository
import com.surveyor.survey.entities.User
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UserService(private val userRepo: UserRepository) {

    fun findByEmail(email: String): Optional<User> {
        return userRepo.findByEmail(email);
    }

}
