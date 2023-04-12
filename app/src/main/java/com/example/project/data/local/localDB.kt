package com.example.project.data.local

import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.project.data.local.model.Recipe
import java.io.File

object localDB{
    var pos = 0
    var loaded = false
    private var recipes = ArrayList<Recipe>()

    private lateinit var file: File

    fun setFilesDir(filesDir: File){ file = File(filesDir, "LB") }

    @JvmName("getRecipes")
    fun getRecipes(): ArrayList<Recipe>{
        if(!loaded){ load(); loaded = true }

        val size = recipes.size
        var response = ArrayList<Recipe>()
        while(pos < size){ response.add(recipes[pos]); pos++ }

        return response
    }

    fun getRecipe(index: Int): Recipe{
        return recipes[index]
    }

    fun add(recipe: Recipe){ recipes.add(recipe); save() }

    fun exist(recipe: Recipe): Boolean{ return recipe in recipes }

    fun load(){
        if(file.exists()){
            file.readText().split("\r\r").forEach{
                Log.d("MyLog", it)

                val recipe = it.split("\r")
                val ingredients = ArrayList<String>()

                recipe[3].split(",").forEach{ ingredients.add(it) }
                recipes.add(Recipe(recipe[0], recipe[1], recipe[2], ingredients))
            }
        }
    }

    fun save(){
        var text = ""
        recipes.forEach{
            var ingredients = ""
            it.ingredients.forEach{ ingredients += it + "," }

            text += "\r\r" + it.title + "\r" + it.instructions + "\r" + it.image + "\r" + ingredients.substring(0, ingredients.length - 1)
        }

        file.writeText(text.substring(2))
    }
}