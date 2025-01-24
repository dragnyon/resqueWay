package fr.backend.backend.mapper

import fr.backend.backend.dto.AbonnementDTO
import fr.backend.backend.dto.EntrepriseDTO
import fr.backend.backend.dto.UtilisateurDto
import fr.backend.backend.model.Entreprise
import fr.backend.backend.model.Utilisateur
import org.springframework.stereotype.Component
import java.util.*

@Component
class UtilisateurMapper {

    fun toEntity(dto: UtilisateurDto, entreprise: Entreprise?): Utilisateur {

        return Utilisateur(
            idUser = UUID.randomUUID(),
            email = dto.email,
            password = dto.password,
            entreprise = entreprise,

        )
    }

    fun toDto(utilisateur: Utilisateur): UtilisateurDto {
        return UtilisateurDto(

            email = utilisateur.email,
            password = utilisateur.password,
            entreprise = utilisateur.entreprise?.idEntreprise

        )
    }

    fun toDtoList(utilisateurs: List<Utilisateur>): List<UtilisateurDto> {
        return utilisateurs.map { toDto(it) }
    }

    fun toEntityList(utilisateurs: List<UtilisateurDto>, entreprise: Entreprise): List<Utilisateur> {
        return utilisateurs.map { toEntity(it, entreprise) }
    }


}
