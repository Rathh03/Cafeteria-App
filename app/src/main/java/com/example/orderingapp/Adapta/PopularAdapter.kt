package com.example.orderingapp.Adapta

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.orderingapp.Domain.ItemsModel
import com.example.orderingapp.databinding.ViewholderPopularBinding
import com.example.orderingapp.Activity.DetailActivity
import com.example.orderingapp.Activity.OrderingListActivity


class PopularAdapter(val items: MutableList<ItemsModel>) :
    RecyclerView.Adapter<PopularAdapter.Viewholder>() {

    lateinit var context: Context

    class Viewholder(val binding: ViewholderPopularBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        context = parent.context
        val binding = ViewholderPopularBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items[position]
        holder.binding.titleTxt.text = item.title
        holder.binding.priceTxt.text = "$${item.price}"
        Glide.with(context).load(item.picUrl[0]).into(holder.binding.pic)

        // ✅ When clicking the whole item -> go to Detail screen
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("object", item)
            context.startActivity(intent)
        }

        // ✅ When clicking the Add to Cart icon -> add directly and go to item list screen
        holder.binding.addToCartBtn.setOnClickListener {
            item.numberIncart = 1 // Default quantity
            val cartManager = com.example.orderingapp.Activity.Helper.ManagmentCart(context)
            cartManager.insertItems(item)

            val intent = Intent(context, OrderingListActivity::class.java)
            context.startActivity(intent)
        }
    }



    override fun getItemCount(): Int = items.size
}
