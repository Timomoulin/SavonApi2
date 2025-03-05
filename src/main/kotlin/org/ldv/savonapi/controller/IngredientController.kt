package org.ldv.savonapi.controller

import org.ldv.savonapi.model.dao.IngredientDAO
import org.ldv.savonapi.model.entity.Ingredient
import org.ldv.savonapi.model.entity.Recette
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping("/api-savon/v1/ingredient")
class IngredientController (val ingredientDAO: IngredientDAO){

    @GetMapping
    fun index(): List<Ingredient> {
        return this.ingredientDAO.findAll()
    }
}