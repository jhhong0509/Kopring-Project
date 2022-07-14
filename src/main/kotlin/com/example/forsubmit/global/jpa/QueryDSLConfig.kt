package com.example.forsubmit.global.jpa

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager

@Configuration
class QueryDSLConfig(
    private val entityManager: EntityManager
) {

    @Bean
    fun jpaQueryFactory() = JPAQueryFactory(entityManager)

}