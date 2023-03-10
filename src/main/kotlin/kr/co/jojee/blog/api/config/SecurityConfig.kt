package kr.co.jojee.blog.api.config

import kr.co.jojee.blog.api.auth.JwtFilter
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
class SecurityConfig(
    val jwtFilter: JwtFilter
) {
    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
        http.headers().frameOptions().disable()

        http.authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**/*").permitAll()
            .antMatchers(
                "/api/v1/users/**",
            ).authenticated()
            .antMatchers(
                "/api/v1/admin/**"
            ).authenticated()
            .anyRequest().permitAll()
            .and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        return http.build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}