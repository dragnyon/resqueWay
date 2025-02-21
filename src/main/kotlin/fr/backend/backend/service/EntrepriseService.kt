package fr.backend.backend.service

import fr.backend.backend.dto.EntrepriseDTO
import fr.backend.backend.mapper.EntrepriseMapper
import fr.backend.backend.repository.AbonnementRepository
import fr.backend.backend.repository.EntrepriseRepository
import fr.backend.backend.request.EntrepriseCreateRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class EntrepriseService(
    private val entrepriseRepository: EntrepriseRepository,
    private val entrepriseMapper: EntrepriseMapper,
    private val abonnementRepository: AbonnementRepository
) {

    fun getAllEntreprise(): List<EntrepriseDTO> {
        val entreprise = entrepriseRepository.findAll()
        return entreprise.map { entrepriseMapper.toDTO(it) }
    }

    fun getEntrepriseById(id: UUID): EntrepriseDTO {
        val entreprise = entrepriseRepository.findById(id).orElseThrow {
            IllegalArgumentException("Entreprise avec ID $id introuvable")
        }
        return entrepriseMapper.toDTO(entreprise)
    }

    fun createEntreprise(entrepriseDTO: EntrepriseCreateRequest): EntrepriseDTO {
        val abonnement = entrepriseDTO.abonnement?.let {
            abonnementRepository.findById(it)
                .orElseThrow { IllegalArgumentException("Abonnement introuvable") }
        }
        val entreprise = entrepriseMapper.toEntity(entrepriseDTO, abonnement)

        val entrepriseSaved = entrepriseRepository.save(entreprise)
        return entrepriseMapper.toDTO(entrepriseSaved)

    }

    fun updateEntreprise(id: UUID, entrepriseDTO: EntrepriseCreateRequest): EntrepriseDTO {
        if (!entrepriseRepository.existsById(id)) {
            throw IllegalArgumentException("Entreprise avec ID $id introuvable")
        }
        val abonnement = entrepriseDTO.abonnement?.let {
            abonnementRepository.findById(it)
                .orElseThrow { IllegalArgumentException("Abonnement introuvable") }
        }
        val entreprise = entrepriseMapper.toEntity(entrepriseDTO, abonnement)
        entreprise.id = id
        val entrepriseSaved = entrepriseRepository.save(entreprise)
        return entrepriseMapper.toDTO(entrepriseSaved)
    }



    fun deleteEntreprise(id: UUID) {
        if (!entrepriseRepository.existsById(id)) {
            throw IllegalArgumentException("Entreprise avec ID $id introuvable")
        }
        entrepriseRepository.deleteById(id)
    }
}