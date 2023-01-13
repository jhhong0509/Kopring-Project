package com.example.forsubmit.global.naturalid

import org.hibernate.Session
import org.springframework.data.jpa.repository.support.JpaEntityInformation
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Transactional(readOnly = true)
class CustomNaturalIdRepositoryImpl<T, ID, NID>(
    entityInformation: JpaEntityInformation<T, ID>,
    private val entityManager: EntityManager
) : SimpleJpaRepository<T, ID>(entityInformation, entityManager), CustomNaturalIdRepository<T, ID, NID> {

    override fun findByNaturalId(id: NID): T? {
        return entityManager.unwrap(Session::class.java)
            .bySimpleNaturalId(domainClass)
            .load(id)
    }
}