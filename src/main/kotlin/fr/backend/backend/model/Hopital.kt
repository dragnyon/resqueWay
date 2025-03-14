package fr.backend.backend.model

import jakarta.persistence.* // Import JPA pour Jakarta
import lombok.NoArgsConstructor
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.Type
import org.hibernate.annotations.UuidGenerator
import org.hibernate.type.SqlTypes
import org.locationtech.jts.geom.Point
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@NoArgsConstructor


data class Hopital(

    @Id
    @GeneratedValue
    @UuidGenerator
    var id: UUID? = null,
    @Column(name = "official_name", nullable = false)
    val officialName: String,

    @Column(name = "full_address", nullable = false)
    val fullAddress: String,

    @Column(name = "postal_code", nullable = false)
    val postalCode: String,



    @Column(name = "location",columnDefinition = "geography(Point,4326)")
    var location: Point,

    @Column(name = "occupancy_rate", nullable = false)
    val occupancyRate: BigDecimal,

    @Column(name = "last_update", nullable = false)
    val lastUpdate: LocalDateTime

) {

}
