package com.example.project.domain.main

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.example.project.data.local.localDB
import com.example.project.data.local.model.Recipe
import com.example.project.data.remote.MainRepository
import com.example.project.domain.main.model.MainRecipe
import java.io.File

object MainUseCase{
    private val repo = MainRepository

    suspend fun getRecipes(count: Int): ArrayList<MainRecipe>{
        var recipes = ArrayList<MainRecipe>()
        repo.getRecipes(count).forEach{ recipes.add(MainRecipe(it.title, it.link, get_ingredients(it.ingredients))) }

        return recipes
    }

    fun get_ingredients(ingredients: List<String>): String{
        var text = ""
        ingredients.forEach{ text += it + ", " }

        return "Ingredients: " + text.dropLast(2) + "."
    }

    fun resetLocalPos(){ localDB.resetLocalPos() }

    fun setFilesDir(filesDir: File){ localDB.setFilesDir(filesDir) }
}