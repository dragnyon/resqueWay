package fr.backend.backend.model

import jakarta.persistence.* // Import JPA pour Jakarta
import lombok.NoArgsConstructor
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@NoArgsConstructor
class Entreprise(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val idEntreprise: UUID? , // Clé primaire unique

    @OneToOne
    @JoinColumn(name = "id_abonnement") // Clé étrangère vers Abonnement
    val abonnement: Abonnement? = null,

    @Column(nullable = false)
    val adresse: String,

    @Column(name = "adresse_mail", nullable = false, unique = true)
    val adresseMail: String,

    @Column(nullable = false)
    val password: String
) {
}