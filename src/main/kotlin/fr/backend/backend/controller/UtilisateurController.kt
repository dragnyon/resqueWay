package fr.backend.backend.controller

import fr.backend.backend.dto.UtilisateurDto
import fr.backend.backend.service.UtilisateurService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/utilisateurs")
class UtilisateurController(
    private val utilisateurService: UtilisateurService
) {

    @GetMapping
    fun getAllUtilisateurs(): ResponseEntity<List<UtilisateurDto>> {
        val utilisateurs = utilisateurService.getAllUtilisateurs()
        return ResponseEntity.ok(utilisateurs)
    }

    @GetMapping("/{id}")
    fun getUtilisateurById(@PathVariable id: UUID): ResponseEntity<UtilisateurDto> {
        val utilisateur = utilisateurService.getUtilisateurById(id)
        return ResponseEntity.ok(utilisateur)
    }

    @PostMapping
    fun createUtilisateur(@RequestBody utilisateurDto: UtilisateurDto): ResponseEntity<UtilisateurDto> {
        val newUtilisateur = utilisateurService.createUtilisateur(utilisateurDto)
        return ResponseEntity(newUtilisateur, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateUtilisateur(
        @PathVariable id: UUID,
        @RequestBody utilisateurDto: UtilisateurDto
    ): ResponseEntity<UtilisateurDto> {
        val updatedUtilisateur = utilisateurService.updateUtilisateur(id, utilisateurDto)
        return ResponseEntity.ok(updatedUtilisateur)
    }

    @DeleteMapping("/{id}")
    fun deleteUtilisateur(@PathVariable id: UUID): ResponseEntity<Void> {
        utilisateurService.deleteUtilisateur(id)
        return ResponseEntity.noContent().build()
    }
}
