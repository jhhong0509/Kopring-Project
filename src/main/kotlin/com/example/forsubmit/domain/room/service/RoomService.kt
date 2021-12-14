package com.example.forsubmit.domain.room.service

import com.example.forsubmit.domain.room.entity.Room
import com.example.forsubmit.domain.room.entity.RoomRepository
import com.example.forsubmit.domain.room.exceptions.CannotUpdateRoomException
import com.example.forsubmit.domain.room.exceptions.RoomNotFoundException
import com.example.forsubmit.domain.room.payload.request.CreateRoomRequest
import com.example.forsubmit.domain.room.payload.request.UpdateRoomRequest
import com.example.forsubmit.domain.room.payload.response.RoomContentResponse
import com.example.forsubmit.domain.room.payload.response.RoomListResponse
import com.example.forsubmit.domain.room.payload.response.RoomResponse
import com.example.forsubmit.domain.user.facade.UserFacade
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val userFacade: UserFacade
) {
    fun saveRoom(request: CreateRoomRequest) {
        val user = userFacade.findCurrentUser()

        val room = Room(
            user = user,
            title = request.title,
            content = request.content
        )

        roomRepository.save(room)
    }

    @Transactional
    fun updateRoom(id: Long, request: UpdateRoomRequest) {
        val user = userFacade.findCurrentUser()
        val room = roomRepository.findByIdOrNull(id) ?: throw RoomNotFoundException.EXCEPTION

        if (room.user != user) {
            throw CannotUpdateRoomException.EXCEPTION
        }

        room.update(
            title = request.title,
            content = request.content
        )
    }

    fun deleteRoom(id: Long) {
        val user = userFacade.findCurrentUser()
        val room = roomRepository.findByIdOrNull(id) ?: throw RoomNotFoundException.EXCEPTION

        if (room.user != user) {
            throw CannotUpdateRoomException.EXCEPTION
        }

        roomRepository.delete(room)
    }

    fun getSingleRoom(id: Long): RoomContentResponse {
        val room = roomRepository.findByIdOrNull(id) ?: throw RoomNotFoundException.EXCEPTION
        val user = userFacade.findCurrentUser()

        return RoomContentResponse(
            content = room.content,
            title = room.title,
            createdAt = room.createdDate ?: LocalDateTime.now(),
            userEmail = user.email,
            userName = user.name
        )
    }

    fun getRoomList(pageable: Pageable): RoomListResponse {
        val roomPage = roomRepository.findAllBy(pageable)

        val roomList = roomPage.content
            .map {
                RoomResponse(
                    userName = it.user.name,
                    userEmail = it.user.email,
                    createdAt = it.createdDate,
                    title = it.title
                )
            }
            .toCollection(mutableListOf())

        return RoomListResponse(
            responses = roomList,
            hasNextPage = roomPage.hasNext()
        )

    }

}