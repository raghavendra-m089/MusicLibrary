package com.example.musiclibrary.room;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart")
public class Cart {
    @PrimaryKey(autoGenerate = true)
    int id;
    String albumName;
    Double price;
    Integer trackId;

    @Ignore
    public Cart(String albumName, Double price, Integer trackId) {
        this.albumName = albumName;
        this.price = price;
        this.trackId = trackId;
    }

    public Cart(int id, String albumName, Double price, Integer trackId) {
        this.id = id;
        this.albumName = albumName;
        this.price = price;
        this.trackId = trackId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTrackId() {
        return trackId;
    }

    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }
}
