package com.example.orderingapp.Activity.Helper

import android.content.Context
import android.widget.Toast
import com.example.orderingapp.Domain.ItemsModel
import com.example.orderingapp.Helper.TinyDB

class ManagmentCart(private val context: Context) {

    private val tinyDB = TinyDB(context)

    fun getListCart(): ArrayList<ItemsModel> {
        return tinyDB.getListObject("CartList", ItemsModel::class.java) ?: arrayListOf()
    }

    // Insert new item or update quantity to exact number in item
    fun insertItems(item: ItemsModel) {
        val listItem = getListCart()
        val index = listItem.indexOfFirst { it.title == item.title }

        if (index >= 0) {
            // Update quantity to the value inside item
            listItem[index].numberIncart = item.numberIncart
        } else {
            listItem.add(item)
        }

        tinyDB.putListObject("CartList", listItem)
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show()
    }

    // Save entire list at once
    fun insertItemsList(items: ArrayList<ItemsModel>) {
        tinyDB.putListObject("CartList", items)
    }

    // Increase quantity of item at position by 1
    fun plusItem(listItems: ArrayList<ItemsModel>, position: Int, listener: ChangeNumberItemsListener) {
        val item = listItems.getOrNull(position)
        item?.let {
            it.numberIncart++
            tinyDB.putListObject("CartList", listItems)
            listener.onChanged()
        }
    }

    // Decrease quantity or remove item if quantity is 1
    fun minusItem(listItems: ArrayList<ItemsModel>, position: Int, listener: ChangeNumberItemsListener) {
        val item = listItems.getOrNull(position)
        if (item != null) {
            if (item.numberIncart <= 1) {
                listItems.removeAt(position)
            } else {
                item.numberIncart--
            }
            tinyDB.putListObject("CartList", listItems)
            listener.onChanged()
        }
    }

    // Remove item completely by position
    fun removeItem(listItems: ArrayList<ItemsModel>, position: Int, listener: ChangeNumberItemsListener) {
        listItems.getOrNull(position)?.let {
            listItems.removeAt(position)
            tinyDB.putListObject("CartList", listItems)
            listener.onChanged()
        }
    }

    fun getTotalFee(): Double {
        val listItem = getListCart()
        var fee = 0.0
        for (item in listItem) {
            fee += item.price * item.numberIncart
        }
        return fee
    }
}
