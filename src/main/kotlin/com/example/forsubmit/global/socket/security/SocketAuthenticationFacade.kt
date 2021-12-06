package com.example.forsubmit.global.socket.security

import com.corundumstudio.socketio.SocketIOClient
import com.example.forsubmit.domain.user.entity.User

interface SocketAuthenticationFacade {
    fun getUser(client: SocketIOClient): User
}