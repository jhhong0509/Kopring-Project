package com.example.forsubmit

import com.example.forsubmit.global.naturalid.CustomNaturalIdRepositoryImpl
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaAuditing
@EnableJpaRepositories(repositoryBaseClass = CustomNaturalIdRepositoryImpl::class)
@Configuration
class JpaConfig