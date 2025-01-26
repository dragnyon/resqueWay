package fr.backend.backend.mapper

import fr.backend.backend.dto.AbonnementDTO
import fr.backend.backend.dto.EntrepriseDTO
import fr.backend.backend.dto.UtilisateurDto
import fr.backend.backend.model.Entreprise
import fr.backend.backend.model.Utilisateur
import fr.backend.backend.request.UtilisateurCreateRequest
import org.springframework.stereotype.Component
import java.util.*

@Component
class UtilisateurMapper {

    fun toEntity(dto: UtilisateurCreateRequest, entreprise: Entreprise?): Utilisateur {

        return Utilisateur(
            email = dto.email,
            password = dto.password,
            entreprise = entreprise?.let { entreprise },

        )
    }

    fun toDto(utilisateur: Utilisateur): UtilisateurDto {
        return UtilisateurDto(
            id = utilisateur.id,
            email = utilisateur.email,
            password = utilisateur.password,
            entreprise = utilisateur.entreprise?.id

        )
    }

    fun toDtoList(utilisateurs: List<Utilisateur>): List<UtilisateurDto> {
        return utilisateurs.map { toDto(it) }
    }

    fun toEntityList(utilisateurs: List<UtilisateurCreateRequest>, entreprise: Entreprise): List<Utilisateur> {
        return utilisateurs.map { toEntity(it, entreprise) }
    }


}
