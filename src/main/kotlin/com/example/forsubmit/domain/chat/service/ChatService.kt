package com.example.forsubmit.domain.chat.service

import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.example.forsubmit.domain.chat.entity.chat.Chat
import com.example.forsubmit.domain.chat.entity.chat.ChatType
import com.example.forsubmit.domain.chat.entity.chat.manager.ChatRepositoryManager
import com.example.forsubmit.domain.chat.payload.request.ChatRequest
import com.example.forsubmit.domain.chat.payload.response.ChatMessage
import com.example.forsubmit.domain.chatroom.entity.chatroom.ChatRoom
import com.example.forsubmit.domain.chatroom.entity.chatroom.manager.ChatRoomExportManager
import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.global.socket.security.SocketAuthenticationFacade
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatRepositoryManager: ChatRepositoryManager,
    private val chatRoomExportManager: ChatRoomExportManager,
    private val socketAuthenticationFacade: SocketAuthenticationFacade
) {
    companion object {
        const val MESSAGE_KEY = "message"
    }

    fun sendChatMessage(client: SocketIOClient, server: SocketIOServer, request: ChatRequest) {
        
        val user = socketAuthenticationFacade.getUser(client)
        val chatRoom = chatRoomExportManager.findById(request.chatRoomId)
        
        val chat = buildChat(user, request, chatRoom)
        chatRepositoryManager.save(chat)
        
        server.getRoomOperations(request.chatRoomId.toString())
            .clients
            .forEach { sendMessage(it, user, request) }

    }

    private fun buildChat(sender: User, request: ChatRequest, chatRoom: ChatRoom): Chat {
        return Chat(
            user = sender,
            content = request.content,
            type = ChatType.MESSAGE,
            chatRoom = chatRoom
        )
    }

    private fun sendMessage(user: SocketIOClient, sender: User, chatRequest: ChatRequest) {
        val receiveUser = socketAuthenticationFacade.getUser(user)

        val message = buildChatMessage(
            request = chatRequest,
            sender = sender,
            receiver = receiveUser
        )
        user.sendEvent(MESSAGE_KEY, message)
    }

    private fun buildChatMessage(request: ChatRequest, sender: User, receiver: User): ChatMessage {
        return ChatMessage(
            content = request.content,
            isMine = sender == receiver,
            senderId = sender.id,
            senderName = sender.name
        )
    }
}