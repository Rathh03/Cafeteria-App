package com.example.orderingapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderingapp.Activity.DetailActivity;
import com.example.orderingapp.Helper.ManagmentCart;
import com.example.orderingapp.Activity.OrderingListActivity;
import com.example.orderingapp.Domain.ItemsModel;
import com.example.orderingapp.databinding.ViewholderPopularBinding;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.Viewholder> {

    private Context context;
    private List<ItemsModel> items;

    public PopularAdapter(List<ItemsModel> items) {
        this.items = items;
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ViewholderPopularBinding binding;

        public Viewholder(ViewholderPopularBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewholderPopularBinding binding = ViewholderPopularBinding.inflate(inflater, parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        ItemsModel item = items.get(position);

        holder.binding.titleTxt.setText(item.getTitle());
        holder.binding.priceTxt.setText("$" + item.getPrice());

        // Load image with Glide
        if (item.getPicUrl() != null && !item.getPicUrl().isEmpty()) {
            Glide.with(context)
                    .load(item.getPicUrl().get(0))
                    .into(holder.binding.pic);
        }

        // Click whole item to open DetailActivity with the item object
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", item);
            context.startActivity(intent);
        });

        // Click add to cart button
        holder.binding.addToCartBtn.setOnClickListener(v -> {
            item.setNumberIncart(1); // Default quantity

            ManagmentCart cartManager = new ManagmentCart(context);
            cartManager.insertItems(item);

            Intent intent = new Intent(context, OrderingListActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
