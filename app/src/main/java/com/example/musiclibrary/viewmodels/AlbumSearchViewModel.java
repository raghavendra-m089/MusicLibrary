package com.example.musiclibrary.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.musiclibrary.models.Album;
import com.example.musiclibrary.models.AlbumResponse;
import com.example.musiclibrary.repositories.AlbumRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AlbumSearchViewModel extends AndroidViewModel {
    private AlbumRepository bookRepository;
    private LiveData<AlbumResponse> volumesResponseLiveData;

    public AlbumSearchViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        bookRepository = new AlbumRepository();
        volumesResponseLiveData = bookRepository.getVolumesResponseLiveData();
    }

    public void searchVolumes(String keyword) {
        bookRepository.searchVolumes(keyword);
    }

    public LiveData<AlbumResponse> getVolumesResponseLiveData() {
        return volumesResponseLiveData;
    }

    public List<Album> removeDuplicates(List<Album> originalList) {
        // Create a new ArrayList
        ArrayList<Album> newList = new ArrayList<>();
        ArrayList<String> title = new ArrayList<>();

        // Traverse through the first list
        for (Album element : originalList) {

            // If this element is not present in newList
            // then add it
            if (!title.contains(element.getArtistName())) {
                newList.add(element);
                title.add(element.getArtistName());
            }
        }

        // return the new list
        return newList;
    }

    public List<Album> arrangeNameAscendingAndDescending(List<Album> originalList, boolean isAsc) {
        if (isAsc)
            originalList.sort((d1, d2) -> d1.getArtistName().compareTo(d2.getArtistName()));
        else
            originalList.sort((d1, d2) -> d2.getArtistName().compareTo(d1.getArtistName()));

        return originalList;
    }

    public List<Album> arrangeCollectionNameAscendingAndDescending(List<Album> originalList, boolean isAsc) {
        try{
            if (isAsc)
                originalList.sort((d1, d2) -> d1.getCollectionName().compareTo(d2.getCollectionName()));
            else
                originalList.sort((d1, d2) -> d2.getCollectionName().compareTo(d1.getCollectionName()));
        } catch (Exception e) {

        }
        return originalList;
    }

    public List<Album> arrangeTrackNameAscendingAndDescending(List<Album> originalList, boolean isAsc) {
        if (isAsc)
            originalList.sort((d1, d2) -> d1.getTrackName().compareTo(d2.getTrackName()));
        else
            originalList.sort((d1, d2) -> d2.getTrackName().compareTo(d1.getTrackName()));

        return originalList;
    }


    public List<Album> arrangePriceAscendingAndDescending(List<Album> originalList, boolean isAsc) {
        if (isAsc)
            originalList.sort((d1, d2) -> Double.compare(d1.getCollectionPrice(), d2.getCollectionPrice()));
        else
            originalList.sort((d1, d2) -> Double.compare(d2.getCollectionPrice(), d1.getCollectionPrice()));

        return originalList;
    }

    public List<Album> arrangeAscendingAndDescending(List<Album> originalList, boolean isAsc) {
        if (isAsc)
            originalList.sort((d1, d2) -> d1.getReleaseDate().compareTo(d2.getReleaseDate()));
        else
            originalList.sort((d1, d2) -> d2.getReleaseDate().compareTo(d1.getReleaseDate()));

        return originalList;
    }
}
