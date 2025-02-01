package fr.backend.backend.service

import fr.backend.backend.dto.UtilisateurDto
import fr.backend.backend.exception.ResourceNotFoundException
import fr.backend.backend.mapper.UtilisateurMapper
import fr.backend.backend.model.Utilisateur
import fr.backend.backend.repository.EntrepriseRepository
import fr.backend.backend.repository.UtilisateurRepository
import fr.backend.backend.request.UtilisateurCreateRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class UtilisateurService(
    private val utilisateurRepository: UtilisateurRepository,
    private val entrepriseRepository: EntrepriseRepository,
    private val utilisateurMapper: UtilisateurMapper,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun createUtilisateur(utilisateurDto: UtilisateurCreateRequest): UtilisateurDto {
        val entreprise = utilisateurDto.entreprise?.let {
            entrepriseRepository.findById(it)
                .orElseThrow { IllegalArgumentException("Entreprise introuvable") }
        }
        val utilisateur = utilisateurMapper.toEntity(utilisateurDto, entreprise)

        //utilisateur.password = passwordEncoder.encode(utilisateurDto.password) // Hashage ici

        val utilisateurSaved = utilisateurRepository.save(utilisateur)
        return utilisateurMapper.toDto(utilisateurSaved)
    }

    fun getUtilisateurById(id: UUID): UtilisateurDto {
        val utilisateur = utilisateurRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException(
                "Utilisateur introuvable",
                resourceId = id
            )
        }
        return utilisateurMapper.toDto(utilisateur)
    }

    fun getAllUtilisateurs(): List<UtilisateurDto> {
        val utilisateurs = utilisateurRepository.findAll()
        return utilisateurMapper.toDtoList(utilisateurs)
    }

    fun deleteUtilisateur(id: UUID) = utilisateurRepository.deleteById(id)

    fun updateUtilisateur(id: UUID, utilisateurDto: UtilisateurCreateRequest): UtilisateurDto {
        val entreprise = utilisateurDto.entreprise?.let {
            entrepriseRepository.findById(it)
                .orElseThrow { IllegalArgumentException("Entreprise introuvable") }
        }
        val utilisateur = utilisateurRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException(
                "Utilisateur introuvable",
                resourceId = id
            )
        }
        utilisateur.email = utilisateurDto.email
        utilisateur.password = utilisateurDto.password
        utilisateur.entreprise = entreprise
        val utilisateurSaved = utilisateurRepository.save(utilisateur)
        return utilisateurMapper.toDto(utilisateurSaved)
    }

    fun findByEmail(email: String): Utilisateur? {
        return utilisateurRepository.findByEmail(email)
    }

}
