package com.example.forsubmit.global.security

import com.example.forsubmit.global.security.jwt.JwtTokenProvider
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class FilterConfig(
    private val jwtTokenProvider: JwtTokenProvider
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
    override fun configure(builder: HttpSecurity) {
        val tokenFilter = TokenFilter(jwtTokenProvider)
        builder.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}