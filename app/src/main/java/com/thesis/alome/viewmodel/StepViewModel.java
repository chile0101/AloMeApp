package com.thesis.alome.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.thesis.alome.model.Address;

import java.util.List;


public class StepViewModel extends ViewModel {

    private final MutableLiveData<String> dateAvail = new MutableLiveData<String>();
    private final MutableLiveData<String> timeAvail = new MutableLiveData<String>();
    private final MutableLiveData<String> phone = new MutableLiveData<>();
    private final MutableLiveData<String> address = new MutableLiveData<String>();
    private final MutableLiveData<String> addressLatLng = new MutableLiveData<String>();
    private final MutableLiveData<Long> typeId = new MutableLiveData<Long>();
    private final MutableLiveData<String> typeStr = new MutableLiveData<String>();
    private final MutableLiveData<List<Uri>> imgList = new MutableLiveData<List<Uri>>();
    private final MutableLiveData<String> description = new MutableLiveData<String>();

    private final MutableLiveData<String> dateErr = new MutableLiveData<>();
    private final MutableLiveData<String> timeErr = new MutableLiveData<>();
    private final MutableLiveData<String> phoneErr = new MutableLiveData<>();
    private final MutableLiveData<String> addressErr = new MutableLiveData<>();
    private final MutableLiveData<String> descErr = new MutableLiveData<>();
    private final MutableLiveData<String> imagesErr = new MutableLiveData<>();



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

    public LiveData<String> getPhone() {
        return phone;
    }

    public void setPhone(String phone){
        this.phone.setValue(phone);
    }

    public void setAddress(String addressParam){
        address.setValue(addressParam);
    }

    public LiveData<String> getAddress(){
        return this.address;
    }

    public LiveData<String> getAddressLatLng(){
        return addressLatLng;
    }

    public void setAddressLatLng(String latLng){
        addressLatLng.setValue(latLng);
    }

    public LiveData<Long> getTypeId(){
        return typeId;
    }

    public void setTypeId(Long typeId){
        this.typeId.setValue(typeId);
    }

    public LiveData<String> getTypeStr(){
        return typeStr;
    }

    public void setTypeStr(String typeStr){
        this.typeStr.setValue(typeStr);
    }

    public void setImageList(List<Uri> imagesList){
        imgList.setValue(imagesList);
    }

    public LiveData<List<Uri>> getImageList(){
        return this.imgList;
    }

    public LiveData<String> getDescription() {
        return description;
    }

    public void setDescription(String desc){
        description.setValue(desc);
    }

    public LiveData<String> getDateErr() {
        return dateErr;
    }

    public void setDateErr(String err){
        dateErr.setValue(err);
    }

    public LiveData<String> getTimeErr() {
        return timeErr;
    }

    public void setTimeErr(String err){
        timeErr.setValue(err);
    }

    public LiveData<String> getPhoneErr() {
        return phoneErr;
    }

    public void setPhoneErr(String err){
        phoneErr.setValue(err);
    }

    public LiveData<String> getAddressErr() {
        return addressErr;
    }

    public void setAddressErr(String err){
        addressErr.setValue(err);
    }

    public LiveData<String> getImagesErr() {
        return imagesErr;
    }

    public void setImagesErr(String err){
        imagesErr.setValue(err);
    }

    public LiveData<String> getDescErr() {
        return descErr;
    }

    public void setDescErr(String err){
        descErr.setValue(err);
    }

}
