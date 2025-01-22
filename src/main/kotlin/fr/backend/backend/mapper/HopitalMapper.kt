package fr.backend.backend.mapper

import fr.backend.backend.dto.HopitalDTO
import fr.backend.backend.model.Hopital
import org.springframework.stereotype.Component

@Component
class HopitalMapper {

    fun toDTO(hopital: Hopital): HopitalDTO {
        return HopitalDTO(
            id = hopital.id,
            officialName = hopital.officialName,
            fullAddress = hopital.fullAddress,
            postalCode = hopital.postalCode,
            latitude = hopital.latitude,
            longitude = hopital.longitude,
            occupancyRate = hopital.occupancyRate,
            lastUpdate = hopital.lastUpdate
        )
    }

    fun toEntity(dto: HopitalDTO): Hopital {
        return Hopital(
            id = dto.id,
            officialName = dto.officialName,
            fullAddress = dto.fullAddress,
            postalCode = dto.postalCode,
            latitude = dto.latitude,
            longitude = dto.longitude,
            occupancyRate = dto.occupancyRate,
            lastUpdate = dto.lastUpdate
        )
    }
}
