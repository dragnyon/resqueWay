package fr.backend.backend.request

data class AuthRequest(
    val email: String,
    val password: String,
    val clientType: String?
)
