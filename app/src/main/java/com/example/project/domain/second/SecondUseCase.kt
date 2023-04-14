package com.example.project.domain.second

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.example.project.data.local.localDB
import com.example.project.data.local.model.Recipe
import com.example.project.data.remote.MainRepository
import com.example.project.data.remote.model.Ingredients
import com.example.project.domain.second.model.SecondRecipe

object SecondUseCase{
    suspend fun getRecipe(index: Int): SecondRecipe {
        val recipe = localDB.getRecipe(index)
        return SecondRecipe(recipe.title, recipe.link, get_ingredients(recipe.ingredients), get_instuctions(recipe.instructions))
    }

    fun get_instuctions(instructions: String): String{
        var instructions = instructions
        val firstOldValues = arrayOf("\n", "</li>", "</p>", "<br>")
        val secondOldValues = arrayOf("<li>", "<ol>", "</ol>", "<strong>", "</strong>", "<p>", "<i>", "</i>", "<b>", "</b>")

        secondOldValues.forEach{ instructions = instructions.replace(it, "") }
        firstOldValues.forEach{ instructions = instructions.replace(it, "\n\n") }

        while(true){
            val oldString = instructions
            instructions = instructions.replace("\n\n\n", "\n\n")

            if(oldString == instructions){ break }
        }

        return instructions.trimEnd('\n')
    }

    fun get_ingredients(ingredients: List<String>): String{
        var text = ""
        ingredients.forEach{ text += it + ", " }

        return text[0].uppercase() + text.substring(1).dropLast(2) + "."
    }
}