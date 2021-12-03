package com.example.forsubmit.global.socket.security

import com.corundumstudio.socketio.SocketIOClient
import com.example.forsubmit.global.security.jwt.JwtTokenProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller

@Controller
class SocketConnectController(
    private val jwtTokenProvider: JwtTokenProvider
) {

    fun onConnect(client: SocketIOClient) {
        val bearerToken: String? = client.handshakeData.getSingleUrlParam("Authorization")
        bearerToken?.let {
            val token = jwtTokenProvider.parseToken(it)
            val authentication: Authentication = jwtTokenProvider.authenticateUser(token)
            client[SecurityProperties.USER_KEY] = authentication.name
        }
    }

}