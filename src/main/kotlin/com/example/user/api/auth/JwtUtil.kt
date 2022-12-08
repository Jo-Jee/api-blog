package com.example.user.api.auth

import com.example.user.api.dto.LoginResponseDto
import com.example.user.api.service.UserService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtUtil(
    val userService: UserService
) {
    val expTime: Long = 1000L * 60 * 3
    val secretKey: Key = Keys.hmacShaKeyFor("CNFjvDbu42BaFPmpDgjw7yFzg0yqms8l".toByteArray())
    val signatureAlgorithm: SignatureAlgorithm = SignatureAlgorithm.HS256

    fun createToken(): LoginResponseDto {
        val claims: Claims = Jwts.claims()
        claims["uid"] = "test"

        val accessToken = Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date(System.currentTimeMillis() + expTime))
            .signWith(secretKey, signatureAlgorithm)
            .compact()

        val refreshToken = Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date(System.currentTimeMillis() + expTime))
            .signWith(secretKey, signatureAlgorithm)
            .compact()

        return LoginResponseDto(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun validate(token: String): Boolean {
        val claims: Claims = getClaims(token)
        val exp: Date = claims.expiration
        return exp.after(Date())
    }

    fun parseUid(token: String): Long {
        return getClaims(token)["uid"] as Long
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }
}