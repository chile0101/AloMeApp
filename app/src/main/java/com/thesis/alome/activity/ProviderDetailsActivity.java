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
    private String[] mTitles = {"Thông tin","Đánh giá"};
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

        // Receives data from Provider List Activity
        Intent intent = getIntent();
        Long providerId = intent.getLongExtra("providerId",1);
        String providerName = intent.getStringExtra("providerName");
        String providerAvatar = intent.getStringExtra("providerAvatar");
        String serviceName = intent.getStringExtra("serviceName");
        Float providerStars = intent.getFloatExtra("providerStars",5f);

        tvProviderName.setText(providerName);
        ratingBar.setRating(providerStars);
        Picasso.get().load(providerAvatar).into(imgAvatar);

        // Get instance of Fragments
        ProviderInfoFragment infoFragment = new ProviderInfoFragment();
        ProviderRatingsFragment ratingsFragment = new ProviderRatingsFragment();

        // Pass data to fragments
        Bundle bundle = new Bundle();
        bundle.putLong("providerId", providerId);
        bundle.putString("job",serviceName);

        infoFragment.setArguments(bundle);
        ratingsFragment.setArguments(bundle);

        mFragments.add(infoFragment);
        mFragments.add(ratingsFragment);
        mDecorView = getWindow().getDecorView();

        mTabLayout = ViewFindUtils.find(mDecorView, R.id.tl_3);
        tl_3();
        mTabLayout.showDot(0);
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
