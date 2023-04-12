package com.example.project.domain.second

import com.example.project.data.local.localDB
import com.example.project.data.local.model.Recipe

object SecondUseCase{
    fun getRecipe(index: Int): Recipe{
        return localDB.getRecipe(index)
    }
}