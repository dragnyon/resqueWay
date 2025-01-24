package fr.backend.backend.dto

import fr.backend.backend.model.Abonnement
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class EntrepriseDTO (
    val id: UUID? = null,
    val abonnement: UUID? = null,
    val adresse: String,
    val mail: String,
    val password: String


)
