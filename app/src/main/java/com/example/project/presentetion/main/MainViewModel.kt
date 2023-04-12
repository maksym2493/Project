package com.example.project.presentetion.main

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.data.local.model.Recipe
import com.example.project.domain.main.MainUseCase
import com.example.project.domain.main.model.Image
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel: ViewModel(){
    val mainUseCase = MainUseCase
    val image = MutableLiveData<Image>()
    val recipes = MutableLiveData<List<Recipe>>()

    fun getRecipes(){
        viewModelScope.launch{
            recipes.postValue(mainUseCase.getRecipes())
        }
    }

    fun getDrawable(view: ImageView, index: Int){
        viewModelScope.launch {
            image.postValue(mainUseCase.getDrawable(view, index))
        }
    }

    fun setFilesDir(filesDir: File){ mainUseCase.setFilesDir(filesDir) }
}