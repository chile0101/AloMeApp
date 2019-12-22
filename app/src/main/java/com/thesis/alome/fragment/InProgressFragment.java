package com.thesis.alome.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.thesis.alome.R;
import com.thesis.alome.activity.FilterJobsActivity;
import com.thesis.alome.adapter.JobListRcvAdapter;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.config.ApiServices;
import com.thesis.alome.config.PrefUtils;
import com.thesis.alome.model.Job;
import com.thesis.alome.model.RespBase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InProgressFragment extends Fragment {
    private static final String TAG = "inProgress";
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.viewEmptyWrapper) RelativeLayout viewEmptyWrapper;
    @BindView(R.id.txtEmpty) TextView txtEmpty;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.filterJobs) TextView filterJobs;
    @BindView(R.id.filterWrapper) LinearLayout filterWrapper;
    private Integer typeJobValue ;
    private String typeJobName;

    List<Job> jobList;
    JobListRcvAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_progress, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // spinner filter jobs
        filterJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FilterJobsActivity.class);
                startActivityForResult(intent,1);
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(false);
                callApi();
            }
        });
        callApi();
    }

    @Override
    public void onResume() {
        super.onResume();
        callApi();
       // Toast.makeText(getActivity(), "" + typeJobValue, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                typeJobValue = data.getIntExtra("typeJobValue",0);
                if(typeJobValue == 0){
                    typeJobValue = null;
                }
                typeJobName = data.getStringExtra("typeJobName");
                filterJobs.setText(typeJobName);
            }
        }
    }

    private void callApi() {
        ApiServices apiServices = ApiClient.getClient(getActivity()).create(ApiServices.class);
        Call<RespBase<List<Job>>> call = apiServices.getJobsInProgress(PrefUtils.getId(getActivity()),
                                                                PrefUtils.getApiKey(getActivity()),
                                                                typeJobValue);
        call.enqueue(new Callback<RespBase<List<Job>>>() {
            @Override
            public void onResponse(Call<RespBase<List<Job>>> call, Response<RespBase<List<Job>>> response) {
                if(response.body()!=null && response.body().getStatus()){
                    jobList = response.body().getData();
                    if(jobList.isEmpty()){
                        recyclerView.setVisibility(View.GONE);
                        viewEmptyWrapper.setVisibility(View.VISIBLE);
                    }else{
                        recyclerView.setVisibility(View.VISIBLE);
                        viewEmptyWrapper.setVisibility(View.GONE);

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
                        }, 500);
                    }
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
