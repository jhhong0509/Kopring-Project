package com.example.forsubmit.global.socket

import com.corundumstudio.socketio.Configuration
import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner
import com.example.forsubmit.global.socket.error.SocketErrorController
import com.example.forsubmit.global.socket.property.SocketProperties
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class SocketIOConfig(
    private val socketErrorController: SocketErrorController,
    private val socketProperties: SocketProperties
) {
    @Bean
    fun socketIOServer(): SocketIOServer {
        val config = Configuration()
        config.port = socketProperties.port
        config.origin = "*"
        config.exceptionListener = socketErrorController
        return SocketIOServer(config)
    }

    @Bean
    fun springAnnotationScanner(socketServer: SocketIOServer): SpringAnnotationScanner {
        return SpringAnnotationScanner(socketServer)
    }
}