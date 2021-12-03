package com.example.forsubmit

import com.example.forsubmit.global.naturalid.CustomNaturalIdRepositoryImpl
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaAuditing
@EnableJpaRepositories(repositoryBaseClass = CustomNaturalIdRepositoryImpl::class)
@ConfigurationPropertiesScan
@SpringBootApplication
class ForSubmitApplication

fun main(args: Array<String>) {
    runApplication<ForSubmitApplication>(*args)
}
