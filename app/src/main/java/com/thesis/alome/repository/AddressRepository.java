package com.thesis.alome.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.thesis.alome.dao.AddressDao;
import com.thesis.alome.database.AppDatabase;
import com.thesis.alome.model.Address;

import java.util.List;

public class AddressRepository {
    private AddressDao mAddressDao;
    private LiveData<List<Address>> mAllAddresss;

    public AddressRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        this.mAddressDao = db.addressDao();
        this.mAllAddresss = mAddressDao.findAllAddressSysc();
    }

    public LiveData<List<Address>> getmAllAddresss(){
        return mAllAddresss;
    }

    public void insert(Address address){
        new insertAsyncTask(mAddressDao).execute(address);
    }

    private static class insertAsyncTask extends AsyncTask<Address, Void, Void> {

        private AddressDao mAsyncTaskDao;

        insertAsyncTask(AddressDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Address... params) {
            mAsyncTaskDao.insertAddress(params[0]);
            return null;
        }
    }


    public void delete(Address address){
        new deleteAddressAsyncTask(mAddressDao).execute(address);
    }
    private static class deleteAddressAsyncTask extends AsyncTask<Address, Void, Void> {
        private AddressDao mAsyncTaskDao;

        deleteAddressAsyncTask(AddressDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Address... params) {
            mAsyncTaskDao.deleteAddress(params[0]);
            return null;
        }
    }
}
