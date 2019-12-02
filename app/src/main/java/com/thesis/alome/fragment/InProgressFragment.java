package com.thesis.alome.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.thesis.alome.R;
import com.thesis.alome.adapter.JobListRcvAdapter;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.config.ApiServices;
import com.thesis.alome.config.PrefUtils;
import com.thesis.alome.model.Job;
import com.thesis.alome.model.RespBase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InProgressFragment extends Fragment {
    private static final String TAG = "inProgress";
    RecyclerView recyclerView;
    List<Job> jobList;
    JobListRcvAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_in_progress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        jobList = new ArrayList<Job>();

        ApiServices apiServices = ApiClient.getClient(getActivity()).create(ApiServices.class);
        Call<RespBase<List<Job>>> call = apiServices.getJobsInProgress(PrefUtils.getId(getActivity()),PrefUtils.getApiKey(getActivity()),0);
        call.enqueue(new Callback<RespBase<List<Job>>>() {
            @Override
            public void onResponse(Call<RespBase<List<Job>>> call, Response<RespBase<List<Job>>> response) {
                if(response.body()!=null && response.body().getStatus()){
                    jobList = response.body().getData();
                    adapter = new JobListRcvAdapter(jobList,getActivity(),TAG);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);

                    final SkeletonScreen skeletonScreen = Skeleton.bind(recyclerView)
                            .adapter(adapter)
                            .shimmer(true)
                            .angle(20)
                            .frozen(false)
                            .duration(1200)
                            .count(10)
                            .load(R.layout.item_job_skeleton)
                            .show();

                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            skeletonScreen.hide();
                        }
                    }, 1000);
                }else {
                    Toast.makeText(getActivity(), getString(R.string.somthing_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespBase<List<Job>>> call, Throwable t) {
                Toast.makeText(getActivity(), getString(R.string.please_check_the_internet), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
