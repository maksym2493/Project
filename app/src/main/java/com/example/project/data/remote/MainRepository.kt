package com.example.project.data.remote

import android.graphics.drawable.Drawable
import android.util.Log
import com.example.project.data.local.localDB
import com.example.project.data.local.model.Recipe
import com.example.project.data.remote.model.Recipes
import com.example.project.data.remote.model.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ApiInterface{
    @GET("/recipes/random?apiKey=70896c3d78f9493089d5a9f7b5a6124c")
    suspend fun getRecipes(@Query("number") number: Int): Response<Recipes>
}

interface Downloader{
    @GET
    suspend fun getImage(@Url getUrl: String): Response<ResponseBody>
}

object MainRepository{
    private val apiInterface = RetrofitClient.getInstance("https://api.spoonacular.com").create(ApiInterface::class.java)

    suspend fun getRecipes(): List<Recipe>{
        var times = 0
        var response = localDB.getRecipes()
        while(response.size < 10 && times++ != 5){
            var recipes = apiInterface.getRecipes(10 - response.size).body()!!.recipes

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

    suspend fun getDrawable(index: Int): Drawable?{
        var drawable = localDB.getDrawable(index)
        if(drawable == null){
            val url = localDB.getRecipe(index).image
            val index_2 = url.lastIndexOf("/") + 1

            val getUrl = url.substring(index_2)
            val baseUrl = url.substring(0, index_2)
            val downloader = RetrofitClient.getInstance(baseUrl).create(Downloader::class.java)

            val file = localDB.getFile(index)
            val inputStream = downloader.getImage(getUrl).body()!!.byteStream()

            file.outputStream().use{output ->
                var length = 0
                val buffer = ByteArray(1024)

                while(inputStream.read(buffer).also{ length = it } != -1){ output.write(buffer, 0, length) }

                output.flush()
            }

            drawable = localDB.getDrawable(index)
        }

        return drawable
    }
}