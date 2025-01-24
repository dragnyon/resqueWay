package fr.backend.backend.repository

import fr.backend.backend.model.Abonnement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface AbonnementRepository : JpaRepository<Abonnement, UUID>
