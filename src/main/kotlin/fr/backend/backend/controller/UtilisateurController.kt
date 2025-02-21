package fr.backend.backend.controller

import fr.backend.backend.dto.UtilisateurDto
import fr.backend.backend.request.UtilisateurCreateRequest
import fr.backend.backend.service.UtilisateurService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*
import fr.backend.backend.security.JwtUtil


@RestController
@RequestMapping("/api/utilisateurs")
class UtilisateurController(
    private val utilisateurService: UtilisateurService,
    private val jwtUtil: JwtUtil,
) {

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUtilisateur(@RequestBody utilisateurDto: UtilisateurCreateRequest): UtilisateurDto {
        return utilisateurService.createUtilisateur(utilisateurDto)
    }

    @GetMapping("/{id}")
    fun getUtilisateurById(@PathVariable id: UUID): UtilisateurDto {
        return utilisateurService.getUtilisateurById(id)
    }

    @GetMapping("/all")
    fun getAllUtilisateurs(): List<UtilisateurDto> {
        return utilisateurService.getAllUtilisateurs()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteUtilisateur(@PathVariable id: UUID) {
        return utilisateurService.deleteUtilisateur(id)
    }

    @PutMapping("update/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateUtilisateur(
        @PathVariable id: UUID,
        @RequestBody utilisateurDto: UtilisateurCreateRequest
    ): UtilisateurDto {
        return utilisateurService.updateUtilisateur(id, utilisateurDto)
    }

    @GetMapping("/getbycompany/{id}")
    fun getUtilisateursByEntrepris(
        @PathVariable id: UUID
    ): List<UtilisateurDto> {
        return utilisateurService.findByEntrepriseId(id)
    }

    @GetMapping("/getbycompany")
    fun getUtilisateursByEntreprise(request: HttpServletRequest): List<UtilisateurDto> {
        // Récupère le cookie "access_token"
        val tokenCookie = request.cookies?.firstOrNull { it.name == "access_token" }
            ?: throw RuntimeException("Token manquant ou mal formé")
        val token = tokenCookie.value

        // Extraction de l'ID de l'entreprise depuis la claim "entreprise"
        val entrepriseIdStr = jwtUtil.extractEntreprise(token)
            ?: throw RuntimeException("Entreprise introuvable dans le token")
        val entrepriseId = UUID.fromString(entrepriseIdStr)

        // Retourne la liste des utilisateurs correspondant à cet ID d'entreprise
        return utilisateurService.findByEntrepriseId(entrepriseId)
    }





}
