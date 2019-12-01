package com.thesis.alome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thesis.alome.R;
import com.thesis.alome.fragment.WarningDialog;
import com.thesis.alome.model.ProviderDetails;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JobDetailsActivity extends AppCompatActivity {

    @BindView(R.id.tvCreateAt) TextView tvCreateAt;
    @BindView(R.id.btnStatus) Button btnStatus;
    @BindView(R.id.tvServiceName) TextView tvServiceName;
    @BindView(R.id.tvHour) TextView tvHour;
    @BindView(R.id.tvEditHour) TextView tvEditHour;
    @BindView(R.id.tvDate) TextView tvDate;
    @BindView(R.id.tvEditDate) TextView tvEditDate;
    @BindView(R.id.tvAddress) TextView tvAddress;
    @BindView(R.id.tvDescription) TextView tvDescription;
    @BindView(R.id.selected_photos_container) LinearLayout selected_photos_container;
    @BindView(R.id.tvPrice) TextView tvPrice;
    @BindView(R.id.btnAccess) Button btnAccess;
    @BindView(R.id.btnRefuse) Button btnRefuse;
    @BindView(R.id.layoutProvider) ConstraintLayout layoutProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        //Setup ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("serviceName"));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //Toobar đã như ActionBar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        ButterKnife.bind(this);

//        layoutProvider.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(JobDetailsActivity.this, ProviderDetails.class);
//                intent.putExtra("providerId",provider.getProviderId());
//                intent.putExtra("providerName",provider.getName());
//                intent.putExtra("providerAvatar",provider.getAvatar());
//                intent.putExtra("providerStars",provider.getNumOfStars());
//                intent.putExtra("serviceName",provider.getServiceName());
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.job_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.deleteJobItem:
                WarningDialog.showDialog(JobDetailsActivity.this,"Bạn chắc chắn muốn xóa yêu cầu này ?");
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
