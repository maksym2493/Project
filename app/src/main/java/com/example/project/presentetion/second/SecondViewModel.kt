package com.example.project.presentetion.second

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.data.local.model.Recipe
import com.example.project.domain.second.SecondUseCase
import kotlinx.coroutines.launch

class SecondViewModel: ViewModel(){
    val secondUseCase = SecondUseCase
    val recipe = MutableLiveData<Recipe>()

    fun getRecipe(index: Int){
        viewModelScope.launch{
            recipe.value = secondUseCase.getRecipe(index)
        }
    }
}