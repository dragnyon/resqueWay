package fr.backend.backend.service
import fr.backend.backend.dto.UtilisateurDto
import fr.backend.backend.mapper.UtilisateurMapper
import fr.backend.backend.repository.EntrepriseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import fr.backend.backend.repository.UtilisateurRepository



@Service
class UtilisateurService(
    private val utilisateurRepository: UtilisateurRepository,
    private val entrepriseRepository: EntrepriseRepository,
    private val utilisateurMapper: UtilisateurMapper
) {

    @Transactional
    fun createUtilisateur(utilisateurDto: UtilisateurDto): UtilisateurDto {
        val entreprise = utilisateurDto.entreprise?.let {
            entrepriseRepository.findById(it)
                .orElseThrow { IllegalArgumentException("Entreprise introuvable") }
        }
        val utilisateur = utilisateurMapper.toEntity(utilisateurDto, entreprise)
        val utilisateurSaved = utilisateurRepository.save(utilisateur)
        return utilisateurMapper.toDto(utilisateurSaved)
    }

    fun getUtilisateurById(id: UUID): UtilisateurDto {
        val utilisateur = utilisateurRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Utilisateur introuvable") }
        return utilisateurMapper.toDto(utilisateur)
    }

    fun getAllUtilisateurs(): List<UtilisateurDto> {
        val utilisateurs = utilisateurRepository.findAll()
        return utilisateurMapper.toDtoList(utilisateurs)
    }

    fun deleteUtilisateur(id: UUID) = utilisateurRepository.deleteById(id)


}
