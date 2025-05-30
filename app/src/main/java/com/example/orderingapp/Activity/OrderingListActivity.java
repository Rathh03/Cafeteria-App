package com.example.orderingapp.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderingapp.Helper.ManagmentCart;
import com.example.orderingapp.Domain.ItemsModel;
import com.example.orderingapp.Domain.OrderItem;
import com.example.orderingapp.R;
import com.example.orderingapp.Adapter.OrderingListAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderingListActivity extends AppCompatActivity {

    private OrderingListAdapter adapter;
    private ManagmentCart managmentCart;

    private TextView labelSubtotal;
    private TextView labelDelivery;
    private TextView labelTotal;
    private Button btnCheckout;

    private final double deliveryFee = 1.00; // fixed delivery fee

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_list);

        labelSubtotal = findViewById(R.id.label_subtotal);
        labelDelivery = findViewById(R.id.label_delivery);
        labelTotal = findViewById(R.id.label_total);
        btnCheckout = findViewById(R.id.btn_checkout);

        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());

        TextView addMoreItem = findViewById(R.id.add_more_item);
        addMoreItem.setOnClickListener(v -> {
            startActivity(new Intent(OrderingListActivity.this, MainActivity.class));
            finish();
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_order_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        managmentCart = new ManagmentCart(this);
        List<ItemsModel> items = new ArrayList<>(managmentCart.getListCart());

        adapter = new OrderingListAdapter(
                items,
                item -> {
                    item.setNumberIncart(item.getNumberIncart() + 1);
                    managmentCart.insertItems(item);
                    List<ItemsModel> updatedList = managmentCart.getListCart();
                    adapter.updateItems(updatedList);
                    updateTotals(updatedList);
                },
                item -> {
                    List<ItemsModel> currentList = new ArrayList<>(managmentCart.getListCart());
                    int index = -1;
                    for (int i = 0; i < currentList.size(); i++) {
                        if (currentList.get(i).getTitle().equals(item.getTitle())) {
                            index = i;
                            break;
                        }
                    }

                    if (index >= 0) {
                        ItemsModel cartItem = currentList.get(index);
                        if (cartItem.getNumberIncart() > 1) {
                            cartItem.setNumberIncart(cartItem.getNumberIncart() - 1);
                            managmentCart.insertItems(cartItem);
                        } else {
                            currentList.remove(index);
                            managmentCart.insertItemsList(new ArrayList<>(currentList));
                        }
                        List<ItemsModel> updatedList = managmentCart.getListCart();
                        adapter.updateItems(updatedList);
                        updateTotals(updatedList);
                    }
                }
        );

        recyclerView.setAdapter(adapter);
        btnCheckout.setEnabled(false);

        btnCheckout.setOnClickListener(v -> {
            ArrayList<OrderItem> itemList = new ArrayList<>();
            for (ItemsModel item : managmentCart.getListCart()) {
                String url = "";
                if (item.getPicUrl() != null && !item.getPicUrl().isEmpty()) {
                    url = item.getPicUrl().get(0);
                }
                itemList.add(new OrderItem(
                        0,
                        item.getTitle(),
                        url,
                        item.getPrice(),
                        item.getNumberIncart()
                ));
                System.out.println("Sending item: " + item.getTitle() + ", picUrl: " + url + ", qty: " + item.getNumberIncart());
            }

            Intent intent = new Intent(OrderingListActivity.this, OrderSummaryActivity.class);
            intent.putParcelableArrayListExtra("orderList", itemList);
            intent.putExtra("pickTime", "12:00 PM");
            startActivity(intent);
        });

        updateTotals(items);
    }

    private void updateTotals(List<ItemsModel> orderItems) {
        double subtotal = 0;
        for (ItemsModel item : orderItems) {
            subtotal += item.getPrice() * item.getNumberIncart();
        }
        double total = subtotal + deliveryFee;

        labelSubtotal.setText(String.format("Subtotal: $%.2f", subtotal));
        labelDelivery.setText(String.format("Delivery: $%.2f", deliveryFee));
        labelTotal.setText(String.format("Total: $%.2f", total));

        btnCheckout.setEnabled(subtotal > 0);
    }
}
