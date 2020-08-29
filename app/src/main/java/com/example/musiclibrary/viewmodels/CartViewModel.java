package com.example.musiclibrary.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.musiclibrary.repositories.CartRepository;
import com.example.musiclibrary.room.Cart;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private CartRepository mRepository;

    private LiveData<List<Cart>> mAllItems;
    private LiveData<List<Integer>> mAllIdItems;

    public CartViewModel (Application application) {
        super(application);
        mRepository = new CartRepository(application);
        mAllItems = mRepository.getAllItems();
        mAllIdItems = mRepository.getAllIdItems();
    }

    public LiveData<List<Cart>> getAllWords() { return mAllItems; }

    public LiveData<List<Integer>> getAllIDCartItems() { return mAllIdItems; }

    public void insert(Cart item) { mRepository.insert(item); }
}
