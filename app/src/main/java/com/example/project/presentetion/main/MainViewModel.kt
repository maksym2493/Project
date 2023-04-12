package com.example.project.presentetion.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.data.local.model.Recipe
import com.example.project.domain.main.MainUseCase
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel: ViewModel(){
    val mainUseCase = MainUseCase
    val recipes = MutableLiveData<List<Recipe>>()

    fun getRecipes(){
        viewModelScope.launch{
            recipes.postValue(mainUseCase.getRecipes())
        }
    }

    fun setFilesDir(filesDir: File){ mainUseCase.setFilesDir(filesDir) }
}