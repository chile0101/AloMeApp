package com.thesis.alome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.thesis.alome.R;
import com.thesis.alome.adapter.MpagerAdapter;

public class LandingActivity extends AppCompatActivity {


    private ViewPager mPager;
    private int[] layouts = {R.layout.slide_clean_service,R.layout.slide_electronic_service,R.layout.slide_plumbing_service};
    private MpagerAdapter mpagerAdapter;
    private LinearLayout dotsLayout;
    private ImageView[] dots;
    private Button btnSigninup;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        mapping();

        mpagerAdapter = new MpagerAdapter(layouts,this);
        mPager.setAdapter(mpagerAdapter);

        createDots(0);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                createDots(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        btnSigninup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this,SignInSignUpActivity.class);
                startActivity(intent);

            }
        });
    }

    private void mapping(){
        mPager = (ViewPager) findViewById(R.id.viewPager);
        dotsLayout = (LinearLayout) findViewById(R.id.dotsLayout);
        btnSigninup = (Button) findViewById(R.id.btnSignInSignUp);
    }

    private void createDots(int current_position){

        dotsLayout.removeAllViews();
        dots = new ImageView[layouts.length];

        for(int i = 0;i<layouts.length;i++){
            dots[i] = new ImageView(this);
            if(i==current_position){
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.dots_active));
            }else{
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.dots_default));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);
            dotsLayout.addView(dots[i],params);
        }
    }
}
