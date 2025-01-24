package fr.backend.backend.mapper

import fr.backend.backend.dto.AbonnementDTO
import fr.backend.backend.dto.EntrepriseDTO
import fr.backend.backend.model.Abonnement
import fr.backend.backend.model.Entreprise
import org.springframework.stereotype.Component

@Component
class EntrepriseMapper {

    fun toEntity(dto: EntrepriseDTO, abo: Abonnement?): Entreprise {

        return Entreprise(
            idEntreprise = dto.id,
            adresse = dto.adresse,
            adresseMail = dto.mail,
            abonnement = abo,
            password = dto.password
        )
    }



    fun toDTO(entity: Entreprise): EntrepriseDTO {
        return EntrepriseDTO(
            id = entity.idEntreprise,
            abonnement = entity.abonnement?.id,
            adresse = entity.adresse,
            mail = entity.adresseMail,
            password = entity.password

        )
    }


}