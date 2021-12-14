package com.example.forsubmit.domain.room.payload.response

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

class RoomContentResponse(

    val title: String,

    val content: String,

    @DateTimeFormat(pattern = "yyyy년 MM월 dd일")
    val createdAt: LocalDateTime,

    val userName: String,

    val userEmail: String

)