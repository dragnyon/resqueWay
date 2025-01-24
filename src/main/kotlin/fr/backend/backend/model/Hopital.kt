package fr.backend.backend.model

import jakarta.persistence.* // Import JPA pour Jakarta
import lombok.NoArgsConstructor
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@NoArgsConstructor


data class Hopital(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(), // Clé primaire unique

    @Column(name = "official_name", nullable = false)
    val officialName: String,

    @Column(name = "full_address", nullable = false)
    val fullAddress: String,

    @Column(name = "postal_code", nullable = false)
    val postalCode: String,

    @Column(nullable = false)
    val latitude: BigDecimal,

    @Column(nullable = false)
    val longitude: BigDecimal,

    @Column(name = "occupancy_rate", nullable = false)
    val occupancyRate: BigDecimal,

    @Column(name = "last_update", nullable = false)
    val lastUpdate: LocalDateTime

) {

}
