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

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val utilisateurRepository: UtilisateurRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) {

    /**
     * 1) Endpoint de connexion :
     * - Vérifie les identifiants
     * - Génère le JWT (avec claims : username, entrepriseId, typeUtilisateur)
     * - Dépose le JWT dans un cookie HttpOnly
     * - Renvoie juste un message (pas de typeUtilisateur dans le body)
     */
    @PostMapping("/login")
    fun login(
        @RequestBody authRequest: AuthRequest,
        response: HttpServletResponse
    ): ResponseEntity<String> {
        val utilisateur = utilisateurRepository.findByEmail(authRequest.email)
            ?: return ResponseEntity.badRequest().body("Email incorrect")

        // Vérification du password
        if (!passwordEncoder.matches(authRequest.password, utilisateur.password)) {
            return ResponseEntity.badRequest().body("Mot de passe incorrect")
        }

        // Génère le JWT
        val token = jwtUtil.generateToken(utilisateur)

        // Prépare le cookie HttpOnly
        val cookie = Cookie("access_token", token).apply {
            isHttpOnly = true
            secure = true             // à "true" en prod (HTTPS)
            path = "/"
            maxAge = 60 * 60 * 10     // 10h
            setAttribute("SameSite", "None") // autorise cross-site (si front sur un autre domaine)
        }

        // Ajoute le cookie à la réponse
        response.addCookie(cookie)

        // On renvoie juste un message simple
        return ResponseEntity.ok("Login successful")
    }

    /**
     * 2) Endpoint pour récupérer les infos utilisateur via le JWT
     * (déposé dans le cookie 'access_token')
     */
    @GetMapping("/me")
    fun getUserInfo(request: HttpServletRequest): ResponseEntity<out Map<String, String?>> {
        // Récupération du cookie
        val tokenCookie = request.cookies?.firstOrNull { it.name == "access_token" }
            ?: return ResponseEntity.status(401).body(mapOf("error" to "No token cookie"))

        val token = tokenCookie.value

        // On décode le JWT
        val username = jwtUtil.extractUsername(token)         // sub
        val typeUtilisateur = jwtUtil.extractUserType(token)  // claim "typeUtilisateur"
        val entrepriseId = jwtUtil.extractEntreprise(token)   // claim "entreprise"

        // On renvoie les infos dans un JSON
        val userInfo = mapOf(
            "username" to username,
            "typeUtilisateur" to typeUtilisateur,
            "entrepriseId" to entrepriseId
        )
        return ResponseEntity.ok(userInfo)
    }

    /**
     * 3) (Optionnel) Endpoint de logout :
     * - Remet le cookie vide + maxAge=0 => supprime côté client
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
