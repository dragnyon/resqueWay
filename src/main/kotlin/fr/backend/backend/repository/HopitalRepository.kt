package fr.backend.backend.repository

import fr.backend.backend.model.Hopital
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface HopitalRepository : JpaRepository<Hopital, UUID> {
    @Query(
        value = "SELECT * FROM hopital WHERE ST_DWithin(location, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography, :distance)",
        nativeQuery = true
    )
    fun findHopitauxWithinDistance(
        @Param("latitude") latitude: Double,
        @Param("longitude") longitude: Double,
        @Param("distance") distance: Double // distance en mètres
    ): List<Hopital>
}
