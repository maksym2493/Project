package com.example.project.data.remote

import android.graphics.drawable.Drawable
import android.util.Log
import com.example.project.data.local.localDB
import com.example.project.data.local.model.Recipe
import com.example.project.data.remote.model.ApiInterface
import com.example.project.data.remote.model.Recipes
import com.example.project.data.remote.model.RetrofitClient
import kotlinx.coroutines.delay
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

object MainRepository{
    private val apiInterface = RetrofitClient.getInstance("https://api.spoonacular.com").create(ApiInterface::class.java)

    suspend fun getRecipes(count: Int): List<Recipe>{
        var times = 0

        val response = localDB.getRecipes(count)
        val oldSize = response.size
        while(response.size < count && times++ != 5){
            try {
                val recipes = apiInterface.getRecipes(count).body()!!.recipes

                recipes.forEach{
                    if(it.image != null && it.instructions != ""){
                        val ingredients = ArrayList<String>()
                        it.ingredients.forEach{ ingredients.add(it.name) }

                        val recipe = Recipe(
                            it.title,
                            it.instructions,
                            it.image,
                            ingredients
                        )

                        if (!localDB.exist(recipe)) {
                            var add = 1
                            if(response.size < count){ response.add(recipe) } else{ add = 0 }

                            localDB.add(recipe, add)
                        }
                    }
                }
            } catch(e: Exception){ times--; delay(1000) }
        }

        if(oldSize != response.size){ localDB.save() }

        return response
    }
}
