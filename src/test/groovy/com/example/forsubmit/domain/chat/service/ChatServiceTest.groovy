package com.example.forsubmit.domain.chat.service


import com.corundumstudio.socketio.SingleRoomBroadcastOperations
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.example.forsubmit.domain.chat.entity.chat.manager.ChatRepositoryManager
import com.example.forsubmit.domain.chat.payload.request.ChatRequest
import com.example.forsubmit.domain.chatroom.entity.chatroom.ChatRoom
import com.example.forsubmit.domain.chatroom.entity.chatroom.manager.ChatRoomExportManager
import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.domain.user.entity.UserRepository
import com.example.forsubmit.global.socket.security.SocketAuthenticationFacade
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles("test")
class ChatServiceTest extends Specification {

    private def userRepository = GroovyMock(UserRepository)
    private def chatRoomExportManager = GroovyMock(ChatRoomExportManager)
    private def chatRepositoryManager = GroovyMock(ChatRepositoryManager)
    private def socketAuthenticationFacade = GroovyMock(SocketAuthenticationFacade)
    private def socketIOClient = GroovyMock(SocketIOClient)
    private def socketIOServer = GroovyMock(SocketIOServer)
    private def singleRoomBroadcastOperations = GroovyMock(SingleRoomBroadcastOperations)
    private def chatService = new ChatService(chatRepositoryManager, chatRoomExportManager, socketAuthenticationFacade)

    def "Send Message Success"() {
        given:
        def chatRequest = new ChatRequest(content, chatRoomId)
        def user = new User(1, "email", "name", "password", new ArrayList(), new ArrayList(), new ArrayList())
        def chatRoom = new ChatRoom(chatRoomId, "name", new ArrayList(), new ArrayList())

        when:
        chatService.sendChatMessage(socketIOClient, socketIOServer, chatRequest)

        then:
        chatRoomExportManager.findById(chatRoomId) >> chatRoom
        socketAuthenticationFacade.getUser(socketIOClient) >> user

        singleRoomBroadcastOperations.clients >> List.of(socketIOClient)
        socketIOServer.getRoomOperations(chatRoomId.toString()) >> singleRoomBroadcastOperations

        noExceptionThrown()

        where:
        content    | chatRoomId
        "content"  | 1
        "content2" | 2
    }

}
