package com.example.project.data.remote.model

import com.google.gson.annotations.SerializedName

data class Ingredients(
    @SerializedName("name")
    val name: String
)