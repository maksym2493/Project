package com.example.project.data.remote.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface{
    //f92ea5df18244d99ad3f813a4a03b41e 70896c3d78f9493089d5a9f7b5a6124c
    @GET("/recipes/random?apiKey=70896c3d78f9493089d5a9f7b5a6124c")
    suspend fun getRecipes(@Query("number") number: Int): Response<Recipes>
}