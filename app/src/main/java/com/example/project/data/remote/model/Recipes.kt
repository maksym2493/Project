package com.example.project.data.remote.model

import com.google.gson.annotations.SerializedName

data class Recipes(
    @SerializedName("recipes")
    val recipes: List<ResponseRecipe>
)
