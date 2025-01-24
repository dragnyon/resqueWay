package fr.backend.backend.repository

import fr.backend.backend.model.Entreprise
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EntrepriseRepository : JpaRepository<Entreprise, UUID>