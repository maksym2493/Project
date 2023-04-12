package com.example.project.domain.main

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.example.project.data.local.localDB
import com.example.project.data.local.model.Recipe
import com.example.project.data.remote.MainRepository
import com.example.project.domain.main.model.Image
import java.io.File

object MainUseCase{
    private val repo = MainRepository

    suspend fun getRecipes(): List<Recipe>{
        return repo.getRecipes()
    }

    suspend fun getDrawable(view: ImageView, index: Int): Image{
        return Image(view, MainRepository.getDrawable(index))
    }

    fun setFilesDir(filesDir: File){ localDB.setFilesDir(filesDir) }
}