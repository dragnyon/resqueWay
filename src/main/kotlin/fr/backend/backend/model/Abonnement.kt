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
    var dateDebut: LocalDateTime,

    @Column(name = "date_fin", nullable = false)
    var dateFin: LocalDateTime,

    //défini le nombre d'utilisateurs qui peuvent utiliser l'abonnement
    @Column(name = "nb_utilisateur", nullable = false)
    var nbUtilisateur: Int,

    //défini le nombre de jours restant avant la fin de l'abonnement
    @Column(name = "nb_jour_restant", nullable = true)
    var nbJourRestant: Int,

    @Column(nullable = false)
    var prix: Double,

// pour savoir si l'abonnement est encore actif ou on a dépassé la date de fin
    @Column(name = "est_actif", nullable = false)
    var estActif: Boolean

) {
}