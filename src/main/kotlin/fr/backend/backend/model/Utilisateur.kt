package fr.backend.backend.model

import jakarta.persistence.* // Import JPA pour Jakarta
import lombok.NoArgsConstructor
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@NoArgsConstructor


class Utilisateur (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val idUser: UUID , // Clé primaire unique

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @ManyToOne
    @JoinColumn(name = "id_entreprise", nullable = true) // Clé étrangère vers Entreprise
    val entreprise: Entreprise? = null,
) {


}