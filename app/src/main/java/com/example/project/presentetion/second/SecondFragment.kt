package com.example.project.presentetion.second

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.project.R
import com.example.project.databinding.FragmentSecondBinding

class SecondFragment: Fragment(){
    private lateinit var viewModel: SecondViewModel
    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        binding = FragmentSecondBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: SecondFragmentArgs by navArgs()
        viewModel = ViewModelProvider(this).get(SecondViewModel::class.java)

        viewModel.getRecipe(args.index)
        viewModel.recipe.observe(viewLifecycleOwner){
            with(binding){
                title.text = it.title

                Glide.with(root)
                    .load(it.link)
                    .error(R.drawable.progress_cat)
                    .into(image)

                ingredients.text = it.ingredients
                instructions.text = it.instructions
            }
        }
    }
}