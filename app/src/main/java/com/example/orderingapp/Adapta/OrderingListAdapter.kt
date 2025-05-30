package com.example.orderingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.orderingapp.Domain.ItemsModel
import com.example.orderingapp.R

class OrderingListAdapter(
    private var items: List<ItemsModel>,
    private val onAddClick: (ItemsModel) -> Unit,
    private val onRemoveClick: (ItemsModel) -> Unit
) : RecyclerView.Adapter<OrderingListAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.item_image)
        val itemName: TextView = view.findViewById(R.id.item_name)
        val itemPrice: TextView = view.findViewById(R.id.item_price)
        val itemQuantity: TextView = view.findViewById(R.id.item_quantity)
        val itemTotal: TextView = view.findViewById(R.id.item_total)
        val btnAdd: TextView = view.findViewById(R.id.btn_add)
        val btnRemove: TextView = view.findViewById(R.id.btn_remove)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = items[position]

        holder.itemName.text = item.title
        holder.itemPrice.text = "Price: $${item.price}"
        holder.itemQuantity.text = item.numberIncart.toString()
        val totalPrice = item.price * item.numberIncart
        holder.itemTotal.text = "Total: $${String.format("%.2f", totalPrice)}"

        // Load first image from picUrl list if exists
        val imageUrl = item.picUrl.firstOrNull()
        if (imageUrl != null) {
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .into(holder.itemImage)
        } else {
            holder.itemImage.setImageResource(R.drawable.capybara) // your placeholder
        }

        holder.btnAdd.setOnClickListener { onAddClick(item) }
        holder.btnRemove.setOnClickListener { onRemoveClick(item) }


    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<ItemsModel>) {
        items = newItems
        notifyDataSetChanged()
    }
}
