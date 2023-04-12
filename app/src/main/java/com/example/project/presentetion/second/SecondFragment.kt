package com.example.project.presentetion.second

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.project.databinding.FragmentSecondBinding

class SecondFragment: Fragment(){
    private lateinit var viewModel: SecondViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        viewModel = ViewModelProvider(this).get(SecondViewModel::class.java)

        val args: SecondFragmentArgs by navArgs()
        val binding = FragmentSecondBinding.inflate(inflater, container, false)

        viewModel.getRecipe(args.index)
        viewModel.recipe.observe(viewLifecycleOwner){
            with(binding){
                title.text = it[0]

                viewModel.getDrawable(args.index)
                viewModel.image.observe(viewLifecycleOwner){
                    image.setImageDrawable(it)
                }

                description.text = "Ingredients: " + it[3] + "\n\nInstruction: " + it[1]
            }
        }

        return binding.root
    }
}