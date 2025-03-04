package fr.backend.backend.security

import fr.backend.backend.model.Utilisateur
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class JwtUtil {

    private val secretKey: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    // Extrait le "subject" (ici l'email) du token
    fun extractUsername(token: String): String {
        return extractClaim(token) { it.subject }
    }

    // Extrait la claim "entreprise" (ID de l'entreprise en string)
    fun extractEntreprise(token: String): String? {
        return extractClaim(token) { claims ->
            claims["entreprise"] as? String
        }
    }

    // Extrait la claim "abonnement" (ID de l'abonnement en string)
    fun extractAbonnement(token: String): String? {
        return extractClaim(token) { claims ->
            claims["abonnement"] as? String
        }
    }

    // Extrait la claim "typeUtilisateur"
    fun extractUserType(token: String): String? {
        return extractClaim(token) { claims ->
            claims["typeUtilisateur"] as? String
        }
    }

    // Extrait la claim "userId"
    fun extractUserId(token: String): String? {
        return extractClaim(token) { claims ->
            claims["userId"] as? String
        }
    }

    // Méthode générique pour extraire un champ du token
    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    // Génère le token avec l'email (subject), l'id de l'entreprise, le type d'utilisateur, etc.
    fun generateToken(user: Utilisateur): String {
        val entrepriseId = user.entreprise?.id?.toString() ?: ""
        val abonnementId = user.entreprise?.abonnement?.id?.toString() ?: ""
        val userId = user.id?.toString() ?: ""
        return Jwts.builder()
            .setSubject(user.email) // subject = email
            .claim("userId", userId)
            .claim("entreprise", entrepriseId)
            .claim("abonnement", abonnementId)
            .claim("typeUtilisateur", user.typeUtilisateur.toString())
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // ex : 10h
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    // Méthode de validation (optionnel si tu as un filtre qui valide autrement)
    fun validateToken(token: String, username: String): Boolean {
        val extractedUsername = extractUsername(token)
        return (extractedUsername == username && !isTokenExpired(token))
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractClaim(token) { it.expiration }.before(Date())
    }
}
