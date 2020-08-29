package com.example.musiclibrary.apis;


import com.example.musiclibrary.models.AlbumResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AlbumSearchService {
    @GET("/search")
    Call<AlbumResponse> searchVolumes(
            @Query("term") String query
    );
}
