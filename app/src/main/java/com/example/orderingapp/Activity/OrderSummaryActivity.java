package com.example.orderingapp.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderingapp.Domain.OrderItem;
import com.example.orderingapp.R;
import com.example.orderingapp.Adapter.OrderItemAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class OrderSummaryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderItemAdapter adapter;
    private TextView pickTimeTextView;
    private TextView totalTextView;
    private ImageView btnBack;
    private Button confirmButton;

    private ArrayList<OrderItem> orderList = new ArrayList<>();
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("orders");

        // Initialize views
        recyclerView = findViewById(R.id.rvOrderItems);
        pickTimeTextView = findViewById(R.id.tvPickTime);
        totalTextView = findViewById(R.id.tvTotalAmount);
        btnBack = findViewById(R.id.btnBack);
        confirmButton = findViewById(R.id.btnConfirmOrder);

        // Set up back button
        btnBack.setOnClickListener(v -> finish());

        // Set up RecyclerView
        adapter = new OrderItemAdapter(orderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Get data from intent
        ArrayList<OrderItem> receivedOrderList = getIntent().getParcelableArrayListExtra("orderList");
        String pickTime = getIntent().getStringExtra("pickTime");

        if (receivedOrderList != null && !receivedOrderList.isEmpty()) {
            orderList.clear();
            orderList.addAll(receivedOrderList);
            adapter.notifyDataSetChanged();

            // Update pick time
            pickTimeTextView.setText(pickTime != null ? "Pick Time: " + pickTime : "Pick Time: N/A");

            // Calculate total price
            double totalPrice = calculateTotalPrice();
            totalTextView.setText(String.format("Total: $%.2f", totalPrice));

            // Make sure confirm button is visible
            confirmButton.setVisibility(Button.VISIBLE);
        } else {
            Toast.makeText(this, "No order data received", Toast.LENGTH_SHORT).show();
            confirmButton.setVisibility(Button.GONE);
        }

        // Set up confirm button with Firebase upload
        confirmButton.setOnClickListener(v -> {
            if (orderList.isEmpty()) {
                Toast.makeText(this, "No items to order", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create order data
            String orderId = databaseReference.push().getKey();
            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            double totalPrice = calculateTotalPrice();

            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("orderId", orderId);
            orderMap.put("pickupTime", pickTimeTextView.getText().toString());
            orderMap.put("orderTime", currentTime);
            orderMap.put("totalPrice", totalPrice);
            orderMap.put("status", "pending");

            // Add order items
            ArrayList<Map<String, Object>> itemsList = new ArrayList<>();
            for (OrderItem item : orderList) {
                Map<String, Object> itemMap = new HashMap<>();
                itemMap.put("name", item.getTitle()); // ✅ Correct method based on your class

                itemMap.put("price", item.getPrice());
                itemMap.put("quantity", item.getQuantity());
                itemsList.add(itemMap);
            }
            orderMap.put("items", itemsList);

            // Upload to Firebase
            if (orderId != null) {
                databaseReference.child(orderId).setValue(orderMap)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(OrderSummaryActivity.this, "Order confirmed!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(OrderSummaryActivity.this, "Failed to place order", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private double calculateTotalPrice() {
        double total = 0;
        for (OrderItem item : orderList) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
}
