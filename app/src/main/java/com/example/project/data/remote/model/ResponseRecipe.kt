package com.example.project.data.remote.model

import com.google.gson.annotations.SerializedName

data class ResponseRecipe(
    @SerializedName("title")
    val title: String,

    @SerializedName("instructions")
    val instructions: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("extendedIngredients")
    val ingredients: List<Ingredients>
)