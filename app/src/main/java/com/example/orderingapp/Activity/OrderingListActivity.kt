package com.example.orderingapp.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.orderingapp.Activity.Helper.ManagmentCart
import com.example.orderingapp.Domain.OrderItem
import com.example.orderingapp.Domain.ItemsModel
import com.example.orderingapp.R
import com.example.orderingapp.adapter.OrderingListAdapter

class OrderingListActivity : AppCompatActivity() {

    private lateinit var adapter: OrderingListAdapter
    private lateinit var managmentCart: ManagmentCart

    private lateinit var labelSubtotal: TextView
    private lateinit var labelDelivery: TextView
    private lateinit var labelTotal: TextView
    private lateinit var btnCheckout: Button

    private val deliveryFee = 1.00 // fixed delivery fee

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ordering_list)

        labelSubtotal = findViewById(R.id.label_subtotal)
        labelDelivery = findViewById(R.id.label_delivery)
        labelTotal = findViewById(R.id.label_total)
        btnCheckout = findViewById(R.id.btn_checkout)

        val backBtn = findViewById<ImageView>(R.id.backBtn)
        backBtn.setOnClickListener { finish() }

        val addMoreItem = findViewById<TextView>(R.id.add_more_item)
        addMoreItem.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_order_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        managmentCart = ManagmentCart(this)
        val items = managmentCart.getListCart().toMutableList()

        adapter = OrderingListAdapter(
            items,
            onAddClick = { item ->
                item.numberIncart++  // increase quantity
                managmentCart.insertItems(item) // save update
                val updatedList = managmentCart.getListCart()
                adapter.updateItems(updatedList)
                updateTotals(updatedList)
            },
            onRemoveClick = { item ->
                val currentList = managmentCart.getListCart().toMutableList()
                val index = currentList.indexOfFirst { it.title == item.title }

                if (index >= 0) {
                    val cartItem = currentList[index]
                    if (cartItem.numberIncart > 1) {
                        cartItem.numberIncart -= 1
                        managmentCart.insertItems(cartItem)
                    } else {
                        currentList.removeAt(index)
                        managmentCart.insertItemsList(ArrayList(currentList))
                    }
                    val updatedList = managmentCart.getListCart()
                    adapter.updateItems(updatedList)
                    updateTotals(updatedList)
                }
            }
        )
        recyclerView.adapter = adapter

        btnCheckout.isEnabled = false

        btnCheckout.setOnClickListener {
            val itemList = ArrayList<OrderItem>()
            for (item in managmentCart.getListCart()) {
                val url = if (item.picUrl.isNotEmpty()) item.picUrl[0] else ""
                itemList.add(
                    OrderItem(
                        id = 0,
                        name = item.title,
                        picUrl = url,
                        price = item.price,
                        quantity = item.numberIncart
                    )
                )
                println("Sending item: ${item.title}, picUrl: $url, qty: ${item.numberIncart}")
            }

            val intent = Intent(this, OrderSummaryActivity::class.java)
            intent.putParcelableArrayListExtra("orderList", itemList)
            intent.putExtra("pickTime", "12:00 PM")
            startActivity(intent)
        }


        updateTotals(items)
    }

    private fun updateTotals(orderItems: List<ItemsModel>) {
        val subtotal = orderItems.sumOf { it.price * it.numberIncart }
        val total = subtotal + deliveryFee

        labelSubtotal.text = "Subtotal: $${String.format("%.2f", subtotal)}"
        labelDelivery.text = "Delivery: $${String.format("%.2f", deliveryFee)}"
        labelTotal.text = "Total: $${String.format("%.2f", total)}"

        btnCheckout.isEnabled = subtotal > 0
    }
}
