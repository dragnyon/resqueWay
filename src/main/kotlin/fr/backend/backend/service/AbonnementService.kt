package fr.backend.backend.service

import fr.backend.backend.dto.AbonnementDTO
import fr.backend.backend.dto.HopitalDto
import fr.backend.backend.exception.ResourceNotFoundException
import fr.backend.backend.mapper.AbonnementMapper
import fr.backend.backend.repository.AbonnementRepository
import fr.backend.backend.request.AbonnementCreateRequest
import org.springframework.stereotype.Service
import java.util.*


@Service
class AbonnementService (
    private val abonnementRepository: AbonnementRepository,
    private val abonnementMapper: AbonnementMapper
) {

    fun getAllAbonnement(): List<AbonnementDTO> {
        val abonnement = abonnementRepository.findAll()
        return abonnement.map { abonnementMapper.toDto(it) }
    }

    fun getAbonnementById(id: UUID): AbonnementDTO {
        val abonnement = abonnementRepository.findById(id).orElseThrow {
            IllegalArgumentException("Abonnement avec ID $id introuvable")
        }
        return abonnementMapper.toDto(abonnement)
    }

    fun createAbonnement(abonnementDTO: AbonnementCreateRequest): AbonnementDTO {
        val abonnement = abonnementMapper.toEntity(abonnementDTO)
        val abonnementSaved = abonnementRepository.save(abonnement)
        return abonnementMapper.toDto(abonnementSaved)
    }

    fun updateAbonnement(id: UUID, abonnementDTO: AbonnementCreateRequest): AbonnementDTO {

        val abonnement = abonnementRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException(
                "Abonnement introuvable",
                resourceId = id
            )
        }
        abonnement.dateFin = abonnementDTO.dateFin
        abonnement.dateDebut = abonnementDTO.dateDebut
        abonnement.periodicite = abonnementDTO.periodicite
        abonnement.nbUtilisateur = abonnementDTO.nbUtilisateur
        abonnement.renouvellementAuto = abonnementDTO.renouvellementAuto
        abonnement.nbJourRestant = abonnementDTO.nbJourRestant
        abonnement.prix = abonnementDTO.prix
        abonnement.estActif = abonnementDTO.estActif

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