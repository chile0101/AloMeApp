package com.thesis.alome.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.squareup.picasso.Picasso;
import com.thesis.alome.R;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.config.ApiServices;
import com.thesis.alome.model.RespBase;
import com.thesis.alome.model.Service;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServiceDetailActivity extends BaseActivity
                implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout sliderLayout;
    private TextView tvTitle,tvFullPrice,tvSalePrice;
    private HashMap<String,String> url_maps;
    private View cardDetail,cardTerm;
    private TextView tvTitleDetail,tvTitleTerm,tvContentTerm,tvContentDetail;
    private Button btnSelectService;
    private Button btnAloNow;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        initToolbar(R.id.toolbar,getString(R.string.service_detail));
        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvFullPrice = (TextView) findViewById(R.id.tvFullPrice);
        tvSalePrice = (TextView) findViewById(R.id.tvSalePrice);
        cardDetail = (View) findViewById(R.id.cardDetail);
        cardTerm = (View) findViewById(R.id.cardTerm);
        tvContentDetail = (TextView) cardDetail.findViewById(R.id.tvContent);
        tvTitleDetail = (TextView) cardDetail.findViewById(R.id.tvSubTitle);
        tvContentTerm = (TextView) cardTerm.findViewById(R.id.tvContent);
        tvTitleTerm = (TextView) cardTerm.findViewById(R.id.tvSubTitle);
        btnSelectService = (Button) findViewById(R.id.btnSelectService);
        btnAloNow = (Button) findViewById(R.id.btnAloNow);
        tvTitleTerm.setText(getString(R.string.term_of_service));
        tvTitleDetail.setText(getString(R.string.service_details));


        ApiServices apiServices = ApiClient.getClient(getApplicationContext()).create(ApiServices.class);
        Call<RespBase<Service>> call = apiServices.getServiceById(getIntent().getLongExtra("serviceId",1));
        call.enqueue(new Callback<RespBase<Service>>() {
            @Override
            public void onResponse(Call<RespBase<Service>> call, Response<RespBase<Service>> response) {
                url_maps = new HashMap<String, String>();
                url_maps.put("img1", response.body().getData().getImgUrls().getImgUrl1());
                url_maps.put("img2", response.body().getData().getImgUrls().getImgUrl2());
                url_maps.put("img3", response.body().getData().getImgUrls().getImgUrl3());
                for(String name: url_maps.keySet()) {
                    DefaultSliderView textSliderView = new DefaultSliderView(getApplicationContext());
                    textSliderView
                            .image(url_maps.get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setPicasso(Picasso.get());
                    sliderLayout.addSlider(textSliderView);
                }
                tvTitle.setText(response.body().getData().getServiceName());
                tvFullPrice.setText("$" + response.body().getData().getFullPrice());
                tvFullPrice.setPaintFlags(tvFullPrice.getPaintFlags()
                        | Paint.STRIKE_THRU_TEXT_FLAG);
                tvSalePrice.setText("$" + response.body().getData().getSalePrice());
                tvContentDetail.setText(response.body().getData().getDescription());
                tvContentTerm.setText(response.body().getData().getTerm());

            }

            @Override
            public void onFailure(Call<RespBase<Service>> call, Throwable t) {
                Toast.makeText(ServiceDetailActivity.this, getString(R.string.please_check_the_internet), Toast.LENGTH_SHORT).show();
            }
        });

        ExpansionLayout expansionLayout = findViewById(R.id.expansionLayout);
        expansionLayout.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {
            }
        });


        btnSelectService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceDetailActivity.this,StepActivity.class);
                intent.putExtra("serviceId",getIntent().getLongExtra("serviceId",1));
                intent.putExtra("Uniqid","From_Activity_ServiceDetail");
                startActivity(intent);
            }
        });

        btnAloNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceDetailActivity.this,ProviderListActivity.class);
                intent.putExtra("serviceId",getIntent().getLongExtra("serviceId",1));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        sliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        //Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}
}
