package com.example.musiclibrary.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musiclibrary.R;
import com.example.musiclibrary.adapters.AlbumSearchResultsAdapter;
import com.example.musiclibrary.models.Album;
import com.example.musiclibrary.models.AlbumResponse;
import com.example.musiclibrary.room.Cart;
import com.example.musiclibrary.viewmodels.AlbumSearchViewModel;
import com.example.musiclibrary.viewmodels.CartViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class AlbumSearchFragment extends Fragment {
    private AlbumSearchViewModel viewModel;
    private CartViewModel cartViewModel;
    private AlbumSearchResultsAdapter adapter;

    private TextInputEditText keywordEditText;
    private Button searchButton;
    private Button date, price, name, cname, track;
    private int value = -1;
    private int oldValue = -1;
    private boolean flag = false;
    private List<Album> globalValue = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new AlbumSearchResultsAdapter(this);

        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        viewModel = ViewModelProviders.of(this).get(AlbumSearchViewModel.class);
        viewModel.init();
        viewModel.getVolumesResponseLiveData().observe(this, volumesResponse -> {
            if (volumesResponse != null) {
                globalValue = viewModel.arrangeAscendingAndDescending(viewModel.removeDuplicates(volumesResponse.getResults()), flag);
                adapter.setResults(globalValue);
                value = 1;
                oldValue = 1;
            }
        });

        cartViewModel.getAllIDCartItems().observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(@Nullable final List<Integer> items) {
                // Update the cached copy of the words in the adapter.
                System.out.println(items);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albumsearch, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.fragment_album_searchResultsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        keywordEditText = view.findViewById(R.id.fragment_album_keyword);
        searchButton = view.findViewById(R.id.fragment_album_search);
        date = view.findViewById(R.id.date);
        price = view.findViewById(R.id.price);
        name = view.findViewById(R.id.name);
        cname = view.findViewById(R.id.collecName);
        track = view.findViewById(R.id.trackName);

        searchButton.setOnClickListener(v -> performSearch());

        date.setOnClickListener(v -> {
            value = 1;
            if(oldValue != value) {
                flag = true;
                oldValue = 1;
            }
            flag = !flag;
            globalValue = viewModel.arrangeAscendingAndDescending(viewModel.removeDuplicates(globalValue), flag);
            adapter.setResults(globalValue);
        });

        price.setOnClickListener(v -> {
            value = 2;
            if(oldValue != value) {
                flag = true;
                oldValue = 2;
            }
            flag = !flag;
            globalValue = viewModel.arrangePriceAscendingAndDescending(viewModel.removeDuplicates(globalValue), flag);
            adapter.setResults(globalValue);
        });

        name.setOnClickListener(v -> {
            value = 3;
            if(oldValue != value) {
                flag = true;
                oldValue = 3;
            }
            flag = !flag;
            globalValue = viewModel.arrangeNameAscendingAndDescending(viewModel.removeDuplicates(globalValue), flag);
            adapter.setResults(globalValue);
        });

        cname.setOnClickListener(v -> {
            value = 4;
            if(oldValue != value) {
                flag = true;
                oldValue = 4;
            }
            flag = !flag;
            globalValue = viewModel.arrangeCollectionNameAscendingAndDescending(viewModel.removeDuplicates(globalValue), flag);
            adapter.setResults(globalValue);
        });

        track.setOnClickListener(v -> {
            value = 5;
            if(oldValue != value) {
                flag = true;
                oldValue = 5;
            }
            flag = !flag;
            globalValue = viewModel.arrangeTrackNameAscendingAndDescending(viewModel.removeDuplicates(globalValue), flag);
            adapter.setResults(globalValue);
        });

        return view;
    }

    public void performSearch() {
        String keyword = keywordEditText.getEditableText().toString();
        viewModel.searchVolumes(keyword);
    }

    public void clickedItem(Album volume) {
        cartViewModel.insert(new Cart(volume.getArtistName(), volume.getCollectionPrice(), volume.getCollectionId()));
    }
}
