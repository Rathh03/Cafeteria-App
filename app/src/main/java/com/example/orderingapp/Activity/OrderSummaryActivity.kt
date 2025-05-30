package com.example.orderingapp.Activity

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.orderingapp.Domain.OrderItem
import com.example.orderingapp.R
import com.example.orderingapp.adapter.OrderItemAdapter

class OrderSummaryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderItemAdapter
    private lateinit var pickTimeTextView: TextView
    private lateinit var totalTextView: TextView
    private lateinit var btnBack: ImageView
    private lateinit var confirmButton: Button

    private var orderList = ArrayList<OrderItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_summary)

        recyclerView = findViewById(R.id.rvOrderItems)
        pickTimeTextView = findViewById(R.id.tvPickTime)
        totalTextView = findViewById(R.id.tvTotalAmount)
        btnBack = findViewById(R.id.btnBack)
        confirmButton = findViewById(R.id.btnConfirmOrder)

        btnBack.setOnClickListener { finish() }

        adapter = OrderItemAdapter(orderList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val receivedOrderList = intent.getParcelableArrayListExtra<OrderItem>("orderList")
        val pickTime = intent.getStringExtra("pickTime") ?: ""

        if (!receivedOrderList.isNullOrEmpty()) {
            orderList.clear()
            orderList.addAll(receivedOrderList)
            adapter.notifyDataSetChanged()

            pickTimeTextView.text = if (pickTime.isNotEmpty()) "Pick Time: $pickTime" else "Pick Time: N/A"

            val totalPrice = orderList.sumOf { it.price * it.quantity }
            totalTextView.text = String.format("Total: $%.2f", totalPrice)
        } else {
            Toast.makeText(this, "No order data received", Toast.LENGTH_SHORT).show()
        }

        confirmButton.setOnClickListener {
            Toast.makeText(this, "Order confirmed!", Toast.LENGTH_SHORT).show()
        }
    }
}
