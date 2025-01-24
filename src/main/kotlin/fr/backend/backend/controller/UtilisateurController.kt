package fr.backend.backend.controller
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import fr.backend.backend.dto.UtilisateurDto
import fr.backend.backend.service.UtilisateurService


@RestController
@RequestMapping("/api/utilisateurs")
class UtilisateurController(
    private val utilisateurService: UtilisateurService
) {

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        fun createUtilisateur(@RequestBody utilisateurDto: UtilisateurDto): UtilisateurDto {
            return utilisateurService.createUtilisateur(utilisateurDto)
        }

        @GetMapping("/{id}")
        fun getUtilisateurById(@PathVariable id: UUID): UtilisateurDto {
            return utilisateurService.getUtilisateurById(id)
        }

        @GetMapping
        fun getAllUtilisateurs(): List<UtilisateurDto> {
            return utilisateurService.getAllUtilisateurs()
        }

        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        fun deleteUtilisateur(@PathVariable id: UUID) {
            utilisateurService.deleteUtilisateur(id)
        }

}
