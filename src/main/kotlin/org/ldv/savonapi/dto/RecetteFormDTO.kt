package org.ldv.savonapi.dto

import org.ldv.savonapi.model.entity.LigneIngredient

class RecetteFormDTO (
    var id: Long? = null,
    var tite: String,
    var description: String,
    var surgraissage: Float,
    var avecSoude: Boolean,
    var concentrationAlcalin: Float,
    var ligneIngredients: MutableList<LigneIngredientDTO> = mutableListOf(),
) {
}