package com.thesis.alome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.thesis.alome.R;
import com.thesis.alome.adapter.TypeJobRcvAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterJobsActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private List<String> typeJobList;
    private TypeJobRcvAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_jobs);
        initToolbar(R.id.toolbar,getString(R.string.select_type_of_job));

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        typeJobList = new ArrayList<String>();
        typeJobList.add(getString(R.string.all));
        typeJobList.add(getString(R.string.sent_request));
        typeJobList.add(getString(R.string.wait_for_confirmation));
        typeJobList.add(getString(R.string.contact_success));
        typeJobList.add(getString(R.string.confirmation_completed));

        adapter = new TypeJobRcvAdapter(this,typeJobList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
