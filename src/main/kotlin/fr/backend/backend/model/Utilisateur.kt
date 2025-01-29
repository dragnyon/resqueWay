package fr.backend.backend.model

import jakarta.persistence.*
import lombok.NoArgsConstructor
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
@NoArgsConstructor


class Utilisateur(

    @Id
    @GeneratedValue
    @UuidGenerator
    var id: UUID? = null,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var password: String,

    @ManyToOne
    @JoinColumn(name = "id_entreprise", nullable = true) // Clé étrangère vers Entreprise
    var entreprise: Entreprise? = null,
) {


}