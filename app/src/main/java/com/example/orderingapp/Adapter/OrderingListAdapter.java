package com.example.orderingapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderingapp.Domain.ItemsModel;
import com.example.orderingapp.R;

import java.util.List;

public class OrderingListAdapter extends RecyclerView.Adapter<OrderingListAdapter.OrderViewHolder> {

    private List<ItemsModel> items;
    private OnItemClickListener onAddClickListener;
    private OnItemClickListener onRemoveClickListener;

    // Interface for click callbacks
    public interface OnItemClickListener {
        void onClick(ItemsModel item);
    }

    public OrderingListAdapter(List<ItemsModel> items,
                               OnItemClickListener onAddClickListener,
                               OnItemClickListener onRemoveClickListener) {
        this.items = items;
        this.onAddClickListener = onAddClickListener;
        this.onRemoveClickListener = onRemoveClickListener;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName, itemPrice, itemQuantity, itemTotal, btnAdd, btnRemove;

        public OrderViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
            itemTotal = itemView.findViewById(R.id.item_total);
            btnAdd = itemView.findViewById(R.id.btn_add);
            btnRemove = itemView.findViewById(R.id.btn_remove);
        }
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        ItemsModel item = items.get(position);

        holder.itemName.setText(item.getTitle());
        holder.itemPrice.setText("Price: $" + item.getPrice());
        holder.itemQuantity.setText(String.valueOf(item.getNumberIncart()));
        double totalPrice = item.getPrice() * item.getNumberIncart();
        holder.itemTotal.setText("Total: $" + String.format("%.2f", totalPrice));

        // Load first image from picUrl list if exists
        if (item.getPicUrl() != null && !item.getPicUrl().isEmpty()) {
            String imageUrl = item.getPicUrl().get(0);
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .into(holder.itemImage);
        } else {
            holder.itemImage.setImageResource(R.drawable.capybara); // placeholder image
        }

        holder.btnAdd.setOnClickListener(v -> {
            if (onAddClickListener != null) {
                onAddClickListener.onClick(item);
            }
        });

        holder.btnRemove.setOnClickListener(v -> {
            if (onRemoveClickListener != null) {
                onRemoveClickListener.onClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItems(List<ItemsModel> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }
}
