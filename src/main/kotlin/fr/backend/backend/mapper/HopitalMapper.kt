package fr.backend.backend.mapper

import fr.backend.backend.dto.HopitalDto
import fr.backend.backend.model.Hopital
import org.springframework.stereotype.Component
import java.util.*

@Component
class HopitalMapper {

    fun toDto(hopital: Hopital): HopitalDto {
        return HopitalDto(
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

    fun toEntity(hopitalDto: HopitalDto): Hopital {
        return Hopital(
            id = hopitalDto.id ,
            officialName = hopitalDto.officialName,
            fullAddress = hopitalDto.fullAddress,
            postalCode = hopitalDto.postalCode,
            latitude = hopitalDto.latitude,
            longitude = hopitalDto.longitude,
            occupancyRate = hopitalDto.occupancyRate,
            lastUpdate = hopitalDto.lastUpdate
        )
    }
}
