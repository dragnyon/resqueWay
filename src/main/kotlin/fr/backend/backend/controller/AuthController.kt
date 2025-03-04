package fr.backend.backend.controller

import fr.backend.backend.repository.UtilisateurRepository
import fr.backend.backend.request.AuthRequest
import fr.backend.backend.security.JwtUtil
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val utilisateurRepository: UtilisateurRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) {

    /**
     * Endpoint de connexion :
     * - Vérifie les identifiants
     * - Si clientType vaut "backoffice" et que l'utilisateur est de type USER,
     *   efface le cookie (s'il existe) et renvoie une erreur 403.
     * - Sinon, génère le JWT (avec claims : username, entrepriseId, typeUtilisateur)
     *   et dépose le JWT dans un cookie HttpOnly.
     */
    @PostMapping("/login")
    fun login(
        @RequestBody authRequest: AuthRequest,
        response: HttpServletResponse
    ): ResponseEntity<String> {
        val utilisateur = utilisateurRepository.findByEmail(authRequest.email)
            ?: return ResponseEntity.badRequest().body("Email incorrect")

        if (!passwordEncoder.matches(authRequest.password, utilisateur.password)) {
            return ResponseEntity.badRequest().body("Mot de passe incorrect")
        }

        // Vérification du contexte de connexion
        if (authRequest.clientType == "backoffice" && utilisateur.typeUtilisateur.toString() == "USER") {
            // Efface le cookie s'il existe en envoyant un cookie vide avec maxAge=0
            val cookie = Cookie("access_token", "").apply {
                isHttpOnly = true
                secure = false  // à "true" en production (HTTPS)
                path = "/"
                maxAge = 0   // expire immédiatement
                setAttribute("SameSite", "None")
            }
            response.addCookie(cookie)
            return ResponseEntity.status(403).body("Accès interdit : utilisateur non autorisé pour le back office")
        }

        // Génère le JWT
        val token = jwtUtil.generateToken(utilisateur)

        // Dépose le JWT dans un cookie HttpOnly
        val cookie = Cookie("access_token", token).apply {
            isHttpOnly = true
            secure = true  // à "true" en production (HTTPS)
            path = "/"
            maxAge = 60 * 60 * 10 // par exemple 10 heures
            setAttribute("SameSite", "None")
        }
        response.addCookie(cookie)

        return ResponseEntity.ok("Login successful")
    }

    /**
     * Endpoint pour récupérer les infos utilisateur via le JWT (déposé dans le cookie "access_token")
     */
    @GetMapping("/me")
    fun getUserInfo(request: HttpServletRequest): ResponseEntity<Map<String, String?>> {
        val tokenCookie = request.cookies?.firstOrNull { it.name == "access_token" }
            ?: return ResponseEntity.status(401).body(mapOf("error" to "No token cookie"))
        val token = tokenCookie.value

        val username = jwtUtil.extractUsername(token)         // subject
        val typeUtilisateur = jwtUtil.extractUserType(token)    // claim "typeUtilisateur"
        val entrepriseId = jwtUtil.extractEntreprise(token)     // claim "entreprise"
        val abonnementId = jwtUtil.extractAbonnement(token)     // claim "abonnement"
        val userId = jwtUtil.extractUserId(token)               // claim "userId"

        val userInfo = mapOf(
            "username" to username,
            "typeUtilisateur" to typeUtilisateur,
            "entrepriseId" to entrepriseId,
            "abonnementId" to abonnementId,
            "userId" to userId
        )
        return ResponseEntity.ok(userInfo)
    }

    /**
     * Endpoint de logout : expirer le cookie
     */
    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<String> {
        val cookie = Cookie("access_token", "").apply {
            isHttpOnly = true
            secure = true
            path = "/"
            maxAge = 0 // expire immédiatement
            setAttribute("SameSite", "None")
        }
        response.addCookie(cookie)
        return ResponseEntity.ok("Logout successful")
    }
}
