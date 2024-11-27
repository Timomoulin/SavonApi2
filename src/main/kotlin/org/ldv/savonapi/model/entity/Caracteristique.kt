package org.ldv.savonapi.model.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
class Caracteristique (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var nom:String,
    @JsonBackReference
    @OneToMany(mappedBy = "caracteristique", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var mentions: MutableList<Mention> = mutableListOf(),
    @JsonBackReference
    @OneToMany(mappedBy = "caracteristique", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var resultats: MutableList<Resultat> = mutableListOf()
) {


}