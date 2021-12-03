package com.example.forsubmit.global.socket.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.socketio")
class SocketProperties(
    val port: Int
)