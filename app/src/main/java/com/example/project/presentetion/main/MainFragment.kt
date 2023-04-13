package com.example.project.presentetion.main

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Layout.Directions
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project.R
import java.io.File

class MainFragment : Fragment(){
    private lateinit var adapter: Adapter
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView

    private var initialized = false
    private lateinit var view: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(!initialized){ view = inflater.inflate(R.layout.fragment_main, container, false) }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.setFilesDir(requireContext().filesDir)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.recipes.value = null
        viewModel.isLoading.value = null

        viewModel.recipes.observe(viewLifecycleOwner){
            if(it != null){
                it.forEach{ adapter.addItem(it) }
                viewModel.isLoading.postValue(false)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            if(it != null){
                val progressCat = view.findViewById<ImageView>(R.id.loading)
                progressCat.visibility = if(it){ ImageView.VISIBLE } else{ ImageView.GONE }

                if(progressCat.drawable == null){ Glide.with(view).load(R.drawable.progress_cat).into(progressCat) }
            }
        }

        if(!initialized){
            initialized = true
            recyclerView = view.findViewById(R.id.recycler_view)

            adapter = Adapter(
                { count: Int ->
                    viewModel.getRecipes(count)
                },
                { index: Int ->
                    try {
                        findNavController().navigate(MainFragmentDirections.actionMainToSecond(index))
                    } catch (_: java.lang.Exception){}
                }
            )

            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)

            viewModel.getRecipes(200)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.resetLocalPos()
    }
}