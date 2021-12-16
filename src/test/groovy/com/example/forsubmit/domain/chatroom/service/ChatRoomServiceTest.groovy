package com.example.forsubmit.domain.chatroom.service


import com.example.forsubmit.domain.chatroom.entity.chatroom.ChatRoom
import com.example.forsubmit.domain.chatroom.entity.chatroom.ChatRoomRepository
import com.example.forsubmit.domain.chatroom.payload.request.CreateChatRoomRequest
import spock.lang.Specification

class ChatRoomServiceTest extends Specification {

    private def chatRoomRepository = GroovyMock(ChatRoomRepository)
    private def chatRoomService = new ChatRoomService(chatRoomRepository)

    def "Create Chat Room Success Test"() {
        given:
        chatRoomRepository.save(_) >> new ChatRoom(1, "name", new ArrayList(), new ArrayList())
        def chatRoomRequest = new CreateChatRoomRequest("name")
        chatRoomService.saveChatRoom(chatRoomRequest)
    }
}
