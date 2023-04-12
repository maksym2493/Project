package com.example.project.presentetion.second

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.data.local.model.Recipe
import com.example.project.domain.second.SecondUseCase
import kotlinx.coroutines.launch

class SecondViewModel: ViewModel(){
    val secondUseCase = SecondUseCase
    val image = MutableLiveData<Drawable?>()
    val recipe = MutableLiveData<Array<String>>()

    fun getRecipe(index: Int){
        viewModelScope.launch{
            recipe.postValue(secondUseCase.getRecipe(index))
        }
    }

    fun getDrawable(index: Int){
        viewModelScope.launch{
            image.postValue(secondUseCase.getDrawable(index))
        }
    }
}