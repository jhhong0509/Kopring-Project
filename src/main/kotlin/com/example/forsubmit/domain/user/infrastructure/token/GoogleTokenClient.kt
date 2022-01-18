package com.example.forsubmit.domain.user.infrastructure.token

import com.example.forsubmit.domain.user.infrastructure.token.dto.GoogleTokenResponse
import com.example.forsubmit.domain.user.properties.GoogleOAuthProperties
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.SpringQueryMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(name = "googleTokenClient", url = GoogleOAuthProperties.TOKEN_BASE_URL)
interface GoogleTokenClient : OAuthTokenClient {
    @PostMapping(GoogleOAuthProperties.TOKEN_ENDPOINT)
    override fun getToken(@SpringQueryMap param: MultiValueMap<String, String>): GoogleTokenResponse
}