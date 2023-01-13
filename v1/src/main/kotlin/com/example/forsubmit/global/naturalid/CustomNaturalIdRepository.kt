package com.example.forsubmit.global.naturalid

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface CustomNaturalIdRepository<T, ID, NID> : JpaRepository<T, ID> {
    fun findByNaturalId(id: NID): T?
}