package com.example.forsubmit.domain.user.payload.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class SignUpRequest(
    @field:NotBlank
    val email: String,

    @field:NotBlank
    val name: String,

    @field:Size(min = 6, max = 20)
    val password: String,
)