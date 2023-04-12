package com.example.project.presentetion.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project.data.local.model.Recipe
import com.example.project.databinding.RecyclerRowBinding

class Adapter(val parent: MainFragment, var items: ArrayList<Recipe> = ArrayList()): RecyclerView.Adapter<Adapter.Holder>(){
    fun addItem(item: Recipe){
        items.add(item)
        notifyItemInserted(items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(itemBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Holder(private val itemBinding: RecyclerRowBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item: Recipe){
            with(itemBinding){
                title.text = item.title

                var text = ""
                item.ingredients.forEach{ text += it + ", " }
                description.text = "Ingredients: " + text.dropLast(1)

                Glide.with(root)
                    .load(item.image)
                    .into(image)

                root.setOnClickListener(parent.Listener(items.indexOf(item)))
            }
        }
    }
}