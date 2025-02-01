package fr.backend.backend.repository

import fr.backend.backend.model.Utilisateur
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UtilisateurRepository : JpaRepository<Utilisateur, UUID> {
    fun findByEmail(email: String): Utilisateur?
}



