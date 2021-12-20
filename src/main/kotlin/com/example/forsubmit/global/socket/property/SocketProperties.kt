package com.example.forsubmit.global.socket.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import kotlin.properties.Delegates

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.socketio")
class SocketProperties {
    var port by Delegates.notNull<Int>()
}