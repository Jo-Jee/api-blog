package com.example.user.api.auth

import com.example.user.api.service.UserService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

//@Component
class JwtUtil(
    val userService: UserService
) {
    val expTime: Long = 1000L * 60 * 3
    val secretKey: Key = Keys.hmacShaKeyFor("testJWTsecretkey".toByteArray())
    val signatureAlgorithm: SignatureAlgorithm = SignatureAlgorithm.HS256

    fun createToken(): String {
        val claims: Claims = Jwts.claims()
        claims["uid"] = "test"

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date(System.currentTimeMillis() + expTime))
            .signWith(secretKey, signatureAlgorithm)
            .compact()
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