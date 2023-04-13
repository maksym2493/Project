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

class MainFragment : Fragment() {
    private lateinit var adapter: Adapter
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setFilesDir(requireContext().filesDir)

        viewModel.recipes.observe(viewLifecycleOwner){
            it.forEach{ adapter.addItem(it) }
            viewModel.isLoading.postValue(false)
        }

        adapter = Adapter(
            {count: Int ->
                viewModel.getRecipes(count)
            },
            {index: Int ->
                try {
                    findNavController().navigate(MainFragmentDirections.actionMainToSecond(index))
                } catch (_: java.lang.Exception) {}
            }
        )

        viewModel.isLoading.observe(viewLifecycleOwner){
            val viewLoading = view.findViewById<View>(R.id.viewLoading)
            val progressCat = view.findViewById<ImageView>(R.id.loading)

            if(it) {
                viewLoading.visibility = View.VISIBLE
                progressCat.visibility = ImageView.VISIBLE
                recyclerView.visibility = RecyclerView.GONE
            } else {
                viewLoading.visibility = View.GONE
                progressCat.visibility = ImageView.GONE
                recyclerView.visibility = RecyclerView.VISIBLE
            }
        }

        viewModel.getRecipes(10)

        recyclerView = view.findViewById(R.id.recycler_view)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }
}