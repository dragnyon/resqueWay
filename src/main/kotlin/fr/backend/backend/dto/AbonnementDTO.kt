package fr.backend.backend.dto

import java.time.LocalDateTime
import java.util.*

data class AbonnementDTO (

    val id: UUID? = null,
    val dateDebut: LocalDateTime,
    val dateFin: LocalDateTime,
    val nbUtilisateur: Int,
    val nbJourRestant: Int,
    val prix: Double,
    val estActif: Boolean

)
