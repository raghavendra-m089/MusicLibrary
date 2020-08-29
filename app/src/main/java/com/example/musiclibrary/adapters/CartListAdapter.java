package com.example.musiclibrary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.musiclibrary.R;
import com.example.musiclibrary.room.Cart;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.WordViewHolder> {

    private final LayoutInflater mInflater;
    private List<Cart> mWords; // Cached copy of words

    public CartListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.cart_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if (mWords != null) {
            Cart current = mWords.get(position);
            holder.cartItem.setText(current.getAlbumName());
            holder.price.setText("\u0024 "+current.getPrice());
        } else {
            // Covers the case of data not being ready yet.
            holder.cartItem.setText("No Item");
        }
    }

    public void setCart(List<Cart> words){
        mWords = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView cartItem, price;

        private WordViewHolder(View itemView) {
            super(itemView);
            cartItem = itemView.findViewById(R.id.item);
            price = itemView.findViewById(R.id.price);
        }
    }
}