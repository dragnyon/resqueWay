package fr.backend.backend.service

import fr.backend.backend.dto.HopitalDTO
import fr.backend.backend.mapper.HopitalMapper
import fr.backend.backend.model.Hopital
import fr.backend.backend.repository.HopitalRepository
import org.springframework.stereotype.Service

@Service
class HopitalService(
    private val hopitalRepository: HopitalRepository,
    private val hopitalMapper: HopitalMapper
) {

    fun getAllHopitaux(): List<HopitalDTO> {
        return hopitalRepository.findAll().map { hopitalMapper.toDTO(it) }
    }

    fun createHopital(dto: HopitalDTO): HopitalDTO {
        val hopital = hopitalMapper.toEntity(dto)
        val savedHopital = hopitalRepository.save(hopital)
        return hopitalMapper.toDTO(savedHopital)
    }

    fun getHopitalById(id: Long): HopitalDTO {
        val hopital = hopitalRepository.findById(id).orElseThrow {
            IllegalArgumentException("Hopital avec ID $id introuvable")
        }
        return hopitalMapper.toDTO(hopital)
    }
}
