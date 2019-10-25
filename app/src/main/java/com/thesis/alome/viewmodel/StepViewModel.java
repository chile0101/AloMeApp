package com.thesis.alome.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class StepViewModel extends ViewModel {

    private final MutableLiveData<String> dateAvail = new MutableLiveData<String>();
    private final MutableLiveData<String> timeAvail = new MutableLiveData<String>();

    public void setDateAvail(String dateStr){
        dateAvail.setValue(dateStr);
    }

    public LiveData<String> getDateAvail(){
        return this.dateAvail;
    }

    public void setTimeAvail(String timeStr){
        timeAvail.setValue(timeStr);
    }

    public LiveData<String> getTimeAvail(){
        return this.timeAvail;
    }


}
