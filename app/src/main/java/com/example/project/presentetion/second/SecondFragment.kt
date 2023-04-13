package com.example.project.presentetion.second

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.project.R
import com.example.project.databinding.FragmentSecondBinding

class SecondFragment: Fragment(){
    private lateinit var viewModel: SecondViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        viewModel = ViewModelProvider(this).get(SecondViewModel::class.java)

        val args: SecondFragmentArgs by navArgs()
        val binding = FragmentSecondBinding.inflate(inflater, container, false)

        viewModel.getRecipe(args.index)
        viewModel.recipe.observe(viewLifecycleOwner){
            Log.d("MyLog", "Recived")
            with(binding){
                title.text = it.title

                if(it.drawable != null){ image.setImageDrawable(it.drawable) }
                else{ image.setImageResource(R.drawable.progress_cat) }

                ingredients.text = it.ingredients
                instructions.text = it.instructions
            }
        }

        return binding.root
    }
}