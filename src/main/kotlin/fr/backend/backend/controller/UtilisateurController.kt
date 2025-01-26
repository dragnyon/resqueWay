package fr.backend.backend.controller

import fr.backend.backend.dto.UtilisateurDto
import fr.backend.backend.request.UtilisateurCreateRequest
import fr.backend.backend.service.UtilisateurService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api/utilisateurs")
class UtilisateurController(
    private val utilisateurService: UtilisateurService
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
}
