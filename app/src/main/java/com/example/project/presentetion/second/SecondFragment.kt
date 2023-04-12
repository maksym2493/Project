package com.example.project.presentetion.second

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.project.R
import com.example.project.databinding.FragmentSecondBinding

class SecondFragment: Fragment(){
    private lateinit var viewModel: SecondViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        viewModel = ViewModelProvider(this).get(SecondViewModel::class.java)
        val binding = FragmentSecondBinding.inflate(inflater, container, false)

        viewModel.recipe.observe(viewLifecycleOwner){
            with(binding){

            }
        }

        return binding.root
    }
}