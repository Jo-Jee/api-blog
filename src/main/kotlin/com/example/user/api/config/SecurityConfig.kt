package com.example.user.api.config

import com.example.user.api.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
class SecurityConfig (
) {
    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
        http.headers().frameOptions().disable()

        http.authorizeRequests()
            .antMatchers(
                "/api/v1/users/**",
                "/h2-console/**",
                "/**"
            ).permitAll()
            .anyRequest().authenticated()

        return http.build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()


}