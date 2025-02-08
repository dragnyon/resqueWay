package fr.backend.backend.model

import jakarta.persistence.*
import lombok.NoArgsConstructor
import org.hibernate.annotations.UuidGenerator
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
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
    private var _password: String = "", // Champ privé pour stocker le hashé

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var typeUtilisateur: TypeUtilisateur, // 🔹 Par défaut, utilisateur classique



    @ManyToOne
    @JoinColumn(name = "id_entreprise", nullable = true) // Clé étrangère vers Entreprise
    var entreprise: Entreprise? = null,
) {
    companion object {
        private val passwordEncoder = BCryptPasswordEncoder()
    }

    var password: String
        get() = _password // Empêche de récupérer le mot de passe en clair
        set(value) {
            _password = passwordEncoder.encode(value) // Hashage automatique
        }

}