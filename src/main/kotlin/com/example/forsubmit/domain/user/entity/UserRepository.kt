package com.example.forsubmit.domain.user.entity

import com.example.forsubmit.global.naturalid.CustomNaturalIdRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CustomNaturalIdRepository<User, Long, String> {
    fun findByEmail(email: String): User?
}