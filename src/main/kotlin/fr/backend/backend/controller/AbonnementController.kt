package fr.backend.backend.controller

import fr.backend.backend.dto.AbonnementDTO
import fr.backend.backend.dto.HopitalDto
import fr.backend.backend.request.AbonnementCreateRequest
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

    @GetMapping("/all")
    fun getAllAbonnements(): ResponseEntity<List<AbonnementDTO>>  {
        val abonnements = abonnementService.getAllAbonnement()
        return ResponseEntity.ok(abonnements)
    }

    @GetMapping("get/{id}")
    fun getAbonnementById(@PathVariable id: UUID): ResponseEntity<AbonnementDTO> {
        val abonnement = abonnementService.getAbonnementById(id)
        return ResponseEntity.ok(abonnement)
    }

    @PostMapping("/create")
    fun createAbonnement(@RequestBody abonnementDTO: AbonnementCreateRequest): ResponseEntity<AbonnementDTO> {
        val newAbonnement = abonnementService.createAbonnement(abonnementDTO)
        return ResponseEntity(newAbonnement, HttpStatus.CREATED)
    }

    @PutMapping("update/{id}")
    fun updateAbonnement(
        @PathVariable id: UUID,
        @RequestBody abonnementDTO: AbonnementCreateRequest
    ): ResponseEntity<AbonnementDTO> {
        val updatedAbonnement = abonnementService.updateAbonnement(id, abonnementDTO)
        return ResponseEntity.ok(updatedAbonnement)
    }

    @DeleteMapping("delete/{id}")
    fun deleteAbonnement(@PathVariable id: UUID): ResponseEntity<Void> {
        abonnementService.deleteAbonnement(id)
        return ResponseEntity.noContent().build()
    }




}