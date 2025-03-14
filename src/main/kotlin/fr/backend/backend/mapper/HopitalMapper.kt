package fr.backend.backend.mapper

import fr.backend.backend.dto.HopitalDto
import fr.backend.backend.model.Hopital
import org.springframework.stereotype.Component
import java.util.*
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import java.math.BigDecimal

@Component
class HopitalMapper {

    private val geometryFactory = GeometryFactory()

    fun toDto(hopital: Hopital): HopitalDto {
        return HopitalDto(
            id = hopital.id,
            officialName = hopital.officialName,
            fullAddress = hopital.fullAddress,
            postalCode = hopital.postalCode,
            latitude = BigDecimal(hopital.location.y.toString()),
            longitude = BigDecimal(hopital.location.x.toString()),
            occupancyRate = hopital.occupancyRate,
            lastUpdate = hopital.lastUpdate
        )
    }

    fun toEntity(hopitalDto: HopitalDto): Hopital {
        val point: Point = geometryFactory.createPoint(Coordinate(hopitalDto.longitude.toDouble(), hopitalDto.latitude.toDouble()))
        return Hopital(
            id = hopitalDto.id,
            officialName = hopitalDto.officialName,
            fullAddress = hopitalDto.fullAddress,
            postalCode = hopitalDto.postalCode,
            location = point,
            occupancyRate = hopitalDto.occupancyRate,
            lastUpdate = hopitalDto.lastUpdate,

        )
    }
}
