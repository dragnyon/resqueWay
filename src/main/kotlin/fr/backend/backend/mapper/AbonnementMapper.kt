package fr.backend.backend.mapper

import fr.backend.backend.dto.AbonnementDTO
import fr.backend.backend.model.Abonnement
import org.springframework.stereotype.Component

@Component
class AbonnementMapper {

    fun toEntity(dto: AbonnementDTO): Abonnement {
        return Abonnement(
            id = dto.id,
            dateDebut = dto.dateDebut,
            dateFin = dto.dateFin,
            periodicite = dto.periodicite,
            nbUtilisateur = dto.nbUtilisateur,
            renouvellementAuto = dto.renouvellementAuto,
            nbJourRestant = dto.nbJourRestant,
            prix = dto.prix,
            status = dto.status
        )
    }

    fun toDto(entity: Abonnement): AbonnementDTO {
        return AbonnementDTO(
            id = entity.id,
            dateDebut = entity.dateDebut,
            dateFin = entity.dateFin,
            periodicite = entity.periodicite,
            nbUtilisateur = entity.nbUtilisateur,
            renouvellementAuto = entity.renouvellementAuto,
            nbJourRestant = entity.nbJourRestant,
            prix = entity.prix,
            status = entity.status
        )
    }
}