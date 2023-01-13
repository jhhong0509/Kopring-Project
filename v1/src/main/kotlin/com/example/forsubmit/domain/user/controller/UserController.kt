package com.example.forsubmit.domain.user.controller

import com.example.forsubmit.domain.auth.payload.response.TokenResponse
import com.example.forsubmit.domain.user.payload.request.SignUpRequest
import com.example.forsubmit.domain.user.service.UserService
import com.example.forsubmit.global.payload.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/user")
@RestController
class UserController(
    private val userService: UserService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@Valid @RequestBody request: SignUpRequest): BaseResponse<TokenResponse> {
        return userService.saveUser(request)
    }

}