package com.example.forsubmit.domain.user.facade

import com.example.forsubmit.domain.user.entity.BaseUser

sealed interface UserFacade {
    fun findUserById(id: Long): BaseUser
    fun findUserByAccountId(email: String): BaseUser
    fun findCurrentUser(): BaseUser
    fun saveUser(user: BaseUser): BaseUser
}