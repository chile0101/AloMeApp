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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.thesis.alome.R;
import com.thesis.alome.adapter.PlacesAutoCompleteAdapter;
import com.thesis.alome.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class MapsActivity extends FragmentActivity implements EasyPermissions.PermissionCallbacks, OnMapReadyCallback, PlacesAutoCompleteAdapter.ClickListener {

    // Map activity
    @BindView(R.id.fabCurrentLocation) FloatingActionButton fabCurrentLocation;
    @BindView(R.id.fabBack) FloatingActionButton fabBack;

    // Fragment add address
    @BindView(R.id.txtFullAddress) TextView tvAddress;
    @BindView(R.id.btnContinue) Button btnContiue;
    @BindView(R.id.layoutAddAddressWrapper) LinearLayout layoutAddAddress;
    @BindView(R.id.bottomSheetAddAddress) LinearLayout bottomSheetAddAddress;

    // Fragment search address
    @BindView(R.id.edtSearch) EditText edtSearch;
    @BindView(R.id.bottomSheetSearchAddress) LinearLayout bottomSheetSearchAddress;
    @BindView(R.id.rcvSearchLocation) RecyclerView rcvSearchLocation;

    // Handing back button
    private int numOfPressBack;

    private GoogleMap mMap;
    private CameraPosition mCameraPosition;
    private LatLng myLocation;
    private Geocoder geocoder;

    // permission
    private final String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    // Keys for storing activity state.
    public final static int RC_LOCATION = 999;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(10.7733305, 106.6594227);
    //private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 18;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    // Bottom sheet
    private BottomSheetBehavior sheetBehaviorAdd;
    private BottomSheetBehavior sheetBehaviorSearch;
    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;

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

         //Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Handing bottom sheet fragment
        sheetBehaviorAdd = BottomSheetBehavior.from(bottomSheetAddAddress);
        sheetBehaviorSearch = BottomSheetBehavior.from(bottomSheetSearchAddress);
        handingBottomSheetSearch();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLocationPermission();
        fabCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocationPermission();
            }
        });

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        setDeviceLocationDefault();
    }

    private void setDeviceLocationDefault(){
        myLocation = mDefaultLocation;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
        handingBottomSheetAdd();
    }

    private void getDeviceLocation() {
        try {
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        // Set the map's camera position to the current location of the device.

                        mLastKnownLocation = task.getResult();
                        myLocation = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
//                        mMap.addMarker(new MarkerOptions().position(myLocation)
//                                                        .icon(Utils.bitmapDescriptorFromVector(getApplication(),
//                                                                                        R.drawable.ic_pin_map)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, DEFAULT_ZOOM));
                        handingBottomSheetAdd();
                    }else{
                        setDeviceLocationDefault();
                    }
                }
            });

        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void handingBottomSheetAdd(){
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

    private void handingBottomSheetSearch(){
        sheetBehaviorAdd.setHideable(false);
        sheetBehaviorSearch.setHideable(false);
        sheetBehaviorSearch.setState(BottomSheetBehavior.STATE_HIDDEN);
        layoutAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehaviorAdd.setHideable(true);
                sheetBehaviorAdd.setState(BottomSheetBehavior.STATE_HIDDEN);
                sheetBehaviorSearch.setState(BottomSheetBehavior.STATE_COLLAPSED);
                numOfPressBack += 1;
            }
        });


        edtSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sheetBehaviorSearch.setState(BottomSheetBehavior.STATE_EXPANDED);
                return false;
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
                    case BottomSheetBehavior.STATE_HIDDEN:
                        fabBack.show();
                        fabCurrentLocation.show();
                        break;
                }
            }
            @Override
            public void onSlide(@NonNull View view, float v) { }
        });

        Places.initialize(this, getResources().getString(R.string.google_maps_key_active));
        edtSearch.addTextChangedListener(filterTextWatcher);
        mAutoCompleteAdapter = new PlacesAutoCompleteAdapter(this);
        rcvSearchLocation.setLayoutManager(new LinearLayoutManager(this));
        mAutoCompleteAdapter.setClickListener(this);
        rcvSearchLocation.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteAdapter.notifyDataSetChanged();
    }


    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            if (!s.toString().equals("")) {
                mAutoCompleteAdapter.getFilter().filter(s.toString());
                if (rcvSearchLocation.getVisibility() == View.GONE) {rcvSearchLocation.setVisibility(View.VISIBLE);}
            } else {
                if (rcvSearchLocation.getVisibility() == View.VISIBLE) {rcvSearchLocation.setVisibility(View.GONE);}
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        public void onTextChanged(CharSequence s, int start, int before, int count) { }
    };

    @Override
    public void click(Place place) {
        Toast.makeText(this, place.getAddress()+", "+place.getLatLng().latitude+place.getLatLng().longitude, Toast.LENGTH_SHORT).show();
        myLocation = new LatLng(place.getLatLng().latitude,place.getLatLng().longitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, DEFAULT_ZOOM));
        handingBottomSheetAdd();

        sheetBehaviorSearch.setHideable(true);
        sheetBehaviorSearch.setState(BottomSheetBehavior.STATE_HIDDEN);

        sheetBehaviorAdd.setHideable(false);
        sheetBehaviorAdd.setState(BottomSheetBehavior.STATE_EXPANDED);

    }
}
