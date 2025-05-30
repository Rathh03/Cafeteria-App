package com.example.orderingapp.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderingapp.Domain.OrderItem;
import com.example.orderingapp.R;


import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderViewHolder> {

    private List<OrderItem> orderList;

    public OrderItemAdapter(List<OrderItem> orderList) {
        this.orderList = orderList;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName, itemPrice, itemQuantity;

        public OrderViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
        }
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_summary, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        OrderItem orderItem = orderList.get(position);

        // Change getName() to getTitle() if your field is 'title'
        holder.itemName.setText(orderItem.getTitle());

        holder.itemPrice.setText("$" + String.format("%.2f", orderItem.getPrice()));
        holder.itemQuantity.setText(String.valueOf(orderItem.getQuantity()));

        String imageUrl = orderItem.getPicUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.capybara)
                    .error(R.drawable.capybara)
                    .into(holder.itemImage);
        } else {
            holder.itemImage.setImageResource(R.drawable.capybara);
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
