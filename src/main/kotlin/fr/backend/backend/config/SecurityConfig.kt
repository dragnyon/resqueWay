package fr.backend.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { it.anyRequest().permitAll() } // Autorise toutes les URL
            .csrf { it.disable() } // Désactive CSRF
            .headers { it.frameOptions().disable() } // Permet l'accès aux frames pour H2 Console

        return http.build()
    }
}
