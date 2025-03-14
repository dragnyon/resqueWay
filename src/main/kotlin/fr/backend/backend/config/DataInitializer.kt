package fr.backend.backend.config

import fr.backend.backend.model.Hopital
import fr.backend.backend.model.TypeUtilisateur
import fr.backend.backend.model.Utilisateur
import fr.backend.backend.repository.HopitalRepository
import fr.backend.backend.repository.UtilisateurRepository
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal
import java.time.LocalDateTime

@Configuration
class DataInitializer {

    @Bean
    fun initDatabase(utilisateurRepository: UtilisateurRepository, hopitalRepository: HopitalRepository): ApplicationRunner {
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

    @Bean
    fun initHopitaux(hopitalRepository: HopitalRepository): ApplicationRunner {
        return ApplicationRunner {
            // Vérifie si la table est vide avant d'insérer les données
            if (hopitalRepository.count() == 0L) {
                val geometryFactory = GeometryFactory()

                val hopital1 = Hopital(
                    officialName = "Centre Hospitalier Universitaire de Dijon",
                    fullAddress = "15 Boulevard de l'Hôpital, 21000 Dijon",
                    postalCode = "21000",
                    // Les coordonnées sont créées avec (longitude, latitude)
                    location = geometryFactory.createPoint(Coordinate(5.041, 47.321)),
                    occupancyRate = BigDecimal("0.85"),
                    lastUpdate = LocalDateTime.now()
                )

                val hopital2 = Hopital(
                    officialName = "Clinique de Beaune",
                    fullAddress = "12 Rue de la Santé, 21200 Beaune",
                    postalCode = "21200",
                    location = geometryFactory.createPoint(Coordinate(4.840, 47.028)),
                    occupancyRate = BigDecimal("0.90"),
                    lastUpdate = LocalDateTime.now()
                )

                // Vous pouvez ajouter d'autres hôpitaux de la Côte-d'Or si nécessaire
                hopitalRepository.save(hopital1)
                hopitalRepository.save(hopital2)

                println("✅ Hôpitaux insérés avec succès dans la région de la Côte-d'Or.")
            } else {
                println("⚠️ La table des hôpitaux n'est pas vide, aucun ajout effectué.")
            }
        }
    }
}