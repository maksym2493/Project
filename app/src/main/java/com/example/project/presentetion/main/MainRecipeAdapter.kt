package com.example.project.presentetion.main

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project.R
import com.example.project.databinding.RecyclerRowBinding
import com.example.project.domain.main.model.MainRecipe

class MainRecipeAdapter(val getRecipes: (Int) -> Unit, val listener: (Int) -> Unit): RecyclerView.Adapter<MainRecipeAdapter.Holder>(){
    var items: ArrayList<MainRecipe> = ArrayList()

    fun addItem(item: MainRecipe){
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(itemBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int){
        val item = items[position]
        if(position == items.size - 1){ getRecipes(200) }

        holder.bind(item, position)
    }

    override fun getItemCount(): Int{
        return items.size
    }

    inner class Holder(private val itemBinding: RecyclerRowBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item: MainRecipe, index: Int){
            with(itemBinding){
                title.text = (index + 1).toString() + ". " + item.title

                Glide.with(root)
                    .load(item.link)
                    .placeholder(R.drawable.progress_cat)
                    .error(R.drawable.progress_cat)
                    .into(image)

                description.text = item.ingredients
                root.setOnClickListener(Listener(index))
            }
        }
    }

    inner class Listener(val index: Int): OnClickListener{
        override fun onClick(p0: View?) {
            listener(index)
        }
    }
}
