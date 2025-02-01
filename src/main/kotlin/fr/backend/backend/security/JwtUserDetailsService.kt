package fr.backend.backend.security

import fr.backend.backend.repository.UtilisateurRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(
    private val utilisateurRepository: UtilisateurRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val utilisateur = utilisateurRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("Utilisateur introuvable : $email")

        return User(utilisateur.email, utilisateur.password, listOf())
    }
}
