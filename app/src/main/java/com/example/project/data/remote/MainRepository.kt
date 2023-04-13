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
    //f92ea5df18244d99ad3f813a4a03b41e 70896c3d78f9493089d5a9f7b5a6124c
    @GET("/recipes/random?apiKey=70896c3d78f9493089d5a9f7b5a6124c")
    suspend fun getRecipes(@Query("number") number: Int): Response<Recipes>
}

interface Downloader{
    @GET
    suspend fun getImage(@Url getUrl: String): Response<ResponseBody>
}

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
            } catch(e: Exception){ Log.d("MyLog", e.toString()) }
        }

        if(oldSize != response.size){ localDB.save() }

        return response
    }

    suspend fun getDrawable(index: Int? = null, recipe: Recipe? = null): Drawable?{
        var drawable = localDB.getDrawable(index, recipe)
        if(drawable == null){
            try {
                val url = if(recipe == null){ localDB.getRecipe(index!!).image } else{ recipe.image }
                val index_2 = url.lastIndexOf("/") + 1

                val getUrl = url.substring(index_2)
                val baseUrl = url.substring(0, index_2)
                val downloader = RetrofitClient.getInstance(baseUrl).create(Downloader::class.java)

                val file = localDB.getFile(index, recipe)
                val inputStream = downloader.getImage(getUrl).body()!!.byteStream()

                file.outputStream().use { output ->
                    var length = 0
                    val buffer = ByteArray(1024)

                    while (inputStream.read(buffer).also { length = it } != -1) {
                        output.write(buffer, 0, length)
                    }

                    output.flush()
                }

                drawable = localDB.getDrawable(index, recipe)
            } catch(_: Exception){}
        }

        return drawable
    }
}
