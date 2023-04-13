package com.example.project.domain.main

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.example.project.data.local.localDB
import com.example.project.data.local.model.Recipe
import com.example.project.data.remote.MainRepository
import com.example.project.domain.main.model.Image
import java.io.File

object MainUseCase{
    private val repo = MainRepository

    suspend fun getRecipes(count: Int): ArrayList<Array<String>>{
        var recipes = ArrayList<Array<String>>()
        repo.getRecipes(count).forEach{ recipes.add(arrayOf(it.title, get_ingredients(it.ingredients))) }

        return recipes
    }

    suspend fun getDrawable(view: ImageView, index: Int): Image{
        return Image(view, repo.getDrawable(index))
    }

    fun get_ingredients(ingredients: List<String>): String{
        var text = ""
        ingredients.forEach{ text += it + ", " }

        return "Ingredients: " + text.dropLast(2) + "."
    }

    fun setFilesDir(filesDir: File){ localDB.setFilesDir(filesDir) }
}