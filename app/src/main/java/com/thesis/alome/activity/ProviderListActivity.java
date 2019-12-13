package com.thesis.alome.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.ethanhua.skeleton.ViewReplacer;
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

    LinearLayout wrapper;
    RecyclerView recyclerView;
    List<Provider> providerList;
    ProviderListRcvAdapter adapter;
    private ViewReplacer mViewReplacer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_list);
        initToolbar(R.id.toolbar,getString(R.string.title_activity_provider_list));
        wrapper = (LinearLayout) findViewById(R.id.wrapper);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mViewReplacer = new ViewReplacer(recyclerView);

        wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewReplacer.restore();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
        ApiServices apiServices = ApiClient.getClient(getApplicationContext()).create(ApiServices.class);
        Call<RespBase<List<Provider>>> call = apiServices.getProviderNearMe(1,
                                                                            PrefUtils.getApiKey(getApplicationContext()));
        call.enqueue(new Callback<RespBase<List<Provider>>>() {
            @Override
            public void onResponse(Call<RespBase<List<Provider>>> call, Response<RespBase<List<Provider>>> response) {
                if(response.body().getStatus() == true){
                    providerList = response.body().getData();
                    adapter = new ProviderListRcvAdapter(getApplicationContext(),providerList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    final SkeletonScreen skeletonScreen = Skeleton.bind(recyclerView)
                            .adapter(adapter)
                            .shimmer(true)
                            .angle(20)
                            .frozen(false)
                            .duration(1200)
                            .count(10)
                            .load(R.layout.item_provider_skeleton)
                            .show(); //default count is 10

                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            skeletonScreen.hide();
                        }
                    }, 1000);
                }
            }
            @Override
            public void onFailure(Call<RespBase<List<Provider>>> call, Throwable t) {
                mViewReplacer.replace(R.layout.layout_no_internet_connection_include_icon);
            }
        });
    }
}
