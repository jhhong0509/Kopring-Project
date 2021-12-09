package com.example.forsubmit.global.security.auth

import com.example.forsubmit.domain.user.facade.UserFacade
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthDetailsService(
    private val userFacade: UserFacade
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userFacade.findUserByEmail(username)
        return AuthDetails(user = user)
    }
}