package com.example.forsubmit.domain.user.`interface`

import com.example.forsubmit.domain.user.entity.User

interface UserFacade {
    fun findUserById(id: Long): User
    fun findUserByEmail(email: String): User
    fun findCurrentUser(): User
}