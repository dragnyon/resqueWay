package fr.backend.backend.mapper

import fr.backend.backend.dto.UtilisateurDto
import fr.backend.backend.model.Utilisateur
import org.springframework.stereotype.Component

@Component
class UtilisateurMapper {

    fun toDto(utilisateur: Utilisateur): UtilisateurDto {
        return UtilisateurDto(
            idUser = utilisateur.idUser,
            email = utilisateur.email,
            password = utilisateur.password,
            entrepriseId = utilisateur.entreprise.idEntreprise!!
        )
    }

    fun toEntity(utilisateurDto: UtilisateurDto): Utilisateur {
        return Utilisateur(
            idUser = utilisateurDto.idUser,
            email = utilisateurDto.email,
            password = utilisateurDto.password,
            entreprise = Entreprise(idEntreprise = utilisateurDto.entrepriseId)
        )
    }
}
