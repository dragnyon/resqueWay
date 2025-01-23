package fr.backend.backend.dto

import java.util.UUID

data class UtilisateurDto(
    val idUser: UUID? = null,
    val email: String,
    val password: String,
    val entrepriseId: UUID
)
