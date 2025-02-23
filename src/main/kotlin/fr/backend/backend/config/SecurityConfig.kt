package fr.backend.backend.config

import fr.backend.backend.security.JwtAuthFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val jwtAuthFilter: JwtAuthFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            // Active CORS (voir le Bean corsConfigurationSource() plus bas)
            .cors { }

            // Désactive la protection CSRF pour simplifier (ou configure un vrai token CSRF si besoin)
            .csrf { it.disable() }

            // Gère l’autorisation des requêtes
            .authorizeHttpRequests { auth ->
                // Autorise sans authentification
                auth.requestMatchers(
                    "/api/auth/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/api/utilisateurs/create",
                    "/api/entreprise/create",
                ).permitAll()

                // N’autorise qu’aux utilisateurs authentifiés
                auth.requestMatchers(
                    "/api/utilisateurs/**",
                    "/api/entreprise/**",
                    "/api/abonnement/**",
                ).authenticated()
            }

            // Session stateless (pas d’état de session serveur)
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

            // Déclare quel AuthenticationProvider utiliser
            .authenticationProvider(authenticationProvider())

            // Insère le filtre JWT avant UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    // Configuration CORS pour autoriser le front (localhost:3000 en exemple)
    // à envoyer des cookies (JWT) au backend
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfig = CorsConfiguration()

        // Remplace par l'origine de ton front (domain/port)
        corsConfig.allowedOrigins = listOf("http://localhost:3000")
        corsConfig.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        corsConfig.allowedHeaders = listOf("*")
        corsConfig.exposedHeaders = listOf("Authorization")

        // Indispensable pour envoyer/recevoir les cookies
        corsConfig.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfig)
        return source
    }

    // Pour obtenir l’AuthenticationManager au besoin
    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    // PasswordEncoder pour hasher/valider les mots de passe
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    // Provider qui va chercher l'utilisateur (UserDetailsService) et comparer le mot de passe
    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }
}
