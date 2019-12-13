package com.thesis.alome.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.thesis.alome.R;
import com.thesis.alome.adapter.TabAdapter;
import com.thesis.alome.config.FragmentLifecycle;
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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int i) {
                FragmentLifecycle fragment = (FragmentLifecycle) tabAdapter.instantiateItem(viewPager,i);
                if (fragment != null) {
                    fragment.onResumeFragment();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {}
        });
    }

    private void mapping(){
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    public void switchSignInFragment(){
       viewPager.setCurrentItem(0);
    }
}
