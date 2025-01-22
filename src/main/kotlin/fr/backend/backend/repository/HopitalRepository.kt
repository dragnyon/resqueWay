package fr.backend.backend.repository

import fr.backend.backend.model.Hopital
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HopitalRepository : JpaRepository<Hopital, Long> {
    // Vous pouvez définir des requêtes personnalisées ici si nécessaire
    fun findByOfficialNameContainingIgnoreCase(name: String): List<Hopital>
}
