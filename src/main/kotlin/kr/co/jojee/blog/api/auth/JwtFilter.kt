package kr.co.jojee.blog.api.auth

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(
    val jwtUtil: JwtUtil
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader: String? = request.getHeader("Authorization") ?: return filterChain.doFilter(request, response)
        val token = authorizationHeader?.substring("Bearer ".length) ?: return filterChain.doFilter(request, response)

        if (jwtUtil.validate(token)) {
            val uid = jwtUtil.parseUid(token)
            val authentication = jwtUtil.getAuthentication(uid)

            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}