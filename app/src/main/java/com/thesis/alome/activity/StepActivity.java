package com.thesis.alome.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.thesis.alome.R;
import com.thesis.alome.adapter.TabAdapter;
import com.thesis.alome.fragment.StepOneFragment;
import com.thesis.alome.fragment.StepTwoFragment;

public class StepActivity extends BaseActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    TabAdapter tabAdapter;
    Button btnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        initToolbar(R.id.toolbar,getString(R.string.title_booking_service));

        //Mapping
        viewPager = (ViewPager) findViewById(R.id.view_pager_step);
        tabLayout = (TabLayout) findViewById(R.id.tabStepLayout);
        btnNext = (Button) findViewById(R.id.btnNext);


        Toast.makeText(this, ""+ viewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();


        tabAdapter = new TabAdapter(getSupportFragmentManager());
       

        tabAdapter.addFragment(new StepOneFragment(), "Step 1");
        tabAdapter.addFragment(new StepTwoFragment(), "Step 2");
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next_fragment(v);

            }
        });

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

}
