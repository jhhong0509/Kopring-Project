package com.example.forsubmit.domain.room.controller

import com.example.forsubmit.domain.room.payload.request.CreateRoomRequest
import com.example.forsubmit.domain.room.payload.request.UpdateRoomRequest
import com.example.forsubmit.domain.room.payload.response.RoomContentResponse
import com.example.forsubmit.domain.room.payload.response.RoomListResponse
import com.example.forsubmit.domain.room.service.RoomService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/room")
class RoomController(
    private val roomService: RoomService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveRoom(@RequestBody @Validated request: CreateRoomRequest) {
        roomService.saveRoom(request)
    }

    @PatchMapping("/{id}")
    fun updateRoom(@PathVariable id: Long, @RequestBody @Validated request: UpdateRoomRequest) {
        roomService.updateRoom(id, request)
    }

    @DeleteMapping("/{id}")
    fun deleteRoom(@PathVariable id: Long) {
        roomService.deleteRoom(id)
    }

    @GetMapping("/{id}")
    fun getRoom(@PathVariable id: Long): RoomContentResponse {
        return roomService.getSingleRoom(id)
    }

    @GetMapping
    fun getRoomList(pageable: Pageable): RoomListResponse {
        return roomService.getRoomList(pageable)
    }
}