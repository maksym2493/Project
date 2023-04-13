package com.example.project.data.local

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.project.data.local.model.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
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
    fun getRecipes(c: Int): ArrayList<Recipe>{
        if(!loaded){ load(); loaded = true }

        var count = 0
        val size = recipes.size
        var response = ArrayList<Recipe>()
        while(pos < size && count < c){ response.add(recipes[pos]); pos++; count++ }

        return response
    }

    fun getRecipe(index: Int): Recipe{
        return recipes[index]
    }

    fun getDrawable(index: Int? = null, recipe: Recipe? = null): Drawable?{
        val file = File(filesDir, (if(index != null){ index } else{ recipes.indexOf(recipe!!) }).toString())

        return if(file.exists()){ Drawable.createFromPath(file.path) } else{ null }
    }

    fun getFile(index: Int? = null, recipe: Recipe? = null): File{
        return File(filesDir, (if(index != null){ index } else{ recipes.indexOf(recipe!!) }).toString())
    }

    fun add(recipe: Recipe, add: Int){ pos += add; recipes.add(recipe) }

    fun exist(recipe: Recipe): Boolean{ return recipe in recipes }

    fun load(){
        Log.d("MyLog", "Loaded.")
        val file = File(filesDir, "DB")
        if(file.exists()){
            file.readText().split("\r\r").forEach{
                val recipe = it.split("\r")
                val ingredients = ArrayList<String>()

                recipe[3].split(",").forEach{ ingredients.add(it) }
                recipes.add(Recipe(recipe[0], recipe[1], recipe[2], ingredients))
            }
        }
    }

    fun save(){
        CoroutineScope(Dispatchers.IO).launch{
            while(true) {
                try {
                    var text = ""
                    recipes.forEach {
                        var ingredients = ""
                        it.ingredients.forEach { ingredients += it + "," }

                        text += "\r\r" + it.title + "\r" + it.instructions + "\r" + it.image + "\r" + ingredients.dropLast(
                            1
                        )
                    }

                    File(filesDir, "DB").writeText(text.substring(2))

                    break
                } catch (_: Exception){}
            }
        }
    }
}