package com.example.musiclibrary.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface CartDAO {

    @Query("SELECT * FROM Cart ORDER BY ID")
    LiveData<List<Cart>> loadAllItems();

    @Query("SELECT trackId FROM Cart ORDER BY ID")
    LiveData<List<Integer>> loadAllIdOfItems();

    @Insert
    void insert(Cart cart);

    @Delete
    void delete(Cart cart);

}
