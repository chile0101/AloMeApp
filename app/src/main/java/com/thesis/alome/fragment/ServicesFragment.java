package com.thesis.alome.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.thesis.alome.R;
import com.thesis.alome.activity.MainActivity;
import com.thesis.alome.activity.StepActivity;
import com.thesis.alome.adapter.MainRecycleAdapter;
import com.thesis.alome.adapter.SnapAdapter;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.config.ApiServices;
import com.thesis.alome.config.PrefUtils;
import com.thesis.alome.model.RespBase;
import com.thesis.alome.model.ServiceType;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.MediaStore.Images.ImageColumns.ORIENTATION;

public class ServicesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private boolean mHorizontal;
    private SnapAdapter snapAdapter;
    List<ServiceType> serviceTypes;
    private FloatingActionButton fabRequestNow;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).setTitleToolBar(getString(R.string.welcome)+ " " + PrefUtils.getShortName(getActivity()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_services,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        fabRequestNow = view.findViewById(R.id.fabRequestNow);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);


        if (savedInstanceState == null) {
            mHorizontal = true;
        } else {
            mHorizontal = savedInstanceState.getBoolean(ORIENTATION);
        }
        if(serviceTypes == null) {
            setupAdapter();
            callApi();


        } else {
            //there is already data? screen must be rotating or tab switching
            for (ServiceType serviceType:serviceTypes){
                snapAdapter.addSnap(serviceType);
                mRecyclerView.setAdapter(snapAdapter);
            }
        }

        callApi();

        //skeleton
        final SkeletonScreen skeletonScreen = Skeleton.bind(mRecyclerView)
                .adapter(snapAdapter)
                .shimmer(true)
                .angle(20)
                .duration(1000)
                .color(R.color.shimmer_color)
                .load(R.layout.item_service_type_skeleton)
                .show();

        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                skeletonScreen.hide();
            }
        }, 3000);

        fabRequestNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StepActivity.class);
                intent.putExtra("Uniqid","From_Activity_Main");
                startActivity(intent);
            }
        });
    }

    private void setupAdapter() {
        serviceTypes =new ArrayList<>();
        snapAdapter = new SnapAdapter(getActivity());
        mRecyclerView.setAdapter(snapAdapter);
    }



    private void callApi(){
        ApiClient.getClient(getActivity()).create(ApiServices.class).getMainData().enqueue(new Callback<RespBase<List<ServiceType>>>() {
            @Override
            public void onResponse(Call<RespBase<List<ServiceType>>> call, Response<RespBase<List<ServiceType>>> response) {
                if(response.body()!=null && response.body().getStatus()){
                    serviceTypes = response.body().getData();
                    for (ServiceType serviceType:serviceTypes){
                        snapAdapter.addSnap(serviceType);
                        mRecyclerView.setAdapter(snapAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespBase<List<ServiceType>>> call, Throwable t) {

            }
        });
    }

}
