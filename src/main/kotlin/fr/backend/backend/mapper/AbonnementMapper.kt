// src/main/kotlin/fr/backend/backend/mapper/AbonnementMapper.kt
package fr.backend.backend.mapper

import fr.backend.backend.dto.AbonnementDTO
import fr.backend.backend.model.Abonnement
import fr.backend.backend.request.AbonnementCreateRequest
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Component
class AbonnementMapper {

    fun toEntity(
        dto: AbonnementCreateRequest,
        nbJourRestant: Int,
        prix: Double,
        estActif: Boolean
    ): Abonnement {
        return Abonnement(
            dateDebut = dto.dateDebut,
            dateFin = dto.dateFin,
            periodicite = dto.periodicite,
            nbUtilisateur = dto.nbUtilisateur,
            renouvellementAuto = dto.renouvellementAuto,
            nbJourRestant = nbJourRestant,
            prix = prix,
            estActif = estActif
        )
    }

    fun toDto(entity: Abonnement): AbonnementDTO {

        val nbJourRestantDynamique = if (LocalDateTime.now().isBefore(entity.dateFin)) {
            ChronoUnit.DAYS.between(LocalDateTime.now(), entity.dateFin).toInt()
        } else 0

        return AbonnementDTO(
            id = entity.id,
            dateDebut = entity.dateDebut,
            dateFin = entity.dateFin,
            periodicite = entity.periodicite,
            nbUtilisateur = entity.nbUtilisateur,
            renouvellementAuto = entity.renouvellementAuto,
            nbJourRestant = nbJourRestantDynamique,
            prix = entity.prix,
            estActif = entity.estActif
        )
    }
}
