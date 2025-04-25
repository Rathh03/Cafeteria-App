package com.example.orderingapp.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.orderingapp.Adapta.PopularAdapter;


import com.bumptech.glide.Glide;
import com.example.orderingapp.Adapta.CategoryAdapter;
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

        initBanner();
        initCategory();
        initPopular();
    }

    private void initBanner() {
        binding.progressBarCategory.setVisibility(View.VISIBLE);

        viewModel.loadBanner().observe(this, banners -> {
            if (banners != null && !banners.isEmpty()) {
                Glide.with(MainActivity.this)
                        .load(banners.get(0).getUrl())
                        .into(binding.banner);
            }
            binding.progressBarCategory.setVisibility(View.GONE);
        });
    }

    private void initCategory() {
        binding.progressBarCategory.setVisibility(View.VISIBLE);

        viewModel.loadCategory().observe(this, categories -> {
            binding.recyclerViewCat.setLayoutManager(
                    new LinearLayoutManager(
                            MainActivity.this,
                            LinearLayoutManager.HORIZONTAL,
                            false
                    )
            );
            binding.recyclerViewCat.setAdapter(new CategoryAdapter(new ArrayList<>(categories)));
            binding.progressBarCategory.setVisibility(View.GONE);
        });
    }

//    private fun initPopular(){
//        binding.progrssBarPopular.visibility=View.VISIBLE
//                viewModel.loadPopular().oberverForever{
//            binding.recyclerViewPopular.setLayoutManager= GridLayoutManager(context:this, spanCount:2 );
//            binding.progressBarPopular.visibility=view.Gone
//        }
//        viewModel.loadPopular()
//    }
private void initPopular() {
    binding.progressBarPopular.setVisibility(View.VISIBLE);

    viewModel.loadPopular().observeForever(items -> {
        // Set layout manager
        binding.recyclerViewPopular.setLayoutManager(new GridLayoutManager(this, 2));

        // Set adapter with your items
        binding.recyclerViewPopular.setAdapter(new PopularAdapter(items));

        // Hide progress bar
        binding.progressBarPopular.setVisibility(View.GONE);
    });
}



}
