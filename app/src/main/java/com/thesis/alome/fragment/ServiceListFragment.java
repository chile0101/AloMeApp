package com.thesis.alome.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.widget.MsgView;
import com.thesis.alome.R;
import com.thesis.alome.activity.MainActivity;
import com.thesis.alome.utils.ViewFindUtils;

import java.util.ArrayList;
import java.util.List;

public class ServiceListFragment extends Fragment implements OnTabSelectListener {

    private Context mContext ;
    private ArrayList<Fragment> mFragments ;
    private List<String> mTitles ;
    private MyPagerAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setTitleToolBar(getString(R.string.my_activate));
        mContext = getContext();
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<String>();
        mTitles.add(getString(R.string.in_progress));
        mTitles.add(getString(R.string.complete_jobs));
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_service_list,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFragments.add(new InProgressFragment());
        mFragments.add(new CompletedJobsFragment());

        View decorView = getActivity() .getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(view, R.id.vp);
        mAdapter = new MyPagerAdapter(getChildFragmentManager());
        vp.setAdapter(mAdapter);

        SlidingTabLayout tabLayout_2 = ViewFindUtils.find(view, R.id.tl_2);
        tabLayout_2.setViewPager(vp);
        tabLayout_2.setOnTabSelectListener(this);

        vp.setCurrentItem(0);

    }

    @Override
    public void onTabSelect(int position) {
        //Toast.makeText(mContext, "onTabSelect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabReselect(int position) {
        //Toast.makeText(mContext, "onTabReselect&position--->" + position, Toast.LENGTH_SHORT).show();
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
            return mTitles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
