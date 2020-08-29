package com.example.musiclibrary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musiclibrary.R;
import com.example.musiclibrary.models.Album;
import com.example.musiclibrary.room.Cart;
import com.example.musiclibrary.viewmodels.AlbumSearchViewModel;
import com.example.musiclibrary.viewmodels.CartViewModel;
import com.example.musiclibrary.views.AlbumSearchFragment;
import com.example.musiclibrary.views.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AlbumSearchResultsAdapter extends RecyclerView.Adapter<AlbumSearchResultsAdapter.AlbumSearchResultHolder> {
    private List<Album> results = new ArrayList<>();
    private AlbumSearchFragment context;
    private List<Integer> cartIds = new ArrayList<>();

    public AlbumSearchResultsAdapter(AlbumSearchFragment context) {
        this.context = context;

    }

    @NonNull
    @Override
    public AlbumSearchResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_item, parent, false);


        return new AlbumSearchResultHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumSearchResultHolder holder, int position) {
        Album volume = results.get(position);

        holder.artist_name_title.setText(volume.getArtistName());
        //2009-09-29T12:00:00Z
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = inputFormat.parse(volume.getReleaseDate());
            String formattedDate = outputFormat.format(date);
            holder.releasedDate.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(cartIds.contains(volume.getCollectionId())){
            holder.cartAdd.setBackground(context.getResources().getDrawable(R.drawable.ic_baseline_remove_shopping_cart_24));
        } else {
            holder.cartAdd.setBackground(context.getResources().getDrawable(R.drawable.ic_baseline_add_shopping_cart_24));
        }

        if (volume.getArtworkUrl100() != null) {
            String imageUrl = volume.getArtworkUrl100();

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.smallThumbnailImageView);
        }

        if (volume.getCollectionName() != null) {
            holder.collection_name_title.setText(volume.getCollectionName());
        }

        if (volume.getCollectionPrice() != null) {
            holder.collection_price.setText("\u0024 "+volume.getCollectionPrice());
        }

        holder.cartAdd.setOnClickListener(v -> {
            if(cartIds.contains(volume.getCollectionId())){
                context.deleteItem(volume.getCollectionId());
            } else {
                context.clickedItem(volume);
            }

        });


    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Album> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public void setChangeCartValues(List<Integer> cartId) {
        this.cartIds = cartId;
        notifyDataSetChanged();
    }

    class AlbumSearchResultHolder extends RecyclerView.ViewHolder {
        private TextView artist_name_title;
        private TextView collection_name_title;
        private TextView collection_price;
        private TextView releasedDate;
        private ImageView smallThumbnailImageView, cartAdd;

        public AlbumSearchResultHolder(@NonNull View itemView) {
            super(itemView);

            artist_name_title = itemView.findViewById(R.id.artist_name_title);
            collection_name_title = itemView.findViewById(R.id.collection_name_title);
            collection_price = itemView.findViewById(R.id.collection_price);
            releasedDate = itemView.findViewById(R.id.releasedDate);
            smallThumbnailImageView = itemView.findViewById(R.id.album_item_smallThumbnail);
            cartAdd  = itemView.findViewById(R.id.cartAdd);
        }
    }

}
