package com.thesis.alome.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.thesis.alome.R;
import com.thesis.alome.fragment.SettingFragment;

public class SettingActivity extends BaseActivity{

    FrameLayout contentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        contentContainer = (FrameLayout) findViewById(R.id.contentContainer);

        if(contentContainer != null){
            if (savedInstanceState != null) {
                return;
            }

            SettingFragment sf = new SettingFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer,sf).commit();
        }
    }

    public void setToolBarTitle(String title){
        initToolbar(R.id.toolbar,title);
    }
}
