package com.example.forsubmit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

const val BASE_PACKAGE = "com.example.forsubmit"

@ConfigurationPropertiesScan
@SpringBootApplication
class ForSubmitApplication

fun main(args: Array<String>) {
    runApplication<ForSubmitApplication>(*args)
}
