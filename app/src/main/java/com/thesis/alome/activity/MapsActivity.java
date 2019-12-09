package com.thesis.alome.activity;

import android.Manifest;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.thesis.alome.R;
import com.thesis.alome.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class MapsActivity extends FragmentActivity implements EasyPermissions.PermissionCallbacks, OnMapReadyCallback {

    @BindView(R.id.fabCurrentLocation) FloatingActionButton fabCurrentLocation;
    @BindView(R.id.fabBack) FloatingActionButton fabBack;
    @BindView(R.id.txtFullAddress) TextView tvAddress;
    @BindView(R.id.btnContinue) Button btnContiue;
    @BindView(R.id.edtSearch) EditText edtSearch;
    @BindView(R.id.layoutAddAddressWrapper) LinearLayout layoutAddAddress;
    @BindView(R.id.bottomSheetAddAddress) LinearLayout bottomSheetAddAddress;
    @BindView(R.id.bottomSheetSearchAddress) LinearLayout bottomSheetSearchAddress;

    private int numOfPressBack;
    private static final int DEFAULT_ZOOM = 18;
    private static final String KEY_LOCATION = "location";
    public final static int RC_LOCATION = 999;
    //private final LatLng mDefaultLocation = new LatLng(10.7733, 106.6594); // BK HCM
    private final String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private LatLng myLocation;
    private Geocoder geocoder;

    private BottomSheetBehavior sheetBehaviorAdd;
    private BottomSheetBehavior sheetBehaviorSearch;

    @Override
    public void onBackPressed() {
        if(sheetBehaviorSearch.getState() == BottomSheetBehavior.STATE_EXPANDED){
            sheetBehaviorSearch.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return;
        }
        if(numOfPressBack == 0){
            super.onBackPressed();
        }else if(numOfPressBack == 1){
            sheetBehaviorAdd.setState(BottomSheetBehavior.STATE_COLLAPSED);
            sheetBehaviorAdd.setHideable(false);
        }
        numOfPressBack -= 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);

        sheetBehaviorAdd = BottomSheetBehavior.from(bottomSheetAddAddress);
        sheetBehaviorSearch = BottomSheetBehavior.from(bottomSheetSearchAddress);

        myLocation = new LatLng(10.7733, 106.6594); // Default BKHCM

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
        }

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLocationPermission();

        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        bottomSheet();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_LOCATION)
    private void getLocationPermission() {

        if (EasyPermissions.hasPermissions(this, perms)) {
            getDeviceLocation();
        } else {
            ActivityCompat.requestPermissions(this, perms, RC_LOCATION);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        getDeviceLocation();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> permss) {
        EasyPermissions.requestPermissions(this,getString(R.string.you_not_allow_location),RC_LOCATION,perms);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getDeviceLocation();
        fabCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocationPermission();
                getDeviceLocation();
            }
        });

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getDeviceLocation() {
        try {
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = task.getResult();
                        myLocation = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                    }
                    mMap.addMarker(new MarkerOptions().position(myLocation)
                            .icon(Utils.bitmapDescriptorFromVector(getApplication(),R.drawable.ic_pin_map)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, DEFAULT_ZOOM));

                    List<Address> addresses;
                    geocoder = new Geocoder(getApplication(), Locale.getDefault());
                    try{
                        addresses = geocoder.getFromLocation(myLocation.latitude, myLocation.longitude, 1);
                        final String address = addresses.get(0).getAddressLine(0);
                        tvAddress.setText(address);
                        btnContiue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.putExtra("addressStr",address);
                                intent.putExtra("addressLatLng",myLocation.latitude + ";" + myLocation.longitude);
                                setResult(RESULT_OK,intent);
                                finish();
                            }
                        });

                    }catch (IOException e){
                        Log.e("Exception", e.getMessage());
                    }
                }
            });

        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void bottomSheet(){
        sheetBehaviorAdd.setHideable(false);
        sheetBehaviorSearch.setHideable(false);
        layoutAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehaviorAdd.setHideable(true);
                sheetBehaviorAdd.setState(BottomSheetBehavior.STATE_HIDDEN);
                numOfPressBack += 1;
            }
        });

        edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                sheetBehaviorSearch.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        sheetBehaviorSearch.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        fabBack.hide();
                        fabCurrentLocation.hide();
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        fabBack.show();
                        fabCurrentLocation.show();
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }
}
