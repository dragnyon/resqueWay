package fr.backend.backend.repository

import fr.backend.backend.model.Hopital
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface HopitalRepository : JpaRepository<Hopital, UUID>
