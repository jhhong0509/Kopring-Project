package com.example.forsubmit.domain.room.payload.response

class RoomListResponse(
    val responses: List<RoomResponse>,
    val hasNextPage: Boolean
)