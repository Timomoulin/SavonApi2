package org.ldv.savonapi.controller

import org.ldv.savonapi.dto.RecetteFormDTO
import org.ldv.savonapi.model.dao.RecetteDAO
import org.ldv.savonapi.model.entity.Exemple
import org.ldv.savonapi.model.entity.Recette
import org.ldv.savonapi.service.SimulateurService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api-savon/v1/recette")
class RecetteController (val simulateurService: SimulateurService,val recetteDAO: RecetteDAO) {
    @GetMapping
    fun index():List<Recette>{
        return this.recetteDAO.findAll()
    }

    @PostMapping
    fun store(@RequestBody recetteFormDTO: RecetteFormDTO): ResponseEntity<Recette> {
        val savedRecette = this.simulateurService.toRecette(recetteFormDTO)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecette)
    }
}