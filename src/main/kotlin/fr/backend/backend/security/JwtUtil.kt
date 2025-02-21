package fr.backend.backend.security

import fr.backend.backend.model.Utilisateur
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtUtil {

    private val secretKey: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256) // ✅ Clé sécurisée

    fun extractUsername(token: String): String {
        return extractClaim(token) { it.subject }
    }

    fun extractEntreprise(token: String): String {
        return extractClaim(token) { it.get("entreprise", String::class.java) }
    }

    fun extractUserType(token: String): String {
        return extractClaim(token) { it.get("typeUtilisateur", String::class.java) }
    }



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

    fun generateToken(user: Utilisateur): String { // 🔹 Prend un String
        return Jwts.builder()
            .setSubject(user.email)
            .claim("entreprise", user.entreprise?.id.toString())
            .claim("typeUtilisateur", user.typeUtilisateur)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun validateToken(token: String, username: String): Boolean { // 🔹 Prend un String
        val extractedUsername = extractUsername(token)
        return extractedUsername == username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractClaim(token) { it.expiration }.before(Date())
    }
}
