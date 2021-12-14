package com.example.forsubmit.domain.room.payload.request

import javax.validation.constraints.NotBlank

class UpdateRoomRequest(
    @field:NotBlank
    val title: String,

    @field:NotBlank
    val content: String
)