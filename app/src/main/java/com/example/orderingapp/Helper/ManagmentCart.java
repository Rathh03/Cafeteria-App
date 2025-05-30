package com.example.orderingapp.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.orderingapp.Domain.ItemsModel;

import java.util.ArrayList;

public class ManagmentCart {

    private final Context context;
    private final TinyDB tinyDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public ArrayList<ItemsModel> getListCart() {
        ArrayList<ItemsModel> list = tinyDB.getListObject("CartList", ItemsModel.class);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void insertItems(ItemsModel item) {
        ArrayList<ItemsModel> listItem = getListCart();
        int index = -1;
        for (int i = 0; i < listItem.size(); i++) {
            if (listItem.get(i).getTitle().equals(item.getTitle())) {
                index = i;
                break;
            }
        }

        if (index >= 0) {
            listItem.get(index).setNumberIncart(item.getNumberIncart());
        } else {
            listItem.add(item);
        }

        tinyDB.putListObject("CartList", listItem);
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
    }

    public void insertItemsList(ArrayList<ItemsModel> items) {
        tinyDB.putListObject("CartList", items);
    }

    public void plusItem(ArrayList<ItemsModel> listItems, int position, ChangeNumberItemsListener listener) {
        if (position >= 0 && position < listItems.size()) {
            ItemsModel item = listItems.get(position);
            item.setNumberIncart(item.getNumberIncart() + 1);
            tinyDB.putListObject("CartList", listItems);
            listener.onChanged();
        }
    }

    public void minusItem(ArrayList<ItemsModel> listItems, int position, ChangeNumberItemsListener listener) {
        if (position >= 0 && position < listItems.size()) {
            ItemsModel item = listItems.get(position);
            if (item.getNumberIncart() <= 1) {
                listItems.remove(position);
            } else {
                item.setNumberIncart(item.getNumberIncart() - 1);
            }
            tinyDB.putListObject("CartList", listItems);
            listener.onChanged();
        }
    }

    public void removeItem(ArrayList<ItemsModel> listItems, int position, ChangeNumberItemsListener listener) {
        if (position >= 0 && position < listItems.size()) {
            listItems.remove(position);
            tinyDB.putListObject("CartList", listItems);
            listener.onChanged();
        }
    }

    public double getTotalFee() {
        ArrayList<ItemsModel> listItem = getListCart();
        double fee = 0.0;
        for (ItemsModel item : listItem) {
            fee += item.getPrice() * item.getNumberIncart();
        }
        return fee;
    }
}
