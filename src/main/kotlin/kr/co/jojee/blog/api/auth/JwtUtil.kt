package kr.co.jojee.blog.api.auth

import kr.co.jojee.blog.api.dto.LoginResponseDto
import kr.co.jojee.blog.api.entity.User
import kr.co.jojee.blog.api.service.UserService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import java.security.Key
import java.util.*

@Component
class JwtUtil(
    val userService: UserService
) {
    val expTime: Long = 1000L * 60 * 60 * 24
    val secretKey: Key = Keys.hmacShaKeyFor("CNFjvDbu42BaFPmpDgjw7yFzg0yqms8l".toByteArray())
    val signatureAlgorithm: SignatureAlgorithm = SignatureAlgorithm.HS256

    fun createToken(user: User): LoginResponseDto {
        val claims: Claims = Jwts.claims()
        claims["uid"] = user.id

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
        val uid = getClaims(token)["uid"]

        return if (uid is Number)
            uid.toLong()
        else throw ResponseStatusException(HttpStatus.BAD_REQUEST)
    }

    fun getAuthentication(uid: Long): Authentication {
        val user = userService.findById(uid)

        return UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }
}