package com.example.forsubmit.domain.user.infrastructure.userinfo

import com.example.forsubmit.domain.user.infrastructure.userinfo.dto.GithubUserInfoResponse
import com.example.forsubmit.domain.user.properties.GithubOAuthProperties
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "githubUserInfoClient", url = GithubOAuthProperties.USER_INFO_BASE_URL)
interface GithubUserInfoClient : OAuthUserInfoClient {

    @GetMapping(GithubOAuthProperties.USER_INFO_ENDPOINT)
    override fun getUserInfo(@RequestHeader("Authorization") token: String): GithubUserInfoResponse

}