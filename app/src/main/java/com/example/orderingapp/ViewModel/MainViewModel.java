package com.example.orderingapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.orderingapp.Domain.BannerModel;
import com.example.orderingapp.Domain.CategoryModel;
import com.example.orderingapp.Domain.ItemsModel;
import com.example.orderingapp.Repository.MainRepository;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private MainRepository repository = new MainRepository();

    public LiveData<ArrayList<BannerModel>> loadBanner() {
        return repository.loadBanner();
    }

    public LiveData<ArrayList<CategoryModel>> loadCategory() {
        return repository.loadCategory();
    }

    public LiveData<ArrayList<ItemsModel>> loadPopular() {
        return repository.loadPopular();
    }
}
