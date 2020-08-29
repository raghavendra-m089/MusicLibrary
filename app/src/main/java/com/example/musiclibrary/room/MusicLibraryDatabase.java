package com.example.musiclibrary.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Cart.class}, version = 1, exportSchema = false)
public abstract class MusicLibraryDatabase extends RoomDatabase {

    public abstract CartDAO cartDAO();
    private static MusicLibraryDatabase INSTANCE;

    public static MusicLibraryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MusicLibraryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MusicLibraryDatabase.class, "music_library_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
