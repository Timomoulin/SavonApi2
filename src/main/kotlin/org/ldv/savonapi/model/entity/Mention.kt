package org.ldv.savonapi.model.entity

import jakarta.persistence.*


@Entity
class Mention(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var label: String,
    var noteMin: Float,
    var noteMax: Float,
    @ManyToOne
    @JoinColumn(name = "caracteristique_id")
    var caracteristique: Caracteristique? = null,

    @OneToMany(mappedBy = "mention", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var resultats: MutableList<Resultat> = mutableListOf()


) {


}