package com.example.forsubmit.domain.auth.infrastructure

import com.example.forsubmit.domain.auth.infrastructure.dto.GoogleTokenResponse
import com.example.forsubmit.domain.auth.properties.GoogleOAuthProperties
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.SpringQueryMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "googleOAuthClient", url = GoogleOAuthProperties.OAUTH_BASE_URL)
interface GoogleOAuthClient {
    @PostMapping(GoogleOAuthProperties.TOKEN_URL)
    fun getGoogleToken(@SpringQueryMap param: MultiValueMap<String, String>): GoogleTokenResponse

    @GetMapping(GoogleOAuthProperties.USER_INFO_URL)
    fun getTokenInfo(@RequestParam("id_token") idToken: String): GoogleTokenResponse
}