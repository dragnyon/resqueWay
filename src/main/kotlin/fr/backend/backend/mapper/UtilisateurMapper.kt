package fr.backend.backend.mapper

import fr.backend.backend.dto.UtilisateurDto
import fr.backend.backend.model.Entreprise
import fr.backend.backend.model.Utilisateur
import fr.backend.backend.request.UtilisateurCreateRequest
import org.springframework.stereotype.Component

@Component
class UtilisateurMapper {

    fun toEntity(dto: UtilisateurCreateRequest, entreprise: Entreprise?): Utilisateur {

        val utilisateur = Utilisateur(
            email = dto.email,
            nom = dto.nom,
            prenom = dto.prenom,
            entreprise = entreprise,
            typeUtilisateur = dto.typeUtilisateur,


        )
        utilisateur.password = dto.password // 🔹 Hash automatiquement via setter
        return utilisateur
    }

    fun toDto(utilisateur: Utilisateur): UtilisateurDto {
        return UtilisateurDto(
            id = utilisateur.id,
            email = utilisateur.email,
            nom = utilisateur.nom,
            prenom = utilisateur.prenom,
            password = "",
            entreprise = utilisateur.entreprise?.id,
            typeUtilisateur = utilisateur.typeUtilisateur

        )
    }

    fun toDtoList(utilisateurs: List<Utilisateur>): List<UtilisateurDto> {
        return utilisateurs.map { toDto(it) }
    }

    fun toEntityList(utilisateurs: List<UtilisateurCreateRequest>, entreprise: Entreprise): List<Utilisateur> {
        return utilisateurs.map { toEntity(it, entreprise) }
    }


}
