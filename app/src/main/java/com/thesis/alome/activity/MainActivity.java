package com.thesis.alome.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.thesis.alome.R;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.config.ApiServices;
import com.thesis.alome.config.PrefUtils;
import com.thesis.alome.fragment.MoreFragment;
import com.thesis.alome.fragment.ServiceListFragment;
import com.thesis.alome.fragment.ServicesFragment;
import com.thesis.alome.model.Customer;
import com.thesis.alome.model.RespBase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    TextView tvWelcome;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseMessaging.getInstance().subscribeToTopic("customer"+  getSharedPreferences("alome", Context.MODE_PRIVATE).getString("TOPIC",""));

        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        bottomNav = (BottomNavigationView) findViewById(R.id.bottomNavView);

        ApiServices apiServices2 = ApiClient.getClient(this).create(ApiServices.class);
        Call<RespBase<Customer>> callProfile = apiServices2.getProfile(PrefUtils.getApiKey(this));
        callProfile.enqueue(new Callback<RespBase<Customer>>() {
            @Override
            public void onResponse(Call<RespBase<Customer>> call, Response<RespBase<Customer>> response) {

                Long id = response.body().getData().getId().longValue();
                String userName = response.body().getData().getUsername();   // is email
                String fullName = response.body().getData().getFullName();
                String shortName = fullName.split(" ",2)[0];
                String phone = response.body().getData().getPhone();
                String address = response.body().getData().getAddress();
                String longitude = response.body().getData().getLongitude();
                String latitude = response.body().getData().getLatitude();
                String gender = response.body().getData().getGenderText();
                String dateOfBirth = response.body().getData().getDateOfBirth();
                String avatar = response.body().getData().getAvatar();
                PrefUtils.storeProfile(getApplicationContext(), id, userName, shortName,fullName, phone, address, latitude, longitude,gender,dateOfBirth,avatar);

                SharedPreferences myPrefs = getSharedPreferences("alome", MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor;
                prefsEditor = myPrefs.edit();
                prefsEditor.putString("TOPIC", id.toString());
                prefsEditor.commit();
            }
            @Override
            public void onFailure(Call<RespBase<Customer>> call, Throwable t) {
            }
        });

        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,
                new ServicesFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.item1:
                    selectedFragment = new ServicesFragment();
                    break;
                case R.id.item2:
                    selectedFragment = new ServiceListFragment();
                    break;
                case R.id.item4:
                    selectedFragment = new MoreFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,
                    selectedFragment).commit();
            return true;
        }
    };

    public void setTitleToolBar(String str){
        tvWelcome.setText(str);
    }


}
