package com.thesis.alome.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.thesis.alome.R;
import com.thesis.alome.adapter.RatingsForProviderAdapter;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.config.ApiServices;
import com.thesis.alome.config.PrefUtils;
import com.thesis.alome.model.RatingForProvider;
import com.thesis.alome.model.RespBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProviderRatingsFragment extends Fragment {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.viewEmptyWrapper) RelativeLayout viewEmptyWrapper;
    @BindView(R.id.txtEmpty) TextView txtEmpty;
    List<RatingForProvider> ratingList;
    RatingsForProviderAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider_ratings, container, false);
        ButterKnife.bind(this,view);

        Long providerId = getArguments().getLong("providerId");
        ratingList = new ArrayList<RatingForProvider>();
        ApiServices apiServices = ApiClient.getClient(getActivity()).create(ApiServices.class);
        Call<RespBase<List<RatingForProvider>>> call = apiServices.getRatingsOfProvider(PrefUtils.getId(getActivity()),providerId,PrefUtils.getApiKey(getActivity()));
        call.enqueue(new Callback<RespBase<List<RatingForProvider>>>() {
            @Override
            public void onResponse(Call<RespBase<List<RatingForProvider>>> call, Response<RespBase<List<RatingForProvider>>> response) {
                if(response.body() != null && response.body().getStatus() == true){
                    ratingList = response.body().getData();
                    if(ratingList.isEmpty()){
                        recyclerView.setVisibility(View.GONE);
                        viewEmptyWrapper.setVisibility(View.VISIBLE);
                        txtEmpty.setText(getString(R.string.review_list_empty));
                    }
                    adapter = new RatingsForProviderAdapter(ratingList,getActivity());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    final SkeletonScreen skeletonScreen = Skeleton.bind(recyclerView)
                            .adapter(adapter)
                            .shimmer(true)
                            .angle(20)
                            .frozen(false)
                            .duration(1200)
                            .count(10)
                            .load(R.layout.item_comment_type_skeleton)
                            .show();
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            skeletonScreen.hide();
                        }
                    }, 1000);
                }
            }

            @Override
            public void onFailure(Call<RespBase<List<RatingForProvider>>> call, Throwable t) {
                Log.d("error","error");
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
