package org.ldv.savonapi.model.entity

import jakarta.persistence.*

@Entity
class Ingredient(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var nom:String,
    var iode:Float,
    var ins:Float,
    var volMousse:Float,
    var tenueMousse:Float,
    var douceur:Float,
    var lavant:Float,
    var durete:Float,
    var solubilite:Float,
    var sechage:Float,
    var estCorpsGras:Boolean=true,
    @OneToMany(mappedBy = "ingredient", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var ligneIngredients: MutableList<LigneIngredient> = mutableListOf()

) {

}