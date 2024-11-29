package org.ldv.savonapi.model.entity

import jakarta.persistence.*

@Entity
class Recette(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var tite: String,
    var description: String,
    var surgraissage: Float,
    var qteEau: Float,
    var avecSoude: Boolean,
    var concentrationAlcalin: Float,
    var qteAlcalin: Float,
    var alcalinEstSolide: Boolean,

    @OneToMany(mappedBy = "recette", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var ligneIngredients: MutableList<LigneIngredient> = mutableListOf(),
    @OneToMany(mappedBy = "recette", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var resultats: MutableList<Resultat> = mutableListOf()

) {
    fun calculNonPondere() {
        val ins: Double = this.ligneIngredients.sumOf { it.ingredient!!.ins *it.pourcentage/100.toDouble() }
        val iode: Double = this.ligneIngredients.sumOf { it.ingredient!!.iode *it.pourcentage/100.toDouble() }
        this.resultats.find { it.caracteristique!!.nom == "Iode" }!!.score = iode.toFloat()
        this.resultats.find { it.caracteristique!!.nom == "Indice INS" }!!.score = ins.toFloat()
    }

    fun calculPondere() {
        //Calcul des scores
        var douceur: Double = this.ligneIngredients.sumOf { it.ingredient!!.douceur * it.pourcentage / 100.toDouble() }
        var lavant: Double = this.ligneIngredients.sumOf { it.ingredient!!.lavant * it.pourcentage / 100.toDouble() }
        var volMousse: Double = this.ligneIngredients.sumOf { it.ingredient!!.volMousse * it.pourcentage / 100.toDouble() }
        var tenueMousse: Double = this.ligneIngredients.sumOf { it.ingredient!!.tenueMousse * it.pourcentage / 100.toDouble() }
        var durete: Double = this.ligneIngredients.sumOf { it.ingredient!!.durete * it.pourcentage / 100.toDouble() }
        var solubilite: Double = this.ligneIngredients.sumOf { it.ingredient!!.solubilite * it.pourcentage / 100.toDouble() }
        var sechage: Double = this.ligneIngredients.sumOf { it.ingredient!!.sechage * it.pourcentage / 100.toDouble() }

        //Modification avec le surgraissage
        douceur= douceur*(1+0.01494*this.surgraissage)
        lavant= lavant*(1+-0.01203*this.surgraissage)
        volMousse=volMousse*(1+-0.00702*this.surgraissage)
        tenueMousse=tenueMousse*(1+0.01016*this.surgraissage)
        durete=durete*(1+-0.00602*this.surgraissage)
        solubilite=solubilite*(1+0.00250*this.surgraissage)
        sechage=sechage*(1+-0.00503*this.surgraissage)

        //Affectation aux resultats
        this.resultats.find { it.caracteristique!!.nom == "Douceur" }!!.score = douceur.toFloat()
        this.resultats.find { it.caracteristique!!.nom == "Lavant" }!!.score = lavant.toFloat()
        this.resultats.find { it.caracteristique!!.nom == "Volume de mousse" }!!.score = volMousse.toFloat()
        this.resultats.find { it.caracteristique!!.nom == "Tenue de mousse" }!!.score = tenueMousse.toFloat()
        this.resultats.find { it.caracteristique!!.nom == "Dureté" }!!.score = durete.toFloat()
        this.resultats.find { it.caracteristique!!.nom == "Solubilité" }!!.score = solubilite.toFloat()
        this.resultats.find { it.caracteristique!!.nom == "Séchage" }!!.score = sechage.toFloat()
    }

}