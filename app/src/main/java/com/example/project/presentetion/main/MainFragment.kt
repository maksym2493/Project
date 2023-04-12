package com.example.project.presentetion.main

import android.os.Bundle
import android.text.Layout.Directions
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
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

        val recycleView = view.findViewById<RecyclerView>(R.id.recycler_view)

        recycleView.adapter =  adapter
        recycleView.layoutManager = LinearLayoutManager(context)

        viewModel.setFilesDir(requireContext().filesDir)
        viewModel.getRecipes()
        viewModel.recipes.observe(viewLifecycleOwner){ it.forEach{ Log.d("MyLog", it.title); adapter.addItem(it) } }

        return view
    }

    inner class Listener(val index: Int): OnClickListener {
        override fun onClick(p0: View?){
            val bundle = Bundle()
            bundle.putInt("index", index)
            findNavController().navigate(R.id.action_main_to_second, bundle)
        }
    }
}