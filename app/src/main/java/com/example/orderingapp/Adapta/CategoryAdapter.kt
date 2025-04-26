package com.example.orderingapp.Adapta

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RemoteViews.RemoteCollectionItems
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.orderingapp.Domain.CategoryModel
import com.example.orderingapp.R
import com.example.orderingapp.databinding.ViewholderCategoryBinding

class CategoryAdapter(val items: MutableList<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.Viewholder>() {

    private lateinit var context: Context
    private var selectedPosition = -1
    private var lastselectedPosition = -1

    inner class Viewholder(val binding: ViewholderCategoryBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.Viewholder {
        context = parent.context
        val binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.Viewholder, position: Int) {
        val item = items[position]
        holder.binding.title.text = item.title

        holder.binding.root.setOnClickListener {
            lastselectedPosition=selectedPosition
            selectedPosition = position
            notifyItemChanged(lastselectedPosition)
            notifyItemChanged(selectedPosition)
        }

        // Check if context is valid before using it
            if (selectedPosition == position) {
                holder.binding.title.setBackgroundResource(R.drawable.greenish_bg)
                holder.binding.title.setTextColor(ContextCompat.getColor(context, R.color.Deepteal))
            } else {
                holder.binding.title.setBackgroundResource(R.drawable.coralpink_bg)
                holder.binding.title.setTextColor(context.resources.getColor(R.color.Deepteal))
            }
    }

    override fun getItemCount(): Int = items.size
}
