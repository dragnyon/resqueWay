package fr.backend.backend.model

import fr.backend.backend.dto.AbonnementDTO
import jakarta.persistence.* // Import JPA pour Jakarta
import lombok.NoArgsConstructor
import java.util.*

@Entity
@NoArgsConstructor
class Entreprise(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var idEntreprise: UUID, // Clé primaire unique

    @OneToOne
    @JoinColumn(name = "id_abonnement") // Clé étrangère vers Abonnement
    val abonnement: Abonnement?,

    @Column(nullable = false)
    val adresse: String,

    @Column(name = "adresse_mail", nullable = false, unique = true)
    val adresseMail: String,

    @Column(name = "password", nullable = false)
    val password: String
) {
}