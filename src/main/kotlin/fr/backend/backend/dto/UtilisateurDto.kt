package fr.backend.backend.dto


import java.util.UUID

data class UtilisateurDto(
    val email: String,
    val password: String,
    val entreprise: UUID? = null,
)
