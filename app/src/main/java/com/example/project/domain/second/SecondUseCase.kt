package com.example.project.domain.second

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.example.project.data.local.localDB
import com.example.project.data.local.model.Recipe
import com.example.project.data.remote.MainRepository
import com.example.project.data.remote.model.Ingredients

object SecondUseCase{
    fun getRecipe(index: Int): Array<String>{
        val recipe = localDB.getRecipe(index)

        return arrayOf(recipe.title, get_instuctions(recipe.instructions), recipe.image, get_ingredients(recipe.ingredients))
    }

    suspend fun getDrawable(index: Int): Drawable?{
        return MainRepository.getDrawable(index)
    }

    fun get_instuctions(instructions: String): String{
        var instructions = instructions
        val firstOldValues = arrayOf("\n", "</li>", "</p>")
        val secondOldValues = arrayOf("<li>", "<ol>", "</ol>", "<strong>", "</strong>", "<p>")

        secondOldValues.forEach{ instructions = instructions.replace(it, "") }
        firstOldValues.forEach{ instructions = instructions.replace(it, "\n\n") }

        return instructions.trimEnd('\n')
    }

    fun get_ingredients(ingredients: List<String>): String{
        var text = ""
        ingredients.forEach{ text += it + ", " }

        return text[0].uppercase() + text.substring(1).dropLast(2) + "."
    }
}