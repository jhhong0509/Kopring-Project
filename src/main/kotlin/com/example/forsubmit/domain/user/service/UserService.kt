package com.example.forsubmit.domain.user.service

import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.domain.user.entity.UserRepository
import com.example.forsubmit.domain.user.exceptions.EmailAlreadyExistsException
import com.example.forsubmit.domain.user.payload.request.SignUpRequest
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun saveUser(request: SignUpRequest) {
        userRepository.findByEmail(request.email)?.let { throw EmailAlreadyExistsException.EXCEPTION }

        val user = User(
            name = request.name,
            email = request.email,
            password = request.password
        )

        userRepository.save(user)
    }

}