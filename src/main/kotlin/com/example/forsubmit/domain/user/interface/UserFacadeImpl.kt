package com.example.forsubmit.domain.user.`interface`

import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.domain.user.entity.UserRepository
import com.example.forsubmit.domain.user.exceptions.UserNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserFacadeImpl(
    private val userRepository: UserRepository
) : UserFacade {
    override fun findUserById(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw UserNotFoundException.EXCEPTION
    }

    override fun findUserByEmail(email: String): User {
        return userRepository.findByEmail(email) ?: throw UserNotFoundException.EXCEPTION
    }

    override fun findCurrentUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication ?: throw UserNotFoundException.EXCEPTION
        val id = authentication.name.toLong()
        return findUserById(id)
    }

}

