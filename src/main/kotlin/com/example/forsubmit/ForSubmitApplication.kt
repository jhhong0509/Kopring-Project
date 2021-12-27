package com.example.forsubmit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class ForSubmitApplication

fun main(args: Array<String>) {
    runApplication<ForSubmitApplication>(*args)
}
