package com.example.forsubmit.global.socket

import com.corundumstudio.socketio.Configuration
import com.corundumstudio.socketio.SocketIOServer
import com.example.forsubmit.global.socket.error.SocketErrorController
import com.example.forsubmit.global.socket.property.SocketProperties
import com.example.forsubmit.global.socket.security.SocketConnectController
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class SocketIOConfig(
    private val socketIOSupporter: SocketIOAddHandlerSupporter,
    private val socketProperties: SocketProperties,
    private val socketErrorController: SocketErrorController,
    private val socketConnectController: SocketConnectController
) {
    @Bean
    fun socketIOServer(): SocketIOServer {
        val config = Configuration()
        config.port = socketProperties.port
        config.origin = "*"
        val socketIOServer = SocketIOServer(config)

        socketIOSupporter.addListeners(socketIOServer)

        socketIOServer.addListeners(socketErrorController)
        socketIOServer.addConnectListener(socketConnectController::onConnect)

        return socketIOServer
    }
}