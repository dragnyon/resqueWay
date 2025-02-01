package fr.backend.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig {
    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**") // 🔹 Applique CORS à toutes les routes
                    .allowedOriginPatterns("*") // 🔹 Accepte toutes les origines
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 🔹 Autorise toutes les méthodes HTTP
                    .allowedHeaders("*") // 🔹 Autorise tous les headers
                    .allowCredentials(true) // 🔹 Autorise les cookies et les headers d'authentification
            }
        }
    }
}
