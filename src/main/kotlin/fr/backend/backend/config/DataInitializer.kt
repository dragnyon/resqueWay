package fr.backend.backend.config

import fr.backend.backend.model.Utilisateur
import fr.backend.backend.repository.UtilisateurRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class DataInitializer {

    @Bean
    fun initDatabase(utilisateurRepository: UtilisateurRepository): ApplicationRunner {
        return ApplicationRunner {
            val defaultEmail = "default@example.com"

            // Vérifie si l'utilisateur existe déjà
            if (utilisateurRepository.findByEmail(defaultEmail) == null) {
                val passwordEncoder = BCryptPasswordEncoder()
                val hashedPassword = passwordEncoder.encode("password123") // 🔹 Mot de passe par défaut

                val defaultUser = Utilisateur(

                    email = defaultEmail,

                    )
                defaultUser.password = "password123" // 🔹 Mot de passe haché
                utilisateurRepository.save(defaultUser)
                println("✅ Utilisateur créé avec succès : $defaultEmail / password123")
            } else {
                println("⚠️ Un utilisateur existe déjà, aucun ajout nécessaire.")
            }
        }
    }
}
