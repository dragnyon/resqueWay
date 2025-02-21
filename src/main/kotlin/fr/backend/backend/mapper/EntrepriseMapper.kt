package fr.backend.backend.mapper

import fr.backend.backend.dto.AbonnementDTO
import fr.backend.backend.dto.EntrepriseDTO
import fr.backend.backend.model.Abonnement
import fr.backend.backend.model.Entreprise
import fr.backend.backend.request.EntrepriseCreateRequest
import org.springframework.stereotype.Component

@Component
class EntrepriseMapper {

    fun toEntity(dto: EntrepriseCreateRequest, abo: Abonnement?): Entreprise {

        return Entreprise(
            name = dto.name,
            adresse = dto.adresse,
            adresseMail = dto.mail,
            abonnement = abo?.let { abo },
            password = dto.password
        )
    }



    fun toDTO(entity: Entreprise): EntrepriseDTO {
        return EntrepriseDTO(
            id = entity.id,
            name = entity.name,
            abonnement = entity.abonnement?.id,
            adresse = entity.adresse,
            mail = entity.adresseMail,
            password = ""

        )
    }


}