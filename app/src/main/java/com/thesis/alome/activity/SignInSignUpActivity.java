package com.thesis.alome.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.thesis.alome.R;
import com.thesis.alome.adapter.TabAdapter;
import com.thesis.alome.fragment.SignInFragment;
import com.thesis.alome.fragment.SignUpFragment;

public class SignInSignUpActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private TabAdapter tabAdapter;
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_sign_up);

        mapping();

        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new SignInFragment(), getString(R.string.log_in));
        tabAdapter.addFragment(new SignUpFragment(), getString(R.string.register));
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void mapping(){
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    public void switchSignInFragment(){
       viewPager.setCurrentItem(0);
    }
}
