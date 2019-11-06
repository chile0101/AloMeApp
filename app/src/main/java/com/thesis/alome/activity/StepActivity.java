package com.thesis.alome.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.thesis.alome.R;
import com.thesis.alome.adapter.TabAdapter;
import com.thesis.alome.fragment.BottomSheetAddAddress;
import com.thesis.alome.fragment.StepOneFragment;
import com.thesis.alome.fragment.StepTwoFragment;
import com.thesis.alome.viewmodel.StepViewModel;

public class StepActivity extends BaseActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    TabAdapter tabAdapter;
    Button btnNext;
    StepViewModel stepViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_step);
        initToolbar(R.id.toolbar,getString(R.string.title_booking_service));
        stepViewModel = ViewModelProviders.of(this).get(StepViewModel.class);
        //Mapping
        viewPager = (ViewPager) findViewById(R.id.view_pager_step);
        tabLayout = (TabLayout) findViewById(R.id.tabStepLayout);
        btnNext = (Button) findViewById(R.id.btnNext);

        tabAdapter = new TabAdapter(getSupportFragmentManager());

        tabAdapter.addFragment(new StepOneFragment(), getString(R.string.step_1));
        tabAdapter.addFragment(new StepTwoFragment(), getString(R.string.step_2));
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next_fragment(v);

            }
        });

        Intent intent = new Intent();
        if(intent != null){
             stepViewModel.setAddress(intent.getStringExtra("address"));
        }
    }

    public void next_fragment(View view) {

        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
        //Change text button
        if(viewPager.getCurrentItem()+1 == 2) {
           btnNext.setText("Confirm");
        }

    }

    public void previous_fragment(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("date","123");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null){
            Log.d("getdata",""+ savedInstanceState.getString("date"));
        }
    }
}
