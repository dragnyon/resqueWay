package fr.backend.backend.controller

import fr.backend.backend.dto.HopitalDto
import fr.backend.backend.service.HopitalService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/hopitaux")
class HopitalController(
    private val hopitalService: HopitalService
) {

    @GetMapping
    fun getAllHopitaux(): ResponseEntity<List<HopitalDto>> {
        val hopitaux = hopitalService.getAllHopitaux()
        return ResponseEntity.ok(hopitaux)
    }

    @GetMapping("/{id}")
    fun getHopitalById(@PathVariable id: UUID): ResponseEntity<HopitalDto> {
        val hopital = hopitalService.getHopitalById(id)
        return ResponseEntity.ok(hopital)
    }

    @PostMapping
    fun createHopital(@RequestBody hopitalDto: HopitalDto): ResponseEntity<HopitalDto> {
        val newHopital = hopitalService.createHopital(hopitalDto)
        return ResponseEntity(newHopital, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateHopital(
        @PathVariable id: UUID,
        @RequestBody hopitalDto: HopitalDto
    ): ResponseEntity<HopitalDto> {
        val updatedHopital = hopitalService.updateHopital(id, hopitalDto)
        return ResponseEntity.ok(updatedHopital)
    }

    @DeleteMapping("/{id}")
    fun deleteHopital(@PathVariable id: UUID): ResponseEntity<Void> {
        hopitalService.deleteHopital(id)
        return ResponseEntity.noContent().build()
    }
}
