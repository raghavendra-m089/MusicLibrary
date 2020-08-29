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

    public void delete (Integer trackId) {
        new deleteAsyncTask(mCartDAO).execute(trackId);
    }

    private static class insertAsyncTask extends AsyncTask<Cart, Void, Void> {

        private CartDAO mAsyncTaskDao;

        insertAsyncTask(CartDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Cart... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }

    }

    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {

        private CartDAO mAsyncTaskDao;

        deleteAsyncTask(CartDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }

    }
}
