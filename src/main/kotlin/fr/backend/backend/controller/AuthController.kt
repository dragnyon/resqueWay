package fr.backend.backend.controller

import fr.backend.backend.repository.UtilisateurRepository
import fr.backend.backend.request.AuthRequest
import fr.backend.backend.response.AuthResponse
import fr.backend.backend.security.JwtUserDetailsService
import fr.backend.backend.security.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/auth")

class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil,
    private val userDetailsService: JwtUserDetailsService,
    private val utilisateurRepository: UtilisateurRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthRequest): ResponseEntity<AuthResponse> {
        val utilisateur = utilisateurRepository.findByEmail(authRequest.email)
            ?: return ResponseEntity.badRequest().body(AuthResponse("Email incorrect", null))

        //  Vérifie le mot de passe hashé
        if (!passwordEncoder.matches(authRequest.password, utilisateur.password)) {
            return ResponseEntity.badRequest().body(AuthResponse("Mot de passe incorrect", null))
        }

        val token = jwtUtil.generateToken(utilisateur.email) // ✅ Génère le token JWT

        return ResponseEntity.ok(AuthResponse(token, utilisateur.typeUtilisateur))
    }
}
