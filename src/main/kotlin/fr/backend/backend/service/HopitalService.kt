package fr.backend.backend.service

import fr.backend.backend.dto.HopitalDto
import fr.backend.backend.mapper.HopitalMapper
import fr.backend.backend.repository.HopitalRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class HopitalService(
    private val hopitalRepository: HopitalRepository,
    private val hopitalMapper: HopitalMapper
) {

    fun getAllHopitaux(): List<HopitalDto> {
        val hopitaux = hopitalRepository.findAll()
        return hopitaux.map { hopitalMapper.toDto(it) }
    }

    fun getHopitalById(id: UUID): HopitalDto {
        val hopital = hopitalRepository.findById(id).orElseThrow {
            IllegalArgumentException("Hopital avec ID $id introuvable")
        }
        return hopitalMapper.toDto(hopital)
    }

    fun createHopital(hopitalDto: HopitalDto): HopitalDto {
        val hopital = hopitalMapper.toEntity(hopitalDto)
        val hopitalSaved = hopitalRepository.save(hopital)
        return hopitalMapper.toDto(hopitalSaved)
    }

    fun updateHopital(id: UUID, hopitalDto: HopitalDto): HopitalDto {
        val existingHopital = hopitalRepository.findById(id).orElseThrow {
            IllegalArgumentException("Hopital avec ID $id introuvable")
        }
        val updatedHopital = hopitalMapper.toEntity(hopitalDto.copy(id = existingHopital.id))
        return hopitalMapper.toDto(hopitalRepository.save(updatedHopital))
    }

    fun deleteHopital(id: UUID) {
        if (!hopitalRepository.existsById(id)) {
            throw IllegalArgumentException("Hopital avec ID $id introuvable")
        }
        hopitalRepository.deleteById(id)
    }
}
