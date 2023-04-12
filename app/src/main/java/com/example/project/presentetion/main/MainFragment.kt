package com.example.project.presentetion.main

import android.graphics.drawable.Drawable
import com.example.project.domain.main.model.Image
import android.os.Bundle
import android.text.Layout.Directions
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import java.io.File

class MainFragment: Fragment(){
    private lateinit var adapter: Adapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        adapter = Adapter(this)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.setFilesDir(requireContext().filesDir)
        viewModel.image.observe(viewLifecycleOwner){ it.view.setImageDrawable(it.drawable) }
        viewModel.recipes.observe(viewLifecycleOwner){ it.forEach{ Log.d("MyLog", it.title); adapter.addItem(it) } }

        val recycleView = view.findViewById<RecyclerView>(R.id.recycler_view)

        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(context)

        viewModel.getRecipes()
        return view
    }

    fun setImage(index: Int, image: ImageView){
        viewModel.getDrawable(image, index)
    }

    inner class Listener(val index: Int): OnClickListener {
        override fun onClick(p0: View?){
            try{ findNavController().navigate(MainFragmentDirections.actionMainToSecond(index)) } catch(_: java.lang.Exception){}
        }
    }
}