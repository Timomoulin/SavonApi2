package org.ldv.savonapi.controller

import org.ldv.savonapi.model.dao.ExempleDAO
import org.ldv.savonapi.model.entity.Exemple
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Contrôleur REST pour gérer les opérations CRUD sur l'entité Exemple.
 * Fournit des points d'entrée pour la manipulation des ressources Exemple.
 *
 * @param exempleDAO DAO pour accéder à la base de données des entités Exemple.
 */
@RestController
@CrossOrigin
@RequestMapping("/api-savon/v1/exemples")
class ExempleController( @Autowired private val exempleDAO: ExempleDAO) {

    /**
     * Récupère la liste de tous les exemples.
     *
     * @return Liste de toutes les entités Exemple disponibles.
     */
    @GetMapping
    fun getAllExemples(): List<Exemple> {
        return exempleDAO.findAll()
    }

    /**
     * Récupère un exemple par son identifiant.
     *
     * @param id Identifiant de l'exemple à récupérer.
     * @return L'entité Exemple si elle est trouvée, ou une réponse HTTP 404 si elle n'existe pas.
     */
    @GetMapping("/{id}")
    fun getExempleById(@PathVariable id: Long): ResponseEntity<Exemple> {
        val exemple = exempleDAO.findById(id)
        return if (exemple.isPresent) {
            ResponseEntity.ok(exemple.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * Crée un nouvel exemple.
     *
     * @param exemple Données de l'entité Exemple à créer.
     * @return L'entité Exemple créée avec un statut HTTP 201 (Created).
     */
    @PostMapping
    fun createExemple(@RequestBody exemple: Exemple): ResponseEntity<Exemple> {
        val savedExemple = exempleDAO.save(exemple)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExemple)
    }

    /**
     * Met à jour un exemple existant.
     *
     * @param id Identifiant de l'exemple à mettre à jour.
     * @param exemple Données de l'entité Exemple mises à jour.
     * @return L'entité Exemple mise à jour si elle existe, ou une réponse HTTP 404 si elle n'est pas trouvée.
     */
    @PutMapping("/{id}")
    fun updateExemple(@PathVariable id: Long, @RequestBody exemple: Exemple): ResponseEntity<Exemple> {
        return if (exempleDAO.existsById(id)) {
            exemple.id = id
            val updatedExemple = exempleDAO.save(exemple)
            ResponseEntity.ok(updatedExemple)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * Supprime un exemple par son identifiant.
     *
     * @param id Identifiant de l'exemple à supprimer.
     * @return Une réponse HTTP 204 (No Content) si la suppression est réussie, ou une réponse HTTP 404 si l'entité n'est pas trouvée.
     */
    @DeleteMapping("/{id}")
    fun deleteExemple(@PathVariable id: Long): ResponseEntity<Void> {
        return if (exempleDAO.existsById(id)) {
            exempleDAO.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}

