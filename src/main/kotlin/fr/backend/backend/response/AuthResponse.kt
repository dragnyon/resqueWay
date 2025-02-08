package fr.backend.backend.response

import fr.backend.backend.model.TypeUtilisateur

data class AuthResponse(
    val token: String,
    val typeUtilisateur: TypeUtilisateur?,
)
