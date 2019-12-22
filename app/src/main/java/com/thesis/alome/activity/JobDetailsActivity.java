package com.thesis.alome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
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
import com.thesis.alome.fragment.WarningDialog;
import com.thesis.alome.model.JobDetails;
import com.thesis.alome.model.Provider;
import com.thesis.alome.model.RespBase;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailsActivity extends BaseActivity  {

    @BindView(R.id.tvCreateAt) TextView tvCreateAt;
    @BindView(R.id.btnStatus) Button btnStatus;
    @BindView(R.id.tvServiceName) TextView tvServiceName;
    @BindView(R.id.tvHour) TextView tvHour;
    @BindView(R.id.tvEditHour) TextView tvEditHour;
    @BindView(R.id.tvDate) TextView tvDate;
    @BindView(R.id.tvEditDate) TextView tvEditDate;
    @BindView(R.id.tvPhone) TextView tvPhone;
    @BindView(R.id.tvAddress) TextView tvAddress;
    @BindView(R.id.tvDescription) TextView tvDescription;
    @BindView(R.id.selected_photos_container) LinearLayout selected_photos_container;
    @BindView(R.id.btnAccept) Button btnAccept;
    @BindView(R.id.btnRefuse) Button btnRefuse;
    @BindView(R.id.layoutProvider) ConstraintLayout layoutProvider;
    @BindView(R.id.btnCompleted ) Button btnCompleted;
    @BindView(R.id.btnNotDone) Button btnNotDone;

    //Provider
    @BindView(R.id.imgAvatar) ImageView imgAvatar;
    @BindView(R.id.tvNameProvider) TextView tvNameProvider;
    @BindView(R.id.txtServiceName) TextView txtServiceName;
    @BindView(R.id.ratingBar) RatingBar ratingBar;
    @BindView(R.id.txtNumOfRatings) TextView txtNumOfRatings;
    @BindView(R.id.tvPrice) TextView tvPrice;
    @BindView(R.id.promiseOfProvider) TextView promiseOfProvider;

    //Wrapper
    @BindView(R.id.wrapper100) LinearLayout wrapper100;
    @BindView(R.id.wrapperProvider) LinearLayout wrapperProvider;
    @BindView(R.id.wrapperPrice) LinearLayout wrapperPrice;
    @BindView(R.id.wrapperBtn) LinearLayout wrapperBtn;
    @BindView(R.id.wrapperSuccess) LinearLayout wrapperSuccess;
    @BindView(R.id.wrapperCompletedConfirm) LinearLayout wrapperCompletedConfirm;
//    private Integer providerId;
    private Long customerRequestId;
    private Integer jobStatus;

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

        Intent intent = getIntent();
        if(intent != null){
//            providerId = Integer.valueOf(getIntent().getStringExtra("providerId"));
            customerRequestId = Long.valueOf(getIntent().getStringExtra("customerRequestId"));
            jobStatus = Integer.valueOf(getIntent().getStringExtra("status")) ;
            //Toast.makeText(this, "customerRequestId=" + customerRequestId.toString(), Toast.LENGTH_SHORT).show();
        }

        ApiServices apiServices = ApiClient.getClient(getApplicationContext()).create(ApiServices.class);
        Call<RespBase<JobDetails>> call = apiServices.getJobDetails(customerRequestId, PrefUtils.getApiKey(this));
        call.enqueue(new Callback<RespBase<JobDetails>>() {
            @Override
            public void onResponse(Call<RespBase<JobDetails>> call, Response<RespBase<JobDetails>> response) {
                if(response.body()!= null && response.body().getStatus()){
                    JobDetails jobDetails = response.body().getData();
                    tvCreateAt.setText(getString(R.string.createdAt) + " " + jobDetails.getCreatedAt());
                    tvServiceName.setText(jobDetails.getServiceName());
                    tvHour.setText(jobDetails.getTime());
                    tvDate.setText(jobDetails.getDate());
                    tvPhone.setText(jobDetails.getPhone());
                    tvAddress.setText(jobDetails.getAddress());
                    tvDescription.setText(jobDetails.getDescription());
                    jobStatus = jobDetails.getStatus();
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
                Toast.makeText(JobDetailsActivity.this, getString(R.string.somthing_wrong), Toast.LENGTH_SHORT).show();
            }
        });

        switch (jobStatus){
            case 100:
                btnStatus.setText(getString(R.string.sent_request));
                wrapper100.setVisibility(View.VISIBLE);
                break;
            case 200 :
                btnStatus.setText(getString(R.string.wait_for_confirmation));
                wrapperProvider.setVisibility(View.VISIBLE);
                wrapperPrice.setVisibility(View.VISIBLE);
                wrapperBtn.setVisibility(View.VISIBLE);

                Call<RespBase<Provider>> callProvider = apiServices.getProviderAcceptedJob(customerRequestId,PrefUtils.getApiKey(this));
                callProvider.enqueue(new Callback<RespBase<Provider>>() {
                    @Override
                    public void onResponse(Call<RespBase<Provider>> call, Response<RespBase<Provider>> resp) {
                        if(resp.body()!= null && resp.body().getStatus()){
                            Provider provider = resp.body().getData();
                            Picasso.get().load(provider.getAvatar()).into(imgAvatar);
                            tvNameProvider.setText(provider.getName());
                            ratingBar.setRating(provider.getNumOfStars());
                            txtServiceName.setText(provider.getServiceName());
                            txtNumOfRatings.setText("( " + provider.getNumOfRatings() + " đánh giá " + ")");

                            tvPrice.setText(separeteByDot(provider.getPrice()) + " " + getString(R.string.vnd) + "/" + getString(R.string.hour));
                            promiseOfProvider.setText(getString(R.string.estimated_time)+ " " + provider.getPromiseOfProvider() + " " + getString(R.string.hour));

                            wrapperProvider.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), ProviderDetailsActivity.class);
                                    intent.putExtra("providerId",provider.getProviderId());
                                    intent.putExtra("providerName",provider.getName());
                                    intent.putExtra("providerAvatar",provider.getAvatar());
                                    intent.putExtra("providerStars",provider.getNumOfStars());
                                    intent.putExtra("serviceName",provider.getServiceName());
                                    startActivity(intent);
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<RespBase<Provider>> call, Throwable t) {
                        Toast.makeText(JobDetailsActivity.this, getString(R.string.somthing_wrong), Toast.LENGTH_SHORT).show();
                    }
                });


                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Call<RespBase<Provider>> acceptProvider = apiServices.acceptProvider(PrefUtils.getId(getApplicationContext()),
                                                                                                        customerRequestId,300,
                                                                                                PrefUtils.getApiKey(getApplicationContext()));
                        acceptProvider.enqueue(new Callback<RespBase<Provider>>() {
                            @Override
                            public void onResponse(Call<RespBase<Provider>> call, Response<RespBase<Provider>> response) {
                                if(response.body().getStatus()){
                                    Intent intent = new Intent(JobDetailsActivity.this,JobDetailsActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("customerRequestId",customerRequestId.toString());
                                    intent.putExtra("status",300+"");
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(Call<RespBase<Provider>> call, Throwable t) {
                                Toast.makeText(JobDetailsActivity.this, getString(R.string.somthing_wrong), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                btnRefuse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Call<RespBase<Provider>> refuseProvider = apiServices.acceptProvider(PrefUtils.getId(getApplicationContext()),
                                                                                        customerRequestId,100,
                                                                                        PrefUtils.getApiKey(getApplicationContext()));
                        refuseProvider.enqueue(new Callback<RespBase<Provider>>() {
                            @Override
                            public void onResponse(Call<RespBase<Provider>> call, Response<RespBase<Provider>> response) {
                                if(response.body().getStatus()){
                                    Intent intent = new Intent(JobDetailsActivity.this,JobDetailsActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("customerRequestId",customerRequestId.toString());
                                    intent.putExtra("status",100+"");
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(Call<RespBase<Provider>> call, Throwable t) {
                                Toast.makeText(JobDetailsActivity.this, getString(R.string.somthing_wrong), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
            case 300:
                Call<RespBase<Provider>> callProvider300 = apiServices.getProviderAcceptedJob(customerRequestId,PrefUtils.getApiKey(this));
                btnStatus.setText(getString(R.string.contact_success));
                wrapperSuccess.setVisibility(View.VISIBLE);
                wrapperProvider.setVisibility(View.VISIBLE);
                wrapperPrice.setVisibility(View.VISIBLE);
                callProvider300.enqueue(new Callback<RespBase<Provider>>() {
                    @Override
                    public void onResponse(Call<RespBase<Provider>> call, Response<RespBase<Provider>> resp) {
                        if(resp.body()!= null && resp.body().getStatus()){
                            Provider provider = resp.body().getData();
                            Picasso.get().load(provider.getAvatar()).into(imgAvatar);
                            tvNameProvider.setText(provider.getName());
                            ratingBar.setRating(provider.getNumOfStars());
                            txtServiceName.setText(provider.getServiceName());
                            txtNumOfRatings.setText("( " + provider.getNumOfRatings() + " đánh giá " + ")");

                            tvPrice.setText(separeteByDot(provider.getPrice()) + " " + getString(R.string.vnd) + "/" + getString(R.string.hour));
                            promiseOfProvider.setText(getString(R.string.estimated_time)+ " " + provider.getPromiseOfProvider() + " " + getString(R.string.hour));

                            wrapperProvider.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), ProviderDetailsActivity.class);
                                    intent.putExtra("providerId",provider.getProviderId());
                                    intent.putExtra("providerName",provider.getName());
                                    intent.putExtra("providerAvatar",provider.getAvatar());
                                    intent.putExtra("providerStars",provider.getNumOfStars());
                                    intent.putExtra("serviceName",provider.getServiceName());
                                    startActivity(intent);
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<RespBase<Provider>> call, Throwable t) {
                        Toast.makeText(JobDetailsActivity.this, getString(R.string.somthing_wrong), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 401:
                btnStatus.setText(getString(R.string.confirmation_completed));
                wrapperCompletedConfirm.setVisibility(View.VISIBLE);


                btnCompleted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Call<RespBase<Provider>> confirmCompleted = apiServices.acceptProvider(PrefUtils.getId(getApplicationContext()),
                                customerRequestId,400,
                                PrefUtils.getApiKey(getApplicationContext()));
                        confirmCompleted.enqueue(new Callback<RespBase<Provider>>() {
                            @Override
                            public void onResponse(Call<RespBase<Provider>> call, Response<RespBase<Provider>> response) {
                                Intent intent = new Intent(JobDetailsActivity.this,JobDetailsCompletedActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("customerRequestId",customerRequestId.toString());
                                intent.putExtra("status",400+"");
                                intent.putExtra("Uniqid","From_Activity_Job_Detail");
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<RespBase<Provider>> call, Throwable t) {

                            }
                        });
                    }
                });


                break;
            default:
                btnStatus.setVisibility(View.GONE);
        }
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
                WarningDialog.showDialog(JobDetailsActivity.this,getString(R.string.are_you_sure_delete_job),customerRequestId);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private StringBuilder separeteByDot(Double priceD){
        Long priceL = Math.round(priceD);
        String priceStr = priceL.toString();
        StringBuilder result = new StringBuilder();
        int len = priceStr.length();
        for(int i = len-1 ; i >= 0 ;i--) {

            if(i!= len-1 && (len-1-i)%3 == 0) {
                result.append('.');
            }
            result.append(priceStr.charAt(i));

        }
        return result.reverse();
    }

}
