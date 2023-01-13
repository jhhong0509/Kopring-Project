package com.example.forsubmit.domain.user.service

import com.example.forsubmit.domain.auth.payload.response.TokenResponse
import com.example.forsubmit.domain.user.entity.OAuthUser
import com.example.forsubmit.domain.user.enums.OAuthType
import com.example.forsubmit.domain.user.facade.UserFacade
import com.example.forsubmit.domain.user.infrastructure.userinfo.dto.BaseUserInfoResponse
import com.example.forsubmit.domain.user.dtos.OAuthDtoFactory
import com.example.forsubmit.domain.user.dtos.authorize.OAuthBaseAuthorizeDto
import com.example.forsubmit.domain.user.dtos.token.OAuthBaseTokenDto
import com.example.forsubmit.domain.user.payload.response.OAuthRedirectUriResponse
import com.example.forsubmit.domain.user.utils.OAuthParamUtil
import com.example.forsubmit.global.payload.BaseResponse
import com.example.forsubmit.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder

@Service
class OAuthService(
    private val userFacade: UserFacade,
    private val oAuthDtoFactory: OAuthDtoFactory,
    private val jwtTokenProvider: JwtTokenProvider
) {

    companion object {
        const val GET_AUTH_URL_MESSAGE = "Get Authentication Url Success"
        const val GET_AUTH_URL_MESSAGE_KOR = "인증 URL을 성공적으로 반환했습니다."

        const val OAUTH_SIGN_IN_MESSAGE = "OAuth Sign In Success"
        const val OAUTH_SIGN_IN_MESSAGE_KOR = "OAuth 로그인에 성공했습니다."
    }

    fun getAuthorizeUri(
        type: OAuthType,
        codeChallenge: String?,
        codeChallengeMethod: String?
    ): BaseResponse<OAuthRedirectUriResponse> {

        val authorizeParam = oAuthDtoFactory.getAuthorizeDto(type, codeChallenge, codeChallengeMethod)
        val authUri = getAuthUri(authorizeParam)

        return BaseResponse(
            status = 200,
            message = GET_AUTH_URL_MESSAGE,
            koreanMessage = GET_AUTH_URL_MESSAGE_KOR,
            content = OAuthRedirectUriResponse(authUri)
        )
    }

    fun oAuthSignInOrSignUp(type: OAuthType, codeVerifier: String?, code: String): BaseResponse<TokenResponse> {
        val oAuthTokenParam = oAuthDtoFactory.getTokenDto(type, code, codeVerifier)
        val userInfoResponse = getUserInfo(oAuthTokenParam, type.tokenPrefix)

        val user = OAuthUser(
            accountId = userInfoResponse.getAccountId(),
            name = userInfoResponse.getName()
        )

        userFacade.saveUser(user)

        val tokens = jwtTokenProvider.getToken(user.accountId)

        val tokenResponse = TokenResponse(
            accessToken = tokens.accessToken,
            refreshToken = tokens.refreshToken
        )

        return BaseResponse(
            status = 200,
            message = OAUTH_SIGN_IN_MESSAGE,
            koreanMessage = OAUTH_SIGN_IN_MESSAGE_KOR,
            content = tokenResponse
        )

    }

    private fun getUserInfo(oAuthParam: OAuthBaseTokenDto, tokenPrefix: String): BaseUserInfoResponse {
        val params = OAuthParamUtil.buildBaseTokenParam(oAuthParam)
        val tokenResponse = oAuthParam.oAuthTokenClient.getToken(params)
        val token = tokenResponse.getToken()

        return oAuthParam.oAuthUserInfoClient.getUserInfo("$tokenPrefix $token")
    }

    private fun getAuthUri(oAuthBaseAuthorizeDto: OAuthBaseAuthorizeDto): String {
        return UriComponentsBuilder
            .fromHttpUrl(oAuthBaseAuthorizeDto.url)
            .path(oAuthBaseAuthorizeDto.endpoint)
            .queryParams(OAuthParamUtil.buildAuthorizeParam(oAuthBaseAuthorizeDto))
            .toUriString()
    }

}