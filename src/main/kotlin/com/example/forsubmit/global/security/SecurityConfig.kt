package com.example.forsubmit.global.security

import com.example.forsubmit.global.security.auth.CustomAccessDeniedHandler
import com.example.forsubmit.global.security.auth.CustomAuthenticationEntryPoint
import com.example.forsubmit.global.security.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val customAccessDeniedHandler: CustomAccessDeniedHandler,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .cors().and()
            .csrf().disable()

            .authorizeRequests()
                .antMatchers("/auth").permitAll()
                .antMatchers("/user").permitAll()
                .antMatchers("/docs/**").permitAll()
                .anyRequest().authenticated()
            .and()

            .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint)
            .and()

            .apply(FilterConfig(jwtTokenProvider))
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

}