package fr.backend.backend.model

import jakarta.persistence.* // Import JPA pour Jakarta
import lombok.NoArgsConstructor
import org.hibernate.annotations.UuidGenerator
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@NoArgsConstructor
class Abonnement(

    @Id
    @GeneratedValue
    @UuidGenerator
    var id: UUID? = null,

    @Column(name = "date_debut", nullable = false)
    val dateDebut: LocalDateTime,

    @Column(name = "date_fin", nullable = false)
    val dateFin: LocalDateTime,

    @Column(nullable = false)
    val periodicite: String,

    @Column(name = "nb_utilisateur", nullable = false)
    val nbUtilisateur: Int,

    @Column(name = "renouvellement_auto", nullable = false)
    val renouvellementAuto: String,

    @Column(name = "nb_jour_restant", nullable = true)
    val nbJourRestant: Int?,

    @Column(nullable = false)
    val prix: Double,

    @Column(nullable = false)
    val status: String // "actif" ou "passif"

) {
}