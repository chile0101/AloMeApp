package com.thesis.alome.activity;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.thesis.alome.R;
import com.thesis.alome.adapter.ProviderListRcvAdapter;
import com.thesis.alome.model.Provider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProviderListActivity extends Activity {

    RecyclerView recyclerView;
    List<Provider> providerList;
    ProviderListRcvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_list);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        providerList = new ArrayList<Provider>();
        providerList.add(new Provider("Chi Le","https://scontent.fsgn6-2.fna.fbcdn.net/v/t1.0-1/p160x160/49547387_2216482051959552_5817986978611724288_o.jpg?_nc_cat=108&_nc_eui2=AeE97DiKmRUVdKqWuCRnoWcrMBzZiN6xIxfv0UIdY0SPDCV9_3hCPGd4Tqasx2g6I-0FB4uemE6ud5g94eIbuhDotjy25MQVuo54nST8W6lfsA&_nc_oc=AQkbYOh_lId2Xx_FV8Bc7N_N_WUz8MiZATHSr2w3W0pDBKLxV8_ACVehdiNuam9VzNg&_nc_ht=scontent.fsgn6-2.fna&oh=4369ea226b0988374e0a45b3befeb9e2&oe=5E51477F",
                4,5));
        providerList.add(new Provider("Chi Le","https://scontent.fsgn6-2.fna.fbcdn.net/v/t1.0-1/p160x160/49547387_2216482051959552_5817986978611724288_o.jpg?_nc_cat=108&_nc_eui2=AeE97DiKmRUVdKqWuCRnoWcrMBzZiN6xIxfv0UIdY0SPDCV9_3hCPGd4Tqasx2g6I-0FB4uemE6ud5g94eIbuhDotjy25MQVuo54nST8W6lfsA&_nc_oc=AQkbYOh_lId2Xx_FV8Bc7N_N_WUz8MiZATHSr2w3W0pDBKLxV8_ACVehdiNuam9VzNg&_nc_ht=scontent.fsgn6-2.fna&oh=4369ea226b0988374e0a45b3befeb9e2&oe=5E51477F",
                4,5));
        providerList.add(new Provider("Chi Le","https://scontent.fsgn6-2.fna.fbcdn.net/v/t1.0-1/p160x160/49547387_2216482051959552_5817986978611724288_o.jpg?_nc_cat=108&_nc_eui2=AeE97DiKmRUVdKqWuCRnoWcrMBzZiN6xIxfv0UIdY0SPDCV9_3hCPGd4Tqasx2g6I-0FB4uemE6ud5g94eIbuhDotjy25MQVuo54nST8W6lfsA&_nc_oc=AQkbYOh_lId2Xx_FV8Bc7N_N_WUz8MiZATHSr2w3W0pDBKLxV8_ACVehdiNuam9VzNg&_nc_ht=scontent.fsgn6-2.fna&oh=4369ea226b0988374e0a45b3befeb9e2&oe=5E51477F",
                4,5));
        adapter = new ProviderListRcvAdapter(providerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


}
