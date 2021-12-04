package com.example.forsubmit.domain.user.controller

import com.example.forsubmit.domain.user.payload.request.SignUpRequest
import com.example.forsubmit.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RequestMapping("/user")
@RestController
class UserController(
    private val userService: UserService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@Valid request: SignUpRequest) {
        userService.saveUser(request)
    }

}