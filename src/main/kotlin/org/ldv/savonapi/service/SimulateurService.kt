package org.ldv.savonapi.service

import org.ldv.savonapi.dto.LigneIngredientDTO
import org.ldv.savonapi.dto.RecetteFormDTO
import org.ldv.savonapi.model.dao.*
import org.ldv.savonapi.model.entity.LigneIngredient
import org.ldv.savonapi.model.entity.Recette
import org.ldv.savonapi.model.entity.Resultat
import org.ldv.savonapi.model.id.LigneIngredientId
import org.ldv.savonapi.model.id.ResultatId
import org.springframework.stereotype.Service

@Service
class SimulateurService(
    val caracteristiqueDAO: CaracteristiqueDAO,
    val recetteDAO: RecetteDAO,
    val ingredientDAO: IngredientDAO,
    val ligneIngredientDAO: LigneIngredientDAO,
    val mentionDAO: MentionDAO,
    val resultatDAO: ResultatDAO
) {

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
        )
        recette=recetteDAO.save(recette)
        for (ligneDTO in recetteFormDTO.ligneIngredients){
            val ligne= this.ligneDTOToLigne(ligneDTO,recette)
            recette.ligneIngredients.add(ligne)
        }

        recette.resultats.addAll(this.creationResultat(recette))
        recette.calculPondere()
        recette.calculNonPondere()
        //TODO qteEau
        ligneIngredientDAO.saveAll(recette.ligneIngredients)
        this.assignMentionsToResults(recette)
        resultatDAO.saveAll(recette.resultats)
        return recetteDAO.save(recette)
    }


fun ligneDTOToLigne( ligneIngredientDTO: LigneIngredientDTO,recette: Recette):LigneIngredient{
    val ingredient = ingredientDAO.findById(ligneIngredientDTO.ingredientId)
    val ligneIngredientId = LigneIngredientId(ligneIngredientDTO.ingredientId, recette.id!!)
    val savedLigne= LigneIngredient(ligneIngredientId,ligneIngredientDTO.quantite,ligneIngredientDTO.pourcentage,ingredient.get(),recette)
    return savedLigne;
}

    fun creationResultat(recette: Recette): List<Resultat> {
        val resultats: MutableList<Resultat> = mutableListOf()
        val caracteristique = caracteristiqueDAO.findAll()

        for (c in caracteristique) {
            resultats.add(Resultat(resultatId = ResultatId(c.id!!, recette.id!!), 0f,recette,c))
        }
        return resultats
    }

    fun assignMentionsToResults(recette: Recette):Recette {
        recette.resultats.forEach { resultat ->
            val caracteristique = resultat.caracteristique

            if (caracteristique != null) {
                // Rechercher la mention correspondante directement via MentionRepository
                val mentionCorrespondante = mentionDAO.findMentionByScoreAndCaracteristique(
                    score = resultat.score,
                    caracteristique = caracteristique
                )

                // Assigner la mention trouvée au résultat
                if (mentionCorrespondante != null) {
                    resultat.mention = mentionCorrespondante
                } else {
                    println("Aucune mention trouvée pour le score ${resultat.score} et la caractéristique ${caracteristique.nom}")
                }
            }
        }
        return recette
    }

    }