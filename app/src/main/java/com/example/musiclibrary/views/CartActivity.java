package com.example.musiclibrary.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.musiclibrary.R;
import com.example.musiclibrary.adapters.CartListAdapter;
import com.example.musiclibrary.room.Cart;
import com.example.musiclibrary.viewmodels.CartViewModel;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final CartListAdapter adapter = new CartListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        cartViewModel.getAllWords().observe(this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(@Nullable final List<Cart> items) {
                // Update the cached copy of the words in the adapter.
                adapter.setCart(items);
            }
        });
    }
}