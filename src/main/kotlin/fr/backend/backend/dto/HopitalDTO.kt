package fr.backend.backend.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class HopitalDTO(
    val id: Long? = null,
    val officialName: String,
    val fullAddress: String,
    val postalCode: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
    val occupancyRate: BigDecimal,
    val lastUpdate: LocalDateTime
)
