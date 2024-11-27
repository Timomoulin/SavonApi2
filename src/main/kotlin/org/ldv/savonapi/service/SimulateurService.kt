package org.ldv.savonapi.service

import org.ldv.savonapi.dto.RecetteFormDTO
import org.ldv.savonapi.model.dao.CaracteristiqueDAO
import org.ldv.savonapi.model.dao.RecetteDAO
import org.ldv.savonapi.model.entity.LigneIngredient
import org.ldv.savonapi.model.entity.Recette
import org.ldv.savonapi.model.entity.Resultat
import org.ldv.savonapi.model.id.LigneIngredientId
import org.ldv.savonapi.model.id.ResultatId
import org.springframework.stereotype.Service

@Service
class SimulateurService(val caracteristiqueDAO: CaracteristiqueDAO,val recetteDAO: RecetteDAO) {

    fun toRecette(recetteFormDTO: RecetteFormDTO): Recette {
        var recette = Recette(
            null,
            recetteFormDTO.tite,
            recetteFormDTO.description,
            recetteFormDTO.surgraissage,
            0f,
            recetteFormDTO.avecSoude,
            recetteFormDTO.concentrationAlcalin,
            recetteFormDTO.qteAlcalin,
            recetteFormDTO.alcalinEstSolide,
            recetteFormDTO.ligneIngredients
        )
        recette=recetteDAO.save(recette)
        recette.resultats.addAll(this.creationResultat(recette))
        recette.calculPondere()
        recette.calculNonPondere()
        //TODO qteEau
        return recetteDAO.save(recette)
    }




    fun creationResultat(recette: Recette): List<Resultat> {
        var resultats: MutableList<Resultat> = mutableListOf()
        var caracteristique = caracteristiqueDAO.findAll()

        for (c in caracteristique) {
            resultats.add(Resultat(resultatId = ResultatId(c.id!!, recette.id!!), 0f))
        }
        return resultats
    }
}