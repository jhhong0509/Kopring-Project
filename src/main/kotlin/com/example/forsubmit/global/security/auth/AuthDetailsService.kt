package com.example.forsubmit.global.security.auth

import com.example.forsubmit.domain.user.entity.UserRepository
import com.example.forsubmit.global.security.exceptions.UserNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findByIdOrNull(username?.toLong()) ?: throw UserNotFoundException.EXCEPTION
        return AuthDetails(user = user)
    }
}