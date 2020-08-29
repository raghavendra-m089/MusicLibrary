package com.example.musiclibrary.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.musiclibrary.R;
import com.example.musiclibrary.room.Cart;
import com.example.musiclibrary.viewmodels.CartViewModel;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private TextView mCounter;
    int mCartItemCount = 0;
    private CartViewModel cartViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if(mCounter.getText().toString().equals("0")){
//            mCounter.setVisibility(View.GONE);
//        } else {
//            mCounter.setVisibility(View.VISIBLE);
//        }
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        cartViewModel.getAllWords().observe(this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(@Nullable final List<Cart> items) {
                // Update the cached copy of the words in the adapter.
                mCartItemCount = items.size();
                setupBadge();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_cart);

        View actionView = menuItem.getActionView();
        mCounter = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_cart: {
                // Do something
                if (mCartItemCount == 0)
                    return true;

                Intent i = new Intent(MainActivity.this, CartActivity.class);
                startActivity(i);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupBadge() {

        if (mCounter != null) {
            if (mCartItemCount == 0) {
                if (mCounter.getVisibility() != View.GONE) {
                    mCounter.setVisibility(View.GONE);
                }
            } else {
                mCounter.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (mCounter.getVisibility() != View.VISIBLE) {
                    mCounter.setVisibility(View.VISIBLE);
                }
            }
        }
    }


}
