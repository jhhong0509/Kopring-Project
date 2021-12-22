package com.example.forsubmit.domain.post.payload.request

import javax.validation.constraints.NotBlank

class UpdatePostRequest {
    @field:NotBlank
    lateinit var title: String
        private set

    @field:NotBlank
    lateinit var content: String
        private set
}