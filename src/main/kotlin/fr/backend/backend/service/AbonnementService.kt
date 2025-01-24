package fr.backend.backend.service

import fr.backend.backend.dto.AbonnementDTO
import fr.backend.backend.dto.HopitalDto
import fr.backend.backend.mapper.AbonnementMapper
import fr.backend.backend.repository.AbonnementRepository
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

    fun createAbonnement(abonnementDTO: AbonnementDTO): AbonnementDTO {
        val abonnement = abonnementMapper.toEntity(abonnementDTO)
        val abonnementSaved = abonnementRepository.save(abonnement)
        return abonnementMapper.toDto(abonnementSaved)
    }

    fun updateAbonnement(id: UUID, abonnementDTO: AbonnementDTO): AbonnementDTO {
        val existingAbonnement = abonnementRepository.findById(id).orElseThrow {
            IllegalArgumentException("Abonnement avec ID $id introuvable")
        }
        val updatedAbonnement = abonnementMapper.toEntity(abonnementDTO.copy(id = existingAbonnement.id))
        return abonnementMapper.toDto(abonnementRepository.save(updatedAbonnement))
    }

    fun deleteAbonnement(id: UUID) {
        if (!abonnementRepository.existsById(id)) {
            throw IllegalArgumentException("Abonnement avec ID $id introuvable")
        }
        abonnementRepository.deleteById(id)
    }



}