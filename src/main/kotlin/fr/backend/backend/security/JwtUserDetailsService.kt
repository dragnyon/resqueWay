
package fr.backend.backend.security

import fr.backend.backend.repository.UtilisateurRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(
    private val utilisateurRepository: UtilisateurRepository
) : org.springframework.security.core.userdetails.UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val utilisateur = utilisateurRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("Utilisateur introuvable : $email")

        val authority = SimpleGrantedAuthority("ROLE_" + utilisateur.typeUtilisateur.toString())

        return CustomUserDetails(
            id = utilisateur.id!!,
            email = utilisateur.email,
            entrepriseId = utilisateur.entreprise?.id,
            typeUtilisateur = utilisateur.typeUtilisateur.toString(),
            password = utilisateur.password,
            authorities = listOf(authority)
        )
    }
}
