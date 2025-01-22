package fr.backend.backend.controller

import fr.backend.backend.dto.HopitalDTO
import fr.backend.backend.service.HopitalService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/hopitaux")
class HopitalController(
    private val hopitalService: HopitalService
) {

    @GetMapping
    fun getAllHopitaux(): ResponseEntity<List<HopitalDTO>> {
        val hopitaux = hopitalService.getAllHopitaux()
        return ResponseEntity.ok(hopitaux)
    }

    @PostMapping
    fun createHopital(@RequestBody hopitalDTO: HopitalDTO): ResponseEntity<HopitalDTO> {
        val createdHopital = hopitalService.createHopital(hopitalDTO)
        return ResponseEntity.ok(createdHopital)
    }

    @GetMapping("/{id}")
    fun getHopitalById(@PathVariable id: Long): ResponseEntity<HopitalDTO> {
        val hopital = hopitalService.getHopitalById(id)
        return ResponseEntity.ok(hopital)
    }
}
