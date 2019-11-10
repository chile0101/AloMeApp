package com.thesis.alome.activity;

import android.Manifest;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.thesis.alome.R;
import com.thesis.alome.adapter.ProviderListRcvAdapter;
import com.thesis.alome.model.Provider;

import java.util.ArrayList;
import java.util.List;

import im.delight.android.location.SimpleLocation;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ProviderListActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{

    RecyclerView recyclerView;
    List<Provider> providerList;
    ProviderListRcvAdapter adapter;
    public final static int RC_LOCATION = 999;
    private final String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
    private SimpleLocation location;
    private LatLng mLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_list);
        initToolbar(R.id.toolbar,getString(R.string.title_activity_provider_list));
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        getLocationPermission();


    }

    @Override
    protected void onResume() {
        super.onResume();
        //location.beginUpdates();
    }

    @Override
    protected void onPause() {
        //location.endUpdates();
        super.onPause();
    }

    public void setAdapter(){
        location = new SimpleLocation(this);
        mLatLng = new LatLng(location.getLatitude(),location.getLongitude());
        Log.d("location",mLatLng.latitude + "");
        providerList = new ArrayList<Provider>();
        providerList.add(new Provider(1,
                "Điện máy hoàng gia Huỳnh Việt",
                "Chuyên sửa chữa các thiết bị điện",
                4.4f,
                5,
                "https://scontent.fsgn6-2.fna.fbcdn.net/v/t1.0-1/p160x160/49547387_2216482051959552_5817986978611724288_o.jpg?_nc_cat=108&_nc_eui2=AeE97DiKmRUVdKqWuCRnoWcrMBzZiN6xIxfv0UIdY0SPDCV9_3hCPGd4Tqasx2g6I-0FB4uemE6ud5g94eIbuhDotjy25MQVuo54nST8W6lfsA&_nc_oc=AQkbYOh_lId2Xx_FV8Bc7N_N_WUz8MiZATHSr2w3W0pDBKLxV8_ACVehdiNuam9VzNg&_nc_ht=scontent.fsgn6-2.fna&oh=4369ea226b0988374e0a45b3befeb9e2&oe=5E51477F"));
        providerList.add(new Provider(1,
                "Điện máy hoàng gia Huỳnh Việt",
                "Chuyên sửa chữa các thiết bị điện",
                4.4f,
                5,
                "https://scontent.fsgn6-2.fna.fbcdn.net/v/t1.0-1/p160x160/49547387_2216482051959552_5817986978611724288_o.jpg?_nc_cat=108&_nc_eui2=AeE97DiKmRUVdKqWuCRnoWcrMBzZiN6xIxfv0UIdY0SPDCV9_3hCPGd4Tqasx2g6I-0FB4uemE6ud5g94eIbuhDotjy25MQVuo54nST8W6lfsA&_nc_oc=AQkbYOh_lId2Xx_FV8Bc7N_N_WUz8MiZATHSr2w3W0pDBKLxV8_ACVehdiNuam9VzNg&_nc_ht=scontent.fsgn6-2.fna&oh=4369ea226b0988374e0a45b3befeb9e2&oe=5E51477F"));

        providerList.add(new Provider(1,
                "Điện máy hoàng gia",
                "Chuyên sửa chữa các thiết bị điện",
                4.4f,
                5,
                "https://scontent.fsgn6-2.fna.fbcdn.net/v/t1.0-1/p160x160/49547387_2216482051959552_5817986978611724288_o.jpg?_nc_cat=108&_nc_eui2=AeE97DiKmRUVdKqWuCRnoWcrMBzZiN6xIxfv0UIdY0SPDCV9_3hCPGd4Tqasx2g6I-0FB4uemE6ud5g94eIbuhDotjy25MQVuo54nST8W6lfsA&_nc_oc=AQkbYOh_lId2Xx_FV8Bc7N_N_WUz8MiZATHSr2w3W0pDBKLxV8_ACVehdiNuam9VzNg&_nc_ht=scontent.fsgn6-2.fna&oh=4369ea226b0988374e0a45b3befeb9e2&oe=5E51477F"));



        adapter = new ProviderListRcvAdapter(this,providerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @AfterPermissionGranted(RC_LOCATION)
    private void getLocationPermission() {

        if (EasyPermissions.hasPermissions(this, perms)) {

            setAdapter();
        } else {
            ActivityCompat.requestPermissions(this, perms, RC_LOCATION);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
//        location = new SimpleLocation(this);
//        mLatLng = new LatLng(location.getLatitude(),location.getLongitude());
//        Log.d("location",mLatLng.latitude + "");
        setAdapter();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> permss) {
        EasyPermissions.requestPermissions(this,getString(R.string.you_not_allow_location),RC_LOCATION,perms);
    }
}
