package fr.backend.backend.dto

import fr.backend.backend.model.TypeUtilisateur
import java.util.UUID

data class UtilisateurDto(
    val id: UUID? = null,
    val nom: String,
    val prenom: String,
    val email: String,
    val password: String,
    val entreprise: UUID? = null,
    val typeUtilisateur: TypeUtilisateur,
)
