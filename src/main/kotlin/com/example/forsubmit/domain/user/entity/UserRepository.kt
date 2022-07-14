package com.example.forsubmit.domain.user.entity

import com.example.forsubmit.global.naturalid.CustomNaturalIdRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CustomNaturalIdRepository<BaseUser, Long, String> {
    fun findByAccountId(accountId: String): BaseUser?
}