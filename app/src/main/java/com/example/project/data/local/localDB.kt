package com.example.project.data.local

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.project.data.local.model.Recipe
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object localDB{
    var pos = 0
    var loaded = false
    private var recipes = ArrayList<Recipe>()

    private lateinit var filesDir: File

    fun setFilesDir(filesDir: File){ this.filesDir = filesDir }

    @JvmName("getRecipes")
    fun getRecipes(): ArrayList<Recipe>{
        if(!loaded){ load(); loaded = true }

        var count = 0
        val size = recipes.size
        var response = ArrayList<Recipe>()
        while(pos < size && count < 10){ response.add(recipes[pos]); pos++; count++ }

        return response
    }

    fun getRecipe(index: Int): Recipe{
        return recipes[index]
    }

    fun getDrawable(index: Int): Drawable?{
        var drawable: Drawable? = null
        val file = File(filesDir, index.toString())
        if(file.exists()){ drawable = Drawable.createFromPath(file.path) }

        return drawable
    }

    fun getFile(index: Int): File{
        return File(filesDir, index.toString())
    }

    fun add(recipe: Recipe){ recipes.add(recipe); save() }

    fun exist(recipe: Recipe): Boolean{ return recipe in recipes }

    fun load(){
        val file = File(filesDir, "DB")
        if(file.exists()){
            Log.d("MyLog", "Size: " + file.readText().split("\r\r").size.toString())
            file.readText().split("\r\r").forEach{
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

            Log.d("MyLog", "Title: " + it.title.length.toString())
            Log.d("MyLog", "Instructions: " + it.instructions.length.toString())
            Log.d("MyLog", "Image: " + it.image.length.toString())
            Log.d("MyLog", "Ingredients: " + ingredients.length.toString() + "\n")

            text += "\r\r" + it.title + "\r" + it.instructions + "\r" + it.image + "\r" + ingredients.dropLast(1)
        }

        File("/storage/emulated/0/log.txt").writeText(text.substring(2))
        File(filesDir, "DB").writeText(text.substring(2))
    }
}