package com.example.orderingapp.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.activity.EdgeToEdge;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.orderingapp.Adapter.CategoryAdapter;
import com.example.orderingapp.Adapter.PopularAdapter;
import com.example.orderingapp.ViewModel.MainViewModel;
import com.example.orderingapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);


        initCategory();
        initPopular();

// Order click listener
        binding.layoutOrder.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OrderSummaryActivity.class);
            startActivity(intent);
        });

// Cart click listener
        binding.layoutCart.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Cart clicked!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, OrderingListActivity.class);
            startActivity(intent);
        });


    }


    private void initCategory() {
        binding.progressBarCategory.setVisibility(View.VISIBLE);

        viewModel.loadCategory().observe(this, categories -> {
            Log.d("MainActivity", "Categories loaded: " + (categories != null ? categories.size() : 0));
            if (categories != null && !categories.isEmpty()) {
                binding.recyclerViewCat.setLayoutManager(
                        new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false)
                );
                binding.recyclerViewCat.setAdapter(new CategoryAdapter(new ArrayList<>(categories)));
            } else {
                Log.d("MainActivity", "No categories found");
            }
            binding.progressBarCategory.setVisibility(View.GONE);
        });
    }

    private void initPopular() {
        binding.progressBarPopular.setVisibility(View.VISIBLE);

        viewModel.loadPopular().observe(this, items -> {
            Log.d("MainActivity", "Popular items loaded: " + (items != null ? items.size() : 0));
            if (items != null && !items.isEmpty()) {
                binding.recyclerViewPopular.setLayoutManager(new GridLayoutManager(this, 2));
                binding.recyclerViewPopular.setAdapter(new PopularAdapter(items));
            } else {
                Log.d("MainActivity", "No popular items found");
            }
            binding.progressBarPopular.setVisibility(View.GONE);
        });
    }
}
