package com.example.project.data.remote

import android.util.Log
import com.example.project.data.local.localDB
import com.example.project.data.local.model.Recipe
import com.example.project.data.remote.model.Recipes
import com.example.project.data.remote.model.RetrofitClient
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface{
    @GET("/recipes/random?apiKey=70896c3d78f9493089d5a9f7b5a6124c&number=10")
    suspend fun getRecipes(): Response<Recipes>
}

object MainRepository{
    private val apiInterface = RetrofitClient.getInstance().create(ApiInterface::class.java)

    suspend fun getRecipes(): List<Recipe>{
        var times = 0
        var response = localDB.getRecipes()
        while(response.size < 10 && times++ != 5){
            var recipes = apiInterface.getRecipes().body()!!.recipes

            recipes.forEach{
                if(it.image != null){
                    var ingredients = ArrayList<String>()
                    it.ingredients.forEach{ ingredients.add(it.name) }

                    Log.d("MyLog", it.image)

                    val recipe = Recipe(
                        it.title,
                        it.instructions,
                        it.image,
                        ingredients
                    )

                    if(!localDB.exist(recipe)){
                        localDB.add(recipe)
                        response.add(recipe)
                    }
                }
            }
        }

        return response
    }
}