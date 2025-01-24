package fr.backend.backend.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*


data class HopitalDto(
    val id: UUID,
    val officialName: String,
    val fullAddress: String,
    val postalCode: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
    val occupancyRate: BigDecimal,
    val lastUpdate: LocalDateTime
)

