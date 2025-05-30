package com.example.orderingapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.orderingapp.Helper.ManagmentCart;
import com.example.orderingapp.Domain.ItemsModel;
import com.example.orderingapp.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private ItemsModel item;
    private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // enableEdgeToEdge() is an extension function in Kotlin,
        // in Java, you might need to set window flags manually if needed.
        // For now, you can skip or implement manually if necessary.

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);
        bundle();
    }

    private void bundle() {
        // getSerializableExtra returns Serializable, so cast it to ItemsModel
        item = (ItemsModel) getIntent().getSerializableExtra("object");

        Glide.with(DetailActivity.this).load(item.getPicUrl().get(0)).into(binding.picMain);

        binding.titleTxt.setText(item.getTitle());
        binding.descriptionTxt.setText(item.getDescription());
        binding.priceTxt.setText("$" + item.getPrice());
        binding.ratingTxt.setText(String.valueOf(item.getRating()));

        // Add to Cart Button
        binding.addToCartBtn.setOnClickListener(v -> {
            item.setNumberIncart(Integer.parseInt(binding.numberItemTxt.getText().toString()));
            managmentCart.insertItems(item);
        });

        // Back Button
        binding.backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Plus Button (increment quantity)
        binding.plusBtn.setOnClickListener(v -> {
            item.setNumberIncart(item.getNumberIncart() + 1);
            binding.numberItemTxt.setText(String.valueOf(item.getNumberIncart()));
        });

        // Minus Button (decrement quantity)
        binding.minusBtn.setOnClickListener(v -> {
            if (item.getNumberIncart() > 0) {
                item.setNumberIncart(item.getNumberIncart() - 1);
                binding.numberItemTxt.setText(String.valueOf(item.getNumberIncart()));
            }
        });
    }
}
