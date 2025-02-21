package fr.backend.backend.controller

import fr.backend.backend.dto.EntrepriseDTO
import fr.backend.backend.request.EntrepriseCreateRequest
import fr.backend.backend.service.EntrepriseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api/entreprise")
class EntrepriseController(

    private val entrepriseService: EntrepriseService
) {

    @GetMapping("/all")
    fun getAllEntreprises(): ResponseEntity<List<EntrepriseDTO>> {
        val entreprises = entrepriseService.getAllEntreprise()
        return ResponseEntity.ok(entreprises)
    }

    @GetMapping("get/{id}")
    fun getEntrepriseById(@PathVariable id: UUID): ResponseEntity<EntrepriseDTO> {
        val entreprise = entrepriseService.getEntrepriseById(id)
        return ResponseEntity.ok(entreprise)
    }

    @PostMapping("/create")
    fun createEntreprise(@RequestBody entrepriseDTO: EntrepriseCreateRequest): ResponseEntity<EntrepriseDTO> {
        val newEntreprise = entrepriseService.createEntreprise(entrepriseDTO)
        return ResponseEntity(newEntreprise, HttpStatus.CREATED)
    }

    @PutMapping("update/{id}")
    fun updateEntreprise(
        @PathVariable id: UUID,
        @RequestBody entrepriseDTO: EntrepriseCreateRequest
    ): ResponseEntity<EntrepriseDTO> {
        val updatedEntreprise = entrepriseService.updateEntreprise(id, entrepriseDTO)
        return ResponseEntity.ok(updatedEntreprise)
    }

    @DeleteMapping("delete/{id}")
    fun deleteEntreprise(@PathVariable id: UUID): ResponseEntity<Void> {
        entrepriseService.deleteEntreprise(id)
        return ResponseEntity.noContent().build()
    }

}