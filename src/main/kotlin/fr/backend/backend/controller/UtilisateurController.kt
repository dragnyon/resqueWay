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
        val authHeader = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw RuntimeException("Token manquant ou mal formé")
        }
        val token = authHeader.substring(7)
        val entrepriseIdStr = jwtUtil.extractEntreprise(token) // Méthode que vous aurez ajoutée dans JwtUtil
        val entrepriseId = UUID.fromString(entrepriseIdStr)
        return utilisateurService.findByEntrepriseId(entrepriseId)
    }




}
