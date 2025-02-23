package fr.backend.backend.model

import fr.backend.backend.dto.AbonnementDTO
import jakarta.persistence.* // Import JPA pour Jakarta
import lombok.NoArgsConstructor
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
@NoArgsConstructor
class Entreprise(

    @Id
    @GeneratedValue
    @UuidGenerator
    var id: UUID? = null,

    @Column(name = "name", nullable = false)
    val name: String,

    @OneToOne
    @JoinColumn(name = "id_abonnement") // Clé étrangère vers Abonnement
    val abonnement: Abonnement?,

    @Column(nullable = false)
    val adresse: String,

    @Column(name = "adresse_mail", nullable = false, unique = true)
    val adresseMail: String,

) {
}