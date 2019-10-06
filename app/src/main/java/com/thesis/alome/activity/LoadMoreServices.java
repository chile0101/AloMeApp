package com.thesis.alome.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.thesis.alome.R;
import com.thesis.alome.adapter.LoadMoreRcvAdapter;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.config.ApiServices;
import com.thesis.alome.dao.RespBase;
import com.thesis.alome.dao.Service;
import com.thesis.alome.dao.TypeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadMoreServices extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Service> serviceList;
    LoadMoreRcvAdapter adapter;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more_services);

        recyclerView = (RecyclerView) findViewById(R.id.rcvLoadMore);
        ApiServices apiServices = ApiClient.getClient(getApplicationContext()).create(ApiServices.class);
        Call<RespBase> call = apiServices.getServicesByTypeId(getIntent().getIntExtra("typeId",1));
        call.enqueue(new Callback<RespBase>() {
            @Override
            public void onResponse(Call<RespBase> call, Response<RespBase> response) {
                serviceList = (List<Service>) response.body().getData();
                adapter = new LoadMoreRcvAdapter(serviceList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<RespBase> call, Throwable t) {
                Toast.makeText(LoadMoreServices.this, "Please check internet", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
