package fr.backend.backend.request

import java.util.UUID

data class EntrepriseCreateRequest(

    val name: String = "",
    val abonnement: UUID?,
    val adresse: String = "",
    val mail: String = "",
    val password: String = "",
)

