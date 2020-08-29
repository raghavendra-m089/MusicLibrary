package com.example.musiclibrary.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.musiclibrary.room.Cart;
import com.example.musiclibrary.room.CartDAO;
import com.example.musiclibrary.room.MusicLibraryDatabase;

import java.util.List;

public class CartRepository {

    private CartDAO mCartDAO;
    private LiveData<List<Cart>> allCartItems;
    private LiveData<List<Integer>> allCartIdItems;

    public CartRepository(Application application) {
        MusicLibraryDatabase db = MusicLibraryDatabase.getDatabase(application);
        mCartDAO = db.cartDAO();
        allCartItems = mCartDAO.loadAllItems();
        allCartIdItems = mCartDAO.loadAllIdOfItems();
    }

    public LiveData<List<Cart>> getAllItems() {
        return allCartItems;
    }

    public LiveData<List<Integer>> getAllIdItems() {
        return allCartIdItems;
    }

    public void insert (Cart word) {
        new insertAsyncTask(mCartDAO).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Cart, Void, Void> {

        private CartDAO mAsyncTaskDao;

        insertAsyncTask(CartDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Cart... params) {
            System.out.println(("came here"));
            mAsyncTaskDao.insert(params[0]);
            return null;
        }

    }
}
