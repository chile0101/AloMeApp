package com.thesis.alome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.thesis.alome.R;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.config.ApiServices;
import com.thesis.alome.config.PrefUtils;
import com.thesis.alome.fragment.ReviewDialog;
import com.thesis.alome.model.JobDetails;
import com.thesis.alome.model.Provider;
import com.thesis.alome.model.RespBase;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailsCompletedActivity extends BaseActivity {

    @BindView(R.id.tvCreateAt) TextView tvCreateAt;
    @BindView(R.id.btnStatus) Button btnStatus;
    @BindView(R.id.tvServiceName) TextView tvServiceName;
    @BindView(R.id.tvHour) TextView tvHour;
    @BindView(R.id.tvDate) TextView tvDate;
    @BindView(R.id.tvAddress) TextView tvAddress;
    @BindView(R.id.tvDescription) TextView tvDescription;
    @BindView(R.id.selected_photos_container) LinearLayout selected_photos_container;
    @BindView(R.id.btnReview) Button btnReview;

    @BindView(R.id.layoutProvider) ConstraintLayout layoutProvider;

    //Provider
    @BindView(R.id.wrapperProvider) LinearLayout wrapperProvider;
    @BindView(R.id.imgAvatar) ImageView imgAvatar;
    @BindView(R.id.tvNameProvider) TextView tvNameProvider;
    @BindView(R.id.txtServiceName) TextView txtServiceName;
    @BindView(R.id.ratingBar) RatingBar ratingBar;
    @BindView(R.id.txtNumOfRatings) TextView txtNumOfRatings;
    @BindView(R.id.txtPrice) TextView txtPrice;
    @BindView(R.id.promiseOfProvider) TextView promiseOfProvider;

    @BindView(R.id.wrapperTextCompleted) LinearLayout wrapperTextCompleted;
    @BindView(R.id.wrapperReview) LinearLayout wrapperReview;

    private Long customerRequestId;
    private Integer jobStatus;
    private Long providerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details_completed);
        ButterKnife.bind(this);

        //Setup ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("serviceName"));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //Toobar đã như ActionBar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if(intent != null){
            customerRequestId = Long.valueOf(getIntent().getStringExtra("customerRequestId"));
            Toast.makeText(this, "customerRequestId=" + customerRequestId.toString(), Toast.LENGTH_SHORT).show();
        }

        ApiServices apiServices = ApiClient.getClient(getApplicationContext()).create(ApiServices.class);
        Call<RespBase<JobDetails>> call = apiServices.getJobDetails(customerRequestId, PrefUtils.getApiKey(this));
        call.enqueue(new Callback<RespBase<JobDetails>>() {
            @Override
            public void onResponse(Call<RespBase<JobDetails>> call, Response<RespBase<JobDetails>> response) {
                if(response.body()!= null && response.body().getStatus()){
                    JobDetails jobDetails = response.body().getData();
                    tvCreateAt.setText(getString(R.string.createdAt) + " " + jobDetails.getCreatedAt());
                    tvHour.setText(jobDetails.getTime());
                    tvDate.setText(jobDetails.getDate());
                    tvAddress.setText(jobDetails.getAddress());
                    tvDescription.setText(jobDetails.getDescription());
                    float width = getResources().getDimension(R.dimen.imageSize120dp);
                    float height = getResources().getDimension(R.dimen.imageSize140dp);
                    LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams((int)width,(int)height));
                    for(String url : jobDetails.getImages()){
                        ImageView img = new ImageView(getApplicationContext());
                        img.setLayoutParams(imgParams);
                        Picasso.get().load(url).into(img);
                        selected_photos_container.addView(img);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespBase<JobDetails>> call, Throwable t) {
                Toast.makeText(JobDetailsCompletedActivity.this, getString(R.string.somthing_wrong), Toast.LENGTH_SHORT).show();
            }
        });


        Call<RespBase<Provider>> callProvider = apiServices.getProviderAcceptedJob(customerRequestId,PrefUtils.getApiKey(this));
        callProvider.enqueue(new Callback<RespBase<Provider>>() {
            @Override
            public void onResponse(Call<RespBase<Provider>> call, Response<RespBase<Provider>> resp) {
                if(resp.body()!= null && resp.body().getStatus()){
                    Provider provider = resp.body().getData();
                    if(!provider.getRated()){
                        wrapperTextCompleted.setVisibility(View.GONE);
                        wrapperReview.setVisibility(View.VISIBLE);
                    }
                    Picasso.get().load(provider.getAvatar()).into(imgAvatar);
                    tvNameProvider.setText(provider.getName());
                    ratingBar.setRating(provider.getNumOfStars());
                    txtServiceName.setText(provider.getServiceName());
                    txtNumOfRatings.setText("( " + provider.getNumOfRatings() + " đánh giá " + ")");
                    txtPrice.setText(Math.round(provider.getPrice().floatValue()) + getString(R.string.hour));
                    promiseOfProvider.setText(getString(R.string.estimated_time) + provider.getPromiseOfProvider() + getString(R.string.hour));

                    wrapperProvider.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), ProviderDetailsActivity.class);
                            intent.putExtra("providerId",provider.getProviderId());
                            //providerId = provider.getProviderId().longValue();
                            intent.putExtra("providerName",provider.getName());
                            intent.putExtra("providerAvatar",provider.getAvatar());
                            intent.putExtra("providerStars",provider.getNumOfStars());
                            intent.putExtra("serviceName",provider.getServiceName());
                            startActivity(intent);
                        }
                    });

                    btnReview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ReviewDialog.showDialog(JobDetailsCompletedActivity.this,provider.getProviderId().longValue(),customerRequestId);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<RespBase<Provider>> call, Throwable t) {
                Toast.makeText(JobDetailsCompletedActivity.this, getString(R.string.somthing_wrong), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
