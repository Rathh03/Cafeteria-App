package com.example.orderingapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.orderingapp.Domain.ItemsModel
import com.example.orderingapp.Activity.Helper.ManagmentCart

class OrderingListViewModel(application: Application) : AndroidViewModel(application) {

    private val _orderItems = MutableLiveData<List<ItemsModel>>()
    val orderItems: LiveData<List<ItemsModel>> = _orderItems

    private val managmentCart = ManagmentCart(application)

    init {
        loadCartItems()
    }

    fun loadCartItems() {
        _orderItems.value = managmentCart.getListCart()
    }

    fun addQuantity(item: ItemsModel) {
        item.numberIncart++
        managmentCart.insertItems(item)
        loadCartItems()
    }

    fun removeQuantity(item: ItemsModel) {
        item.numberIncart--
        if (item.numberIncart <= 0) {
            val updatedList = managmentCart.getListCart()
                .filter { it.title != item.title }
            managmentCart.insertItemsList(ArrayList(updatedList)) // <- create this method
        } else {
            managmentCart.insertItems(item)
        }
        loadCartItems()
    }
}
