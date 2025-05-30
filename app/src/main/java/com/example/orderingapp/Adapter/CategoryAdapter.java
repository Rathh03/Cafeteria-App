package com.example.orderingapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderingapp.Domain.CategoryModel;
import com.example.orderingapp.R;
import com.example.orderingapp.databinding.ViewholderCategoryBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<CategoryModel> items;
    private int selectedPosition = -1;
    private int lastSelectedPosition = -1;

    public CategoryAdapter(List<CategoryModel> items) {
        this.items = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderCategoryBinding binding;

        public ViewHolder(ViewholderCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewholderCategoryBinding binding = ViewholderCategoryBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CategoryModel item = items.get(position);
        holder.binding.title.setText(item.getTitle());

        holder.binding.getRoot().setOnClickListener(v -> {
            lastSelectedPosition = selectedPosition;
            selectedPosition = position;
            if (lastSelectedPosition != -1) {
                notifyItemChanged(lastSelectedPosition);
            }
            notifyItemChanged(selectedPosition);
        });

        if (selectedPosition == position) {
            holder.binding.title.setBackgroundResource(R.drawable.greenish_bg);
            holder.binding.title.setTextColor(ContextCompat.getColor(context, R.color.Deepteal));
        } else {
            holder.binding.title.setBackgroundResource(R.drawable.coralpink_bg);
            holder.binding.title.setTextColor(ContextCompat.getColor(context, R.color.Deepteal));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
