package com.example.orderingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.orderingapp.Helper.ManagmentCart;
import com.example.orderingapp.Domain.ItemsModel;

import java.util.ArrayList;
import java.util.List;

public class OrderingListViewModel extends AndroidViewModel {

    private final MutableLiveData<List<ItemsModel>> _orderItems = new MutableLiveData<>();
    public LiveData<List<ItemsModel>> orderItems = _orderItems;

    private final ManagmentCart managmentCart;

    public OrderingListViewModel(@NonNull Application application) {
        super(application);
        managmentCart = new ManagmentCart(application);
        loadCartItems();
    }

    public void loadCartItems() {
        _orderItems.setValue(managmentCart.getListCart());
    }

    // Add quantity method here
    public void addQuantity(ItemsModel item) {
        item.setNumberIncart(item.getNumberIncart() + 1);
        managmentCart.insertItems(item);
        loadCartItems();
    }

    // Remove quantity method here
    public void removeQuantity(ItemsModel item) {
        int newQuantity = item.getNumberIncart() - 1;
        item.setNumberIncart(newQuantity);

        if (newQuantity <= 0) {
            List<ItemsModel> updatedList = new ArrayList<>();
            for (ItemsModel i : managmentCart.getListCart()) {
                if (!i.getTitle().equals(item.getTitle())) {
                    updatedList.add(i);
                }
            }
            managmentCart.insertItemsList(new ArrayList<>(updatedList));  // <-- cast here
        } else {
            managmentCart.insertItems(item);
        }
        loadCartItems();
    }

}
