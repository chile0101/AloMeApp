package com.thesis.alome.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.thesis.alome.R;
import com.thesis.alome.activity.MainActivity;
import com.thesis.alome.activity.StepActivity;
import com.thesis.alome.adapter.SnapAdapter;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.config.ApiServices;
import com.thesis.alome.config.PrefUtils;
import com.thesis.alome.model.RespBase;
import com.thesis.alome.model.ServiceType;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.MediaStore.Images.ImageColumns.ORIENTATION;

public class ServicesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fabRequestNow;

    private boolean mHorizontal;
    private SnapAdapter snapAdapter;
    List<ServiceType> serviceTypes;

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
        fabRequestNow.bringToFront();
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                callApi();
            }
        });

        if (savedInstanceState == null) {
            mHorizontal = true;
        } else {
            mHorizontal = savedInstanceState.getBoolean(ORIENTATION);
        }
        if(serviceTypes == null) {
            callApi();
        } else {
            //there is already data? screen must be rotating or tab switching
            for (ServiceType serviceType:serviceTypes){
                snapAdapter.addSnap(serviceType);
                mRecyclerView.setAdapter(snapAdapter);
            }
        }

        fabRequestNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StepActivity.class);
                intent.putExtra("Uniqid","From_Activity_Main");
                startActivity(intent);
            }
        });
    }

    private void callApi(){
        serviceTypes = new ArrayList<>();
        snapAdapter = new SnapAdapter(getActivity());
        mRecyclerView.setAdapter(snapAdapter);

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

        ApiClient.getClient(getActivity()).create(ApiServices.class).getMainData().enqueue(new Callback<RespBase<List<ServiceType>>>() {
            @Override
            public void onResponse(Call<RespBase<List<ServiceType>>> call, Response<RespBase<List<ServiceType>>> response) {
                if(response.body()!=null && response.body().getStatus()){
                    serviceTypes = response.body().getData();
                    for (ServiceType serviceType:serviceTypes){
                        snapAdapter.addSnap(serviceType);
                        mRecyclerView.setAdapter(snapAdapter);
                    }
                }else {
                    Toast.makeText(getActivity(), getString(R.string.somthing_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespBase<List<ServiceType>>> call, Throwable t) {
                Toast.makeText(getActivity(), getString(R.string.please_check_the_internet), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
