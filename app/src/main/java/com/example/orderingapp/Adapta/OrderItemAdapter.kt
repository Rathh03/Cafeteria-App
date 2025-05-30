package com.example.orderingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.orderingapp.Domain.OrderItem
import com.example.orderingapp.R

class OrderItemAdapter(
    private val orderList: List<OrderItem>
) : RecyclerView.Adapter<OrderItemAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.item_image)
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemPrice: TextView = itemView.findViewById(R.id.item_price)
        val itemQuantity: TextView = itemView.findViewById(R.id.item_quantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_summary, parent, false)  // Your item layout file
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val orderItem = orderList[position]

        holder.itemName.text = orderItem.name
        holder.itemPrice.text = "$${String.format("%.2f", orderItem.price)}"
        holder.itemQuantity.text = orderItem.quantity.toString()

        // Set image - for now using placeholder drawable
        holder.itemImage.setImageResource(R.drawable.capybara)

        // TODO: Use Glide or Picasso if you have image URLs
    }

    override fun getItemCount(): Int = orderList.size
}
