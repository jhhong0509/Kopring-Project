package com.example.forsubmit.global.socket.annotation

import org.springframework.web.bind.annotation.RestController

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@RestController
annotation class SocketController
