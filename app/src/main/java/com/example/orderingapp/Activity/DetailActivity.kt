package com.example.orderingapp.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.orderingapp.Activity.Helper.ManagmentCart
import com.example.orderingapp.Domain.ItemsModel
import com.example.orderingapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private lateinit var managmentCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)
        bundle()
    }

    private fun bundle() {
        item = intent.getSerializableExtra("object") as ItemsModel

        Glide.with(this@DetailActivity).load(item.picUrl[0]).into(binding.picMain)

        binding.titleTxt.text = item.title
        binding.descriptionTxt.text = item.description
        binding.priceTxt.text = "$" + item.price
        binding.ratingTxt.text = item.rating.toString()

        // Add to Cart Button
        binding.addToCartBtn.setOnClickListener {
            item.numberIncart = binding.numberItemTxt.text.toString().toInt()
            managmentCart.insertItems(item)
        }

        // Back Button
        binding.backBtn.setOnClickListener {
            startActivity(Intent(this@DetailActivity, MainActivity::class.java))
        }

        // Plus Button (increment quantity)
        binding.plusBtn.setOnClickListener {
            item.numberIncart++
            binding.numberItemTxt.text = item.numberIncart.toString()
        }

        // Minus Button (decrement quantity)
        binding.minusBtn.setOnClickListener {
            if (item.numberIncart > 0) {
                item.numberIncart--
                binding.numberItemTxt.text = item.numberIncart.toString()
            }
        }
    }
}
