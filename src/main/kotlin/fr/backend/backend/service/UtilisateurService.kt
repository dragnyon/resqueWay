package fr.backend.backend.service

import fr.backend.backend.dto.UtilisateurDto
import fr.backend.backend.mapper.UtilisateurMapper
import fr.backend.backend.repository.UtilisateurRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UtilisateurService(
    private val utilisateurRepository: UtilisateurRepository,
    private val utilisateurMapper: UtilisateurMapper
) {

    fun getAllUtilisateurs(): List<UtilisateurDto> {
        val utilisateurs = utilisateurRepository.findAll()
        return utilisateurs.map { utilisateurMapper.toDto(it) }
    }

    fun getUtilisateurById(id: UUID): UtilisateurDto {
        val utilisateur = utilisateurRepository.findById(id).orElseThrow {
            IllegalArgumentException("Utilisateur avec ID $id introuvable")
        }
        return utilisateurMapper.toDto(utilisateur)
    }

    fun createUtilisateur(utilisateurDto: UtilisateurDto): UtilisateurDto {
        val utilisateur = utilisateurMapper.toEntity(utilisateurDto.copy(idUser = null))
        val savedUtilisateur = utilisateurRepository.save(utilisateur)
        return utilisateurMapper.toDto(savedUtilisateur)
    }

    fun updateUtilisateur(id: UUID, utilisateurDto: UtilisateurDto): UtilisateurDto {
        val existingUtilisateur = utilisateurRepository.findById(id).orElseThrow {
            IllegalArgumentException("Utilisateur avec ID $id introuvable")
        }
        val updatedUtilisateur = utilisateurMapper.toEntity(utilisateurDto.copy(idUser = existingUtilisateur.idUser))
        return utilisateurMapper.toDto(utilisateurRepository.save(updatedUtilisateur))
    }

    fun deleteUtilisateur(id: UUID) {
        if (!utilisateurRepository.existsById(id)) {
            throw IllegalArgumentException("Utilisateur avec ID $id introuvable")
        }
        utilisateurRepository.deleteById(id)
    }
}
