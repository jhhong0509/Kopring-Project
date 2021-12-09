package com.example.forsubmit.global.socket

import com.corundumstudio.socketio.SocketIOServer
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("!test")
class SocketRunner(
    private val socketIOServer: SocketIOServer
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        socketIOServer.start()
    }
}