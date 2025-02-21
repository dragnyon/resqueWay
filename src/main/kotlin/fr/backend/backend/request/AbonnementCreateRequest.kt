package fr.backend.backend.request

import java.time.LocalDateTime

data class AbonnementCreateRequest(
    val dateDebut: LocalDateTime,
    val dateFin: LocalDateTime,
    val periodicite: String,
    val nbUtilisateur: Int,
    val renouvellementAuto: Boolean,
    val nbJourRestant: Int,
    val prix: Double,
    val estActif: Boolean
    
)
