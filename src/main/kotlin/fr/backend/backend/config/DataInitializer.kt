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
            val testUsersName = listOf(
                "UserName1",
                "UserName2",
                "UserName3",
                "UserName4",
                "UserName5"
            )

            val testUsersLastName = listOf(
                "UserLastName1",
                "UserLastName2",
                "UserLastName3",
                "UserLastName4",
                "UserLastName5"
            )

            // Crée un utilisateur de test pour chaque email, nom et prénom


            testUsers.forEachIndexed { index, email ->
                if (utilisateurRepository.findByEmail(email) == null) {
                    val utilisateur = Utilisateur(
                        email = email,
                        nom = testUsersName[index],
                        prenom = testUsersLastName[index],
                        typeUtilisateur = TypeUtilisateur.SUPER_ADMIN
                    )
                    utilisateur.password = defaultPassword
                    utilisateurRepository.save(utilisateur)
                    println("✅ Utilisateur créé avec succès : $email / $defaultPassword")
                } else {
                    println("⚠️ L'utilisateur $email existe déjà, aucun ajout nécessaire.")
                }

            }






        }
    }
}
