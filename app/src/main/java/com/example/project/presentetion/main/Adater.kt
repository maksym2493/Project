package com.example.project.presentetion.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project.data.local.model.Recipe
import com.example.project.databinding.RecyclerRowBinding

class Adapter(val parent: MainFragment, var items: ArrayList<Recipe> = ArrayList()): RecyclerView.Adapter<Adapter.Holder>(){
    var count = 149

    @Synchronized
    fun addItem(item: Recipe){
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(itemBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int){
        val size = items.size
        val item = items[position]
        if(position == size - 1){ count = position }
        if(position == count){ count += 200; parent.getRecipes(200) }

        holder.bind(item, position)
    }

    override fun getItemCount(): Int{
        return items.size
    }

    inner class Holder(private val itemBinding: RecyclerRowBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item: Recipe, index: Int){
            with(itemBinding){
                title.text = (index + 1).toString() + ". " + item.title

                var text = ""
                item.ingredients.forEach{ text += it + ", " }
                description.text = "Ingredients: " + text.dropLast(2) + "."

                parent.setImage(index, image)
                root.setOnClickListener(parent.Listener(index))
            }
        }
    }
}
