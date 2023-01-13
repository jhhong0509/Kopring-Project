package com.example.forsubmit.domain.user.facade

import com.example.forsubmit.domain.user.entity.BaseUser
import com.example.forsubmit.domain.user.entity.UserRepository
import com.example.forsubmit.domain.user.exceptions.AccountIdAlreadyExistsException
import com.example.forsubmit.domain.user.exceptions.UserNotFoundException
import com.example.forsubmit.global.exception.exceptions.InternalServerError
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.sql.SQLIntegrityConstraintViolationException

@Component
class UserFacadeImpl(
    private val userRepository: UserRepository
) : UserFacade {
    override fun findUserById(id: Long): BaseUser {
        return userRepository.findByIdOrNull(id) ?: throw UserNotFoundException.EXCEPTION
    }

    override fun findUserByAccountId(accountId: String): BaseUser {
        return userRepository.findByAccountId(accountId) ?: throw UserNotFoundException.EXCEPTION
    }

    override fun findCurrentUser(): BaseUser {
        val authentication = SecurityContextHolder.getContext().authentication ?: throw UserNotFoundException.EXCEPTION
        val accountId = authentication.name
        return findUserByAccountId(accountId)
    }

    override fun saveUser(user: BaseUser): BaseUser {
        return try {
            userRepository.save(user)
        } catch (_: SQLIntegrityConstraintViolationException) {
            throw AccountIdAlreadyExistsException.EXCEPTION
        } catch (_: Exception) {
            throw InternalServerError.EXCEPTION
        }
    }

}

