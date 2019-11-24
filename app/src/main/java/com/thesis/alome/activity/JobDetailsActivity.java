package com.thesis.alome.activity;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.thesis.alome.R;

public class JobDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        Integer providerId = Integer.valueOf(getIntent().getStringExtra("providerId"));
        Integer customerRequestId = Integer.valueOf(getIntent().getStringExtra("customerRequestId"));
        Toast.makeText(this,"providerId= " + providerId.toString() + " customerRequestId=" + customerRequestId.toString(), Toast.LENGTH_SHORT).show();
    }

}
