package kr.co.jojee.blog.api.auth

import kr.co.jojee.blog.api.dto.TokenResponse
import kr.co.jojee.blog.api.entity.User
import kr.co.jojee.blog.api.service.UserService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import java.security.Key
import java.util.*

@Component
class JwtUtil(
    val userService: UserService,
    @Value("\${jwt.secret}")
    val secretString: String
) {
    val accessTokenExpTime: Long = 1000L * 60 * 10
    val refreshTokenExpTime: Long = 1000L * 60 * 60 * 24 * 10
    val secretKey: Key = Keys.hmacShaKeyFor(secretString.toByteArray())
    val signatureAlgorithm: SignatureAlgorithm = SignatureAlgorithm.HS256

    fun createToken(user: User): TokenResponse {
        val accessTokenClaims = Jwts.claims()
        val refreshTokenClaims = Jwts.claims()

        accessTokenClaims["type"] = "access"
        accessTokenClaims["uid"] = user.id

        refreshTokenClaims["type"] = "refresh"
        refreshTokenClaims["uid"] = user.id

        val accessToken = Jwts.builder()
            .setClaims(accessTokenClaims)
            .setExpiration(Date(System.currentTimeMillis() + accessTokenExpTime))
            .signWith(secretKey, signatureAlgorithm)
            .compact()

        val refreshToken = Jwts.builder()
            .setClaims(refreshTokenClaims)
            .setExpiration(Date(System.currentTimeMillis() + refreshTokenExpTime))
            .signWith(secretKey, signatureAlgorithm)
            .compact()

        return TokenResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun validate(token: String): Boolean {
        try {
            getClaims(token)
        } catch (jwtException: JwtException) {
            return false
        }
        return true
    }

    fun validateRefreshToken(token: String): Long {
        try {
            val claims = getClaims(token)
            val uid = getClaims(token)["uid"]

            if (claims["type"] != "refresh")
                throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 아닙니다.")

            return if (uid is Number)
                uid.toLong()
            else throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        } catch (jwtException: JwtException) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰 인증에 실패했습니다.")
        }
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