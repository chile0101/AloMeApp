package com.thesis.alome.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.widget.MsgView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.thesis.alome.R;
import com.thesis.alome.fragment.ProviderInfoFragment;
import com.thesis.alome.fragment.ProviderRatingsFragment;
import com.thesis.alome.model.Provider;
import com.thesis.alome.utils.ViewFindUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProviderDetailsActivity extends BaseActivity {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"Đánh giá","Thông tin"};
    private View mDecorView;
    private SegmentTabLayout mTabLayout;
    @BindView(R.id.imgAvatar) ImageView imgAvatar;
    @BindView(R.id.tvProviderName) TextView tvProviderName;
    @BindView(R.id.ratingBar) RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_details);
        ButterKnife.bind(this);
        initToolbar(R.id.toolbar,getString(R.string.provider_information));

        Intent intent = getIntent();
        Long providerId = intent.getLongExtra("providerId",1);
        String providerName = intent.getStringExtra("providerName");
        String providerAvatar = intent.getStringExtra("providerAvatar");
        Float providerStars = intent.getFloatExtra("providerStars",5f);

        tvProviderName.setText(providerName);
        ratingBar.setRating(providerStars);
        Picasso.get().load(providerAvatar).into(imgAvatar);


        mFragments.add(new ProviderRatingsFragment());
        mFragments.add(new ProviderInfoFragment());

        mDecorView = getWindow().getDecorView();

        mTabLayout = ViewFindUtils.find(mDecorView, R.id.tl_3);
        tl_3();
        mTabLayout.showDot(1);

        mTabLayout.showDot(2);
        MsgView rtv_3_2 = mTabLayout.getMsgView(2);
        if (rtv_3_2 != null) {
            rtv_3_2.setBackgroundColor(Color.parseColor("#6D8FB0"));
        }
    }

    private void tl_3() {
        final ViewPager vp_3 = ViewFindUtils.find(mDecorView, R.id.vp_2);
        vp_3.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        mTabLayout.setTabData(mTitles);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vp_3.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        vp_3.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_3.setCurrentItem(1);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}
