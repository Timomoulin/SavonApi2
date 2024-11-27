package org.ldv.savonapi.service
import org.ldv.savonapi.model.dao.CaracteristiqueDAO
import org.ldv.savonapi.model.dao.IngredientDAO
import org.ldv.savonapi.model.dao.MentionDAO
import org.ldv.savonapi.model.entity.Caracteristique
import org.ldv.savonapi.model.entity.Ingredient
import org.ldv.savonapi.model.entity.Mention
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
@Component
class DataInitializer (val ingredientDAO: IngredientDAO, val caracteristiqueDAO: CaracteristiqueDAO,val mentionDAO: MentionDAO) : CommandLineRunner {
    override fun run(vararg args: String?) {
        //Pour importer les ingredients
        if (ingredientDAO.count() == 0L) { // Éviter les doublons
            val coco = Ingredient(
                nom = "Coco",
                iode = 9f,
                ins = 248f,
                volMousse = 13.326f,
                tenueMousse = 9.560f,
                lavant = 14.462f,
                douceur = 7.746f,
                durete = 9.390f,
                solubilite = 11.204f,
                sechage = 11.880f,
                estCorpsGras = true
            )

            val olive = Ingredient(
                nom = "Olive",
                iode = 78f,
                ins = 111f,
                lavant = 10.192f,
                volMousse = 9.838f,
                tenueMousse = 9.152f,
                douceur = 9.260f,
                durete = 10.144f,
                solubilite = 9.298f,
                sechage = 10.194f,
                estCorpsGras = true
            )
            var ingredients = listOf<Ingredient>(olive,coco)
            ingredientDAO.saveAll(ingredients)
        }
        if (caracteristiqueDAO.count() == 0L) {

            val iode = Caracteristique(
                id = 1,
                nom = "Iode"
            )

            val ins = Caracteristique(
                id = 2,
                nom = "Indice INS"
            )

            val douceur = Caracteristique(
                id = 3,
                nom = "Douceur"
            )

            val lavant = Caracteristique(
                id = 4,
                nom = "Lavant"
            )

            val volMousse = Caracteristique(
                id = 5,
                nom = "Volume de mousse"
            )

            val tenueMousse = Caracteristique(
                id = 6,
                nom = "Tenue de mousse"
            )

            val durete = Caracteristique(
                id = 7,
                nom = "Dureté"
            )

            val solubilite = Caracteristique(
                id = 8,
                nom = "Solubilité"
            )

            val sechage = Caracteristique(
                id = 9,
                nom = "Séchage"
            )
            val liste= mutableListOf(iode,ins,douceur,lavant,volMousse,tenueMousse,solubilite,sechage)
            caracteristiqueDAO.saveAll(liste)
        }
        if (mentionDAO.count() == 0L) {
            // Récupérez les caractéristiques précédemment sauvegardées
            val caracteristiques = caracteristiqueDAO.findAll()

            // Création des mentions pour chaque caractéristique
            val mentions = mutableListOf<Mention>()

            caracteristiques.forEach { caracteristique ->
                when (caracteristique.nom) {
                    "Iode" -> {
                        mentions.add(Mention(label = "Très faible", noteMin = 0f, noteMax = 30f, caracteristique = caracteristique))
                        mentions.add(Mention(label = "Faible", noteMin = 31f, noteMax = 70f, caracteristique = caracteristique))
                        mentions.add(Mention(label = "Élevé", noteMin = 71f, noteMax = 100f, caracteristique = caracteristique))
                    }
                    "Indice INS" -> {
                        mentions.add(Mention(label = "Faible", noteMin = 0f, noteMax = 100f, caracteristique = caracteristique))
                        mentions.add(Mention(label = "Optimal", noteMin = 101f, noteMax = 160f, caracteristique = caracteristique))
                        mentions.add(Mention(label = "Trop élevé", noteMin = 161f, noteMax = 200f, caracteristique = caracteristique))
                    }
                    "Douceur" -> {
                        mentions.add(Mention(label = "Insuffisante", noteMin = 0f, noteMax = 5f, caracteristique = caracteristique))
                        mentions.add(Mention(label = "Bonne", noteMin = 6f, noteMax = 10f, caracteristique = caracteristique))
                    }
                    "Lavant" -> {
                        mentions.add(Mention(label = "Faible", noteMin = 0f, noteMax = 7f, caracteristique = caracteristique))
                        mentions.add(Mention(label = "Excellent", noteMin = 8f, noteMax = 15f, caracteristique = caracteristique))
                    }
                    "Volume de mousse" -> {
                        mentions.add(Mention(label = "Faible", noteMin = 0f, noteMax = 8f, caracteristique = caracteristique))
                        mentions.add(Mention(label = "Optimal", noteMin = 9f, noteMax = 15f, caracteristique = caracteristique))
                    }
                    "Tenue de mousse" -> {
                        mentions.add(Mention(label = "Peu stable", noteMin = 0f, noteMax = 5f, caracteristique = caracteristique))
                        mentions.add(Mention(label = "Stable", noteMin = 6f, noteMax = 10f, caracteristique = caracteristique))
                    }
                    "Dureté" -> {
                        mentions.add(Mention(label = "Mou", noteMin = 0f, noteMax = 5f, caracteristique = caracteristique))
                        mentions.add(Mention(label = "Dur", noteMin = 6f, noteMax = 10f, caracteristique = caracteristique))
                    }
                    "Solubilité" -> {
                        mentions.add(Mention(label = "Faible", noteMin = 0f, noteMax = 5f, caracteristique = caracteristique))
                        mentions.add(Mention(label = "Bonne", noteMin = 6f, noteMax = 10f, caracteristique = caracteristique))
                    }
                    "Séchage" -> {
                        mentions.add(Mention(label = "Lent", noteMin = 0f, noteMax = 5f, caracteristique = caracteristique))
                        mentions.add(Mention(label = "Rapide", noteMin = 6f, noteMax = 10f, caracteristique = caracteristique))
                    }
                }
            }

            // Sauvegardez toutes les mentions
            mentionDAO.saveAll(mentions)
        }



    }
}