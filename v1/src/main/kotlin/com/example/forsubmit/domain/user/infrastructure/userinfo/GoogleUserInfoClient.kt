package com.example.forsubmit.domain.user.infrastructure.userinfo

import com.example.forsubmit.domain.user.infrastructure.userinfo.dto.GoogleUserInfoResponse
import com.example.forsubmit.domain.user.properties.GoogleOAuthProperties
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "googleUserInfoClient", url = GoogleOAuthProperties.USER_INFO_BASE_URL)
interface GoogleUserInfoClient : OAuthUserInfoClient {

    @GetMapping(GoogleOAuthProperties.USER_INFO_ENDPOINT)
    override fun getUserInfo(@RequestHeader("Authorization") token: String): GoogleUserInfoResponse

}