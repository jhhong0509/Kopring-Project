package com.example.forsubmit.global.jpa

import com.example.forsubmit.BASE_PACKAGE
import com.example.forsubmit.global.naturalid.CustomNaturalIdRepositoryImpl
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaAuditing
@EnableJpaRepositories(basePackages = [BASE_PACKAGE], repositoryBaseClass = CustomNaturalIdRepositoryImpl::class)
@Configuration
class JpaConfig