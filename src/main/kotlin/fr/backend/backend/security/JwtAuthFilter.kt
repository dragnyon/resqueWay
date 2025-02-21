package fr.backend.backend.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // On récupère le cookie "access_token"
        val tokenCookie = request.cookies?.firstOrNull { it.name == "access_token" }
        val token = tokenCookie?.value

        // Si le cookie n'existe pas ou est vide, on passe au suivant
        if (token.isNullOrBlank()) {
            filterChain.doFilter(request, response)
            return
        }

        // On extrait l'username depuis le token
        val username = jwtUtil.extractUsername(token)

        // Vérifie si l'utilisateur n'est pas déjà authentifié
        if (SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService.loadUserByUsername(username)
            if (jwtUtil.validateToken(token, userDetails.username)) {
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            }
        }

        filterChain.doFilter(request, response)
    }
}
