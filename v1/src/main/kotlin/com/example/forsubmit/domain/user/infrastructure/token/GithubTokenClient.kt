package com.example.forsubmit.domain.user.infrastructure.token

import com.example.forsubmit.domain.user.infrastructure.token.dto.GithubTokenResponse
import com.example.forsubmit.domain.user.properties.GithubOAuthProperties
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.SpringQueryMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(name = "githubTokenClient", url = GithubOAuthProperties.TOKEN_BASE_URL)
interface GithubTokenClient : OAuthTokenClient {
    @PostMapping(GithubOAuthProperties.TOKEN_ENDPOINT)
    override fun getToken(@SpringQueryMap param: MultiValueMap<String, String>): GithubTokenResponse
}