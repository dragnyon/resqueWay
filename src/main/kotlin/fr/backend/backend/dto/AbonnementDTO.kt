package fr.backend.backend.dto

import java.time.LocalDateTime
import java.util.*

data class AbonnementDTO (

    val id: UUID? = null,
    val dateDebut: LocalDateTime,
    val dateFin: LocalDateTime,
    val periodicite: String,
    val nbUtilisateur: Int,
    val renouvellementAuto: Boolean,
    val nbJourRestant: Int,
    val prix: Double,
    val estActif: Boolean

)
