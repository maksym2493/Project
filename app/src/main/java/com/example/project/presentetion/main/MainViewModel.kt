package com.example.project.presentetion.main

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.data.local.model.Recipe
import com.example.project.domain.main.MainUseCase
import com.example.project.domain.main.model.Image
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel: ViewModel(){
    val mainUseCase = MainUseCase
    val image = MutableLiveData<Image>()
    val isLoading = MutableLiveData<Boolean>()
    val recipes = MutableLiveData<List<Recipe>>()

    fun getRecipes(count: Int = 200){
        viewModelScope.launch{
            isLoading.postValue(true)
            recipes.postValue(mainUseCase.getRecipes(count))
        }
    }

    fun getDrawable(view: ImageView, index: Int){
        viewModelScope.launch{
            val i = mainUseCase.getDrawable(view, index)
            //i.view.setImageDrawable(i.drawable)
            image.postValue(i)
            Log.d("MyLog", index.toString())
        }
    }

    fun setFilesDir(filesDir: File){ mainUseCase.setFilesDir(filesDir) }
}