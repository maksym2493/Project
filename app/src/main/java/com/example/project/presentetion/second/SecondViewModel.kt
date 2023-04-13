package com.example.project.presentetion.second

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.data.local.model.Recipe
import com.example.project.domain.second.SecondUseCase
import com.example.project.domain.second.model.SecondRecipe
import kotlinx.coroutines.launch

class SecondViewModel: ViewModel(){
    val secondUseCase = SecondUseCase

    val recipe = MutableLiveData<SecondRecipe>()

    fun getRecipe(index: Int){
        viewModelScope.launch{
            recipe.postValue(secondUseCase.getRecipe(index))
        }
    }
}