package com.example.forsubmit.global.socket.security

import com.corundumstudio.socketio.SocketIOClient
import com.example.forsubmit.domain.user.entity.User
import org.springframework.stereotype.Component

@Component
class SocketAuthenticationFacadeImpl : SocketAuthenticationFacade {
    override fun getUser(client: SocketIOClient): User {
        return client.get(SecurityProperties.USER_KEY)
    }
}