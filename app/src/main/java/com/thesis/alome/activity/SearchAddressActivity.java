package com.thesis.alome.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.thesis.alome.R;
import com.thesis.alome.adapter.PlacesAutoCompleteAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAddressActivity extends AppCompatActivity implements PlacesAutoCompleteAdapter.ClickListener {

    // Fragment search address
    @BindView(R.id.edtSearch) EditText edtSearch;
    @BindView(R.id.rcvSearchLocation) RecyclerView rcvSearchLocation;

    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);
        ButterKnife.bind(this);

        Places.initialize(this, getResources().getString(R.string.google_maps_key));
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
        //Toast.makeText(this, place.getAddress()+", "+place.getLatLng().latitude+place.getLatLng().longitude, Toast.LENGTH_SHORT).show();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("address",place.getAddress());
        returnIntent.putExtra("lat",place.getLatLng().latitude);
        returnIntent.putExtra("lng",place.getLatLng().longitude);

        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

}
