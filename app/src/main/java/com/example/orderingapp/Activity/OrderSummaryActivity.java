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

import java.util.ArrayList;

public class OrderSummaryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderItemAdapter adapter;
    private TextView pickTimeTextView;
    private TextView totalTextView;
    private ImageView btnBack;
    private Button confirmButton;

    private ArrayList<OrderItem> orderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        recyclerView = findViewById(R.id.rvOrderItems);
        pickTimeTextView = findViewById(R.id.tvPickTime);
        totalTextView = findViewById(R.id.tvTotalAmount);
        btnBack = findViewById(R.id.btnBack);
        confirmButton = findViewById(R.id.btnConfirmOrder);

        btnBack.setOnClickListener(v -> finish());

        adapter = new OrderItemAdapter(orderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ArrayList<OrderItem> receivedOrderList = getIntent().getParcelableArrayListExtra("orderList");
        String pickTime = getIntent().getStringExtra("pickTime");
        if (pickTime == null) pickTime = "";

        if (receivedOrderList != null && !receivedOrderList.isEmpty()) {
            orderList.clear();
            orderList.addAll(receivedOrderList);
            adapter.notifyDataSetChanged();

            if (!pickTime.isEmpty()) {
                pickTimeTextView.setText("Pick Time: " + pickTime);
            } else {
                pickTimeTextView.setText("Pick Time: N/A");
            }

            double totalPrice = 0;
            for (OrderItem item : orderList) {
                totalPrice += item.getPrice() * item.getQuantity();
            }
            totalTextView.setText(String.format("Total: $%.2f", totalPrice));
        } else {
            Toast.makeText(this, "No order data received", Toast.LENGTH_SHORT).show();
        }

        confirmButton.setOnClickListener(v ->
                Toast.makeText(OrderSummaryActivity.this, "Order confirmed!", Toast.LENGTH_SHORT).show()
        );
    }
}
