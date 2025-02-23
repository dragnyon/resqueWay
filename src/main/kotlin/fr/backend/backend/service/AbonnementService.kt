// src/main/kotlin/fr/backend/backend/service/AbonnementService.kt
package fr.backend.backend.service

import fr.backend.backend.dto.AbonnementDTO
import fr.backend.backend.exception.ResourceNotFoundException
import fr.backend.backend.mapper.AbonnementMapper
import fr.backend.backend.repository.AbonnementRepository
import fr.backend.backend.request.AbonnementCreateRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class AbonnementService(
    private val abonnementRepository: AbonnementRepository,
    private val abonnementMapper: AbonnementMapper
) {

    fun getAllAbonnement(): List<AbonnementDTO> {
        val abonnements = abonnementRepository.findAll()
        return abonnements.map { abonnementMapper.toDto(it) }
    }

    fun getAbonnementById(id: UUID): AbonnementDTO {
        val abonnement = abonnementRepository.findById(id).orElseThrow {
            IllegalArgumentException("Abonnement avec ID $id introuvable")
        }
        return abonnementMapper.toDto(abonnement)
    }

    fun createAbonnement(request: AbonnementCreateRequest): AbonnementDTO {
        // Calcul du nombre de jours entre dateDebut et dateFin
        val daysBetween = ChronoUnit.DAYS.between(request.dateDebut, request.dateFin).toInt()
        val nbJourRestantCalculated = if (daysBetween > 0) daysBetween else 0

        // Calcul du prix : 0,5€ par utilisateur par jour
        val prixCalculated = request.nbUtilisateur * nbJourRestantCalculated * 0.5

        // Détermination de l'état actif :
        // L'abonnement est actif si la date actuelle est comprise entre dateDebut et dateFin (inclus)
        // ou si nbJourRestantCalculated > 0.
        val now = LocalDateTime.now()
        val estActifCalculated = ((now.isEqual(request.dateDebut) || now.isAfter(request.dateDebut)) &&
                (now.isEqual(request.dateFin) || now.isBefore(request.dateFin))) ||
                (nbJourRestantCalculated > 0)

        // Créer l'entité via le mapper en passant les valeurs calculées
        val abonnement = abonnementMapper.toEntity(request, nbJourRestantCalculated, prixCalculated, estActifCalculated)
        val abonnementSaved = abonnementRepository.save(abonnement)
        return abonnementMapper.toDto(abonnementSaved)
    }

    fun updateAbonnement(id: UUID, request: AbonnementCreateRequest): AbonnementDTO {
        val abonnement = abonnementRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException("Abonnement introuvable", resourceId = id)
        }
        val daysBetween = ChronoUnit.DAYS.between(request.dateDebut, request.dateFin).toInt()
        val nbJourRestantCalculated = if (daysBetween > 0) daysBetween else 0
        val prixCalculated = request.nbUtilisateur * nbJourRestantCalculated * 0.5
        val now = LocalDateTime.now()
        val estActifCalculated = ((now.isEqual(request.dateDebut) || now.isAfter(request.dateDebut)) &&
                (now.isEqual(request.dateFin) || now.isBefore(request.dateFin))) ||
                (nbJourRestantCalculated > 0)

        abonnement.dateDebut = request.dateDebut
        abonnement.dateFin = request.dateFin
        abonnement.periodicite = request.periodicite
        abonnement.nbUtilisateur = request.nbUtilisateur
        abonnement.renouvellementAuto = request.renouvellementAuto
        abonnement.nbJourRestant = nbJourRestantCalculated
        abonnement.prix = prixCalculated
        abonnement.estActif = estActifCalculated

        val abonnementSaved = abonnementRepository.save(abonnement)
        return abonnementMapper.toDto(abonnementSaved)
    }

    fun deleteAbonnement(id: UUID) {
        if (!abonnementRepository.existsById(id)) {
            throw IllegalArgumentException("Abonnement avec ID $id introuvable")
        }
        abonnementRepository.deleteById(id)
    }
}
