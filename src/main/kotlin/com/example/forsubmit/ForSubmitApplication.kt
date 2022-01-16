package com.example.forsubmit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

const val BASE_PACKAGE = "com.example.forsubmit"

@ConfigurationPropertiesScan
@SpringBootApplication
@EnableFeignClients
class ForSubmitApplication

fun main(args: Array<String>) {
    runApplication<ForSubmitApplication>(*args)
}
