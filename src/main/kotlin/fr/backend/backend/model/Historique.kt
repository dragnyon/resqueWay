package fr.backend.backend.model



import jakarta.persistence.*
import lombok.NoArgsConstructor
import java.io.Serializable
import java.time.LocalDateTime


@Entity
@NoArgsConstructor
data class Historique(
    @EmbeddedId
    val id: HistoriqueId,

    @Column(name = "file", nullable = false)
    val file: String
)

@Embeddable
data class HistoriqueId(
    @ManyToOne
    @JoinColumn(name = "id_entreprise", nullable = false)
    val entreprise: Entreprise,

    @Column(name = "date_facture", nullable = false)
    val dateFacture: LocalDateTime
) : Serializable