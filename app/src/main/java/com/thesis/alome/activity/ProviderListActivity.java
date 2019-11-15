package com.thesis.alome.activity;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.thesis.alome.R;
import com.thesis.alome.adapter.ProviderListRcvAdapter;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.config.ApiServices;
import com.thesis.alome.config.PrefUtils;
import com.thesis.alome.model.Provider;
import com.thesis.alome.model.RespBase;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProviderListActivity extends BaseActivity{

    RecyclerView recyclerView;
    List<Provider> providerList;
    ProviderListRcvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_list);
        initToolbar(R.id.toolbar,getString(R.string.title_activity_provider_list));
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        ApiServices apiServices = ApiClient.getClient(getApplicationContext()).create(ApiServices.class);
        Call<RespBase<List<Provider>>> call = apiServices.getProviderNearMe(1,
                                                                            PrefUtils.getApiKey(getApplicationContext()));
        call.enqueue(new Callback<RespBase<List<Provider>>>() {
            @Override
            public void onResponse(Call<RespBase<List<Provider>>> call, Response<RespBase<List<Provider>>> response) {
                providerList = response.body().getData();
                adapter = new ProviderListRcvAdapter(getApplicationContext(),providerList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<RespBase<List<Provider>>> call, Throwable t) {
                Toast.makeText(ProviderListActivity.this, getString(R.string.please_check_the_internet), Toast.LENGTH_SHORT).show();
            }
        });



    }
}
