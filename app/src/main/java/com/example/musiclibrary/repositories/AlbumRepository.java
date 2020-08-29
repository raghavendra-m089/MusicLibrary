package com.example.musiclibrary.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.musiclibrary.apis.AlbumSearchService;
import com.example.musiclibrary.models.AlbumResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlbumRepository {
    private static final String BOOK_SEARCH_SERVICE_BASE_URL = "https://itunes.apple.com/";

    private AlbumSearchService bookSearchService;
    private MutableLiveData<AlbumResponse> volumesResponseLiveData;

    public AlbumRepository() {
        volumesResponseLiveData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        bookSearchService = new retrofit2.Retrofit.Builder()
                .baseUrl(BOOK_SEARCH_SERVICE_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AlbumSearchService.class);

    }

    public void searchVolumes(String keyword) {
        bookSearchService.searchVolumes(keyword)
                .enqueue(new Callback<AlbumResponse>() {
                    @Override
                    public void onResponse(Call<AlbumResponse> call, Response<AlbumResponse> response) {
                        if (response.body() != null) {
                            volumesResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<AlbumResponse> call, Throwable t) {
                        volumesResponseLiveData.postValue(null);
                    }
                });
    }

    public LiveData<AlbumResponse> getVolumesResponseLiveData() {
        return volumesResponseLiveData;
    }

}
