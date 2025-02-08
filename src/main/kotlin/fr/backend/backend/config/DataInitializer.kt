package fr.backend.backend.config

import fr.backend.backend.model.TypeUtilisateur
import fr.backend.backend.model.Utilisateur
import fr.backend.backend.repository.UtilisateurRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataInitializer {

    @Bean
    fun initDatabase(utilisateurRepository: UtilisateurRepository): ApplicationRunner {
        return ApplicationRunner {
            val defaultPassword = "password123"

            // Liste des emails des utilisateurs de test
            val testUsers = listOf(
                "default@example.com",
                "test1@example.com",
                "test2@example.com",
                "test3@example.com",
                "test4@example.com"
            )

            testUsers.forEach { email ->
                // Vérifie si l'utilisateur existe déjà
                if (utilisateurRepository.findByEmail(email) == null) {

                    val utilisateur = Utilisateur(
                        email = email,
                        typeUtilisateur = TypeUtilisateur.USER
                    )
                    utilisateur.password = defaultPassword // ✅ Pas d'encodage ici

                    utilisateurRepository.save(utilisateur)
                    println("✅ Utilisateur créé avec succès : $email / $defaultPassword")
                } else {
                    println("⚠️ L'utilisateur $email existe déjà, aucun ajout nécessaire.")
                }
            }
        }
    }
}
