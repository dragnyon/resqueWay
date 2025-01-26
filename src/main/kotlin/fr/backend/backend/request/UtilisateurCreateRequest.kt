package fr.backend.backend.request

import fr.backend.backend.model.Entreprise
import java.util.*

data class UtilisateurCreateRequest(
    val email: String,
    val password: String,
    val entreprise: UUID?,
)
