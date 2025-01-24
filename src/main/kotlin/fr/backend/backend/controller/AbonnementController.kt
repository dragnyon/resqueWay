package fr.backend.backend.controller

import fr.backend.backend.dto.AbonnementDTO
import fr.backend.backend.dto.HopitalDto
import fr.backend.backend.service.AbonnementService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api/abonnement")
class AbonnementController(

    private val abonnementService: AbonnementService

) {

    @GetMapping
    fun getAllAbonnements(): ResponseEntity<List<AbonnementDTO>>  {
        val abonnements = abonnementService.getAllAbonnement()
        return ResponseEntity.ok(abonnements)
    }

    @GetMapping("/{id}")
    fun getAbonnementById(@PathVariable id: UUID): ResponseEntity<AbonnementDTO> {
        val abonnement = abonnementService.getAbonnementById(id)
        return ResponseEntity.ok(abonnement)
    }

    @PostMapping
    fun createAbonnement(@RequestBody abonnementDTO: AbonnementDTO): ResponseEntity<AbonnementDTO> {
        val newAbonnement = abonnementService.createAbonnement(abonnementDTO)
        return ResponseEntity(newAbonnement, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateAbonnement(
        @PathVariable id: UUID,
        @RequestBody abonnementDTO: AbonnementDTO
    ): ResponseEntity<AbonnementDTO> {
        val updatedAbonnement = abonnementService.updateAbonnement(id, abonnementDTO)
        return ResponseEntity.ok(updatedAbonnement)
    }

    @DeleteMapping("/{id}")
    fun deleteAbonnement(@PathVariable id: UUID): ResponseEntity<Void> {
        abonnementService.deleteAbonnement(id)
        return ResponseEntity.noContent().build()
    }




}