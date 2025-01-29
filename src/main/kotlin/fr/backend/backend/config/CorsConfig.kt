package fr.backend.backend.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**") // Applique CORS à tous les endpoints
            .allowedOrigins("http://localhost:3000") // URL du frontend
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Méthodes HTTP autorisées
            .allowedHeaders("*") // Tous les headers autorisés
            .allowCredentials(true) // Si vous utilisez des cookies ou des sessions
    }
}
