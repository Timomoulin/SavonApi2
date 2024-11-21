package org.ldv.savonapi.model.entity

import jakarta.persistence.*

@Entity
class Exemple(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long?,
    var nom: String,
    //...
) {

}