package com.thesis.alome.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thesis.alome.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JobDetailsCompletedActivity extends BaseActivity {

    @BindView(R.id.tvCreateAt) TextView tvCreateAt;
    @BindView(R.id.btnStatus) Button btnStatus;
    @BindView(R.id.tvServiceName) TextView tvServiceName;
    @BindView(R.id.tvHour) TextView tvHour;
    @BindView(R.id.tvDate) TextView tvDate;
    @BindView(R.id.tvAddress) TextView tvAddress;
    @BindView(R.id.tvDescription) TextView tvDescription;
    @BindView(R.id.selected_photos_container) LinearLayout selected_photos_container;
    @BindView(R.id.tvPrice) TextView tvPrice;

    @BindView(R.id.layoutProvider) ConstraintLayout layoutProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details_completed);
        ButterKnife.bind(this);

    }
}
