package com.example.project.domain.main

import com.example.project.data.local.localDB
import com.example.project.data.local.model.Recipe
import com.example.project.data.remote.MainRepository
import java.io.File

object MainUseCase{
    private val repo = MainRepository

    suspend fun getRecipes(): List<Recipe>{
        return repo.getRecipes()
    }

    fun setFilesDir(filesDir: File){ localDB.setFilesDir(filesDir) }
}