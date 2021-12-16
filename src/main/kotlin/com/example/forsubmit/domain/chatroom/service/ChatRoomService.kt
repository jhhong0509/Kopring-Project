package com.example.forsubmit.domain.chatroom.service

import com.example.forsubmit.domain.chatroom.entity.chatroom.ChatRoom
import com.example.forsubmit.domain.chatroom.entity.chatroom.ChatRoomRepository
import com.example.forsubmit.domain.chatroom.payload.request.CreateChatRoomRequest
import com.example.forsubmit.domain.chatroom.payload.response.CreateChatRoomResponse
import com.example.forsubmit.global.payload.BaseResponse
import org.springframework.stereotype.Service

@Service
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository
) {

    companion object {
        private const val CREATE_ROOM_RESPONSE = "Create Room Success"
        private const val CREATE_ROOM_RESPONSE_KOR = "방을 생성하는데 성공했습니다"
    }

    fun saveChatRoom(request: CreateChatRoomRequest): BaseResponse<CreateChatRoomResponse> {
        val unsavedChatRoom = buildChatRoom(request)
        val savedChatRoom = chatRoomRepository.save(unsavedChatRoom)

        val createChatRoomResponse = CreateChatRoomResponse(
            name = savedChatRoom.name,
            id = savedChatRoom.id
        )
        return BaseResponse(
            status = 201,
            message = CREATE_ROOM_RESPONSE,
            koreanMessage = CREATE_ROOM_RESPONSE_KOR,
            content = createChatRoomResponse
        )
    }

    private fun buildChatRoom(request: CreateChatRoomRequest): ChatRoom {
        return ChatRoom(name = request.name)
    }

}