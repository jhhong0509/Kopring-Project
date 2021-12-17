package com.example.forsubmit.domain.post.payload.response

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

class PostContentResponse(

    val title: String,

    val content: String,

    @DateTimeFormat(pattern = "yyyy년 MM월 dd일")
    val createdAt: LocalDateTime,

    val userName: String,

    val userEmail: String

)