package fr.backend.backend.request

import fr.backend.backend.model.TypeUtilisateur
import java.util.*

data class UtilisateurUpdateRequest(
    val nom: String,
    val prenom: String,
    val email: String,
    val entreprise: UUID?,
    val typeUtilisateur: TypeUtilisateur

)
