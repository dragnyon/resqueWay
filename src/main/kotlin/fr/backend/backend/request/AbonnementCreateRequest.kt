// src/main/kotlin/fr/backend/backend/request/AbonnementCreateRequest.kt
package fr.backend.backend.request

import java.time.LocalDateTime

data class AbonnementCreateRequest(
    val dateDebut: LocalDateTime,
    val dateFin: LocalDateTime,
    val nbUtilisateur: Int,

)
