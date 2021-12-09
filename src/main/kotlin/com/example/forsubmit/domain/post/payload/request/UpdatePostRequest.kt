package com.example.forsubmit.domain.post.payload.request

import javax.validation.constraints.NotBlank

class UpdatePostRequest(
    @field:NotBlank
    val title: String,

    @field:NotBlank
    val content: String
)