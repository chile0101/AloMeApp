package com.thesis.alome.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.thesis.alome.model.Address;
import com.thesis.alome.repository.AddressRepository;

import java.util.List;

public class AddressViewModel extends AndroidViewModel {

    private AddressRepository mRepository;

    private LiveData<List<Address>> mAllAddress;

    public AddressViewModel(@NonNull Application application) {
        super(application);
        mRepository = new AddressRepository(application);
        mAllAddress = mRepository.getmAllAddresss();
    }

    public LiveData<List<Address>> getAllAddress(){
        return mAllAddress;
    }

    public void insert(Address address){
        mRepository.insert(address);
    }

    public void delete(Address address){
        mRepository.delete(address);
    }

}
