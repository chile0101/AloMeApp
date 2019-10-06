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
import android.widget.Toast;

import com.thesis.alome.R;
import com.thesis.alome.adapter.MainRecycleAdapter;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.config.ApiServices;
import com.thesis.alome.dao.RespBase;
import com.thesis.alome.dao.TypeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesFragment extends Fragment {

    RecyclerView recyclerView;
    MainRecycleAdapter mainRecycleAdapter;
    List<TypeService> typeServiceList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_services,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        
        ApiServices apiServices = ApiClient.getClient(getContext()).create(ApiServices.class);
        Call<RespBase> call = apiServices.getMainData();
        call.enqueue(new Callback<RespBase>() {
            @Override
            public void onResponse(Call<RespBase> call, Response<RespBase> response) {
                typeServiceList = (List<TypeService>) response.body().getData();
                mainRecycleAdapter = new MainRecycleAdapter(typeServiceList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mainRecycleAdapter);
            }

            @Override
            public void onFailure(Call<RespBase> call, Throwable t) {
                Toast.makeText(getActivity(), "Please check internet", Toast.LENGTH_SHORT).show();
            }
        });






    }
}
