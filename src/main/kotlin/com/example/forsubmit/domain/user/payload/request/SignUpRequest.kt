package com.example.forsubmit.domain.user.payload.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class SignUpRequest {
    @field:Email
    lateinit var email: String
        private set

    @field:NotBlank
    lateinit var name: String
        private set

    @field:Size(min = 6, max = 20)
    lateinit var password: String
        private set
}