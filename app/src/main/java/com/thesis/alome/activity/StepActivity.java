package com.thesis.alome.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;
import com.thesis.alome.R;
import com.thesis.alome.adapter.TabAdapter;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.config.ApiServices;
import com.thesis.alome.config.FragmentLifecycle;
import com.thesis.alome.config.PrefUtils;
import com.thesis.alome.fragment.StepOneFragment;
import com.thesis.alome.fragment.StepTwoFragment;
import com.thesis.alome.fragment.SuccessDialog;
import com.thesis.alome.model.RespBase;
import com.thesis.alome.viewmodel.StepViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StepActivity extends BaseActivity {

    @BindView(R.id.view_pager_step) ViewPager viewPager;
    @BindView(R.id.tabStepLayout) TabLayout tabLayout;
    @BindView(R.id.btnNext) Button btnNext;
    @BindView(R.id.loadingContainer) FrameLayout loadingContainer;
    TabAdapter tabAdapter;
    StepViewModel stepViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        ButterKnife.bind(this);
        initToolbar(R.id.toolbar,getString(R.string.title_booking_service));
        stepViewModel = ViewModelProviders.of(this).get(StepViewModel.class);
        stepViewModel.setPhone(PrefUtils.getPhoneNumber(getApplicationContext()));
        tabAdapter = new TabAdapter(getSupportFragmentManager());

        tabAdapter.addFragment(new StepOneFragment(), getString(R.string.step_1));
        tabAdapter.addFragment(new StepTwoFragment(), getString(R.string.step_2));

        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int i) {
                int fragmentBefore = i == 0 ? 1 : 0;
                FragmentLifecycle fragment = (FragmentLifecycle) tabAdapter.instantiateItem(viewPager,fragmentBefore);
                if (fragment != null) {
                    fragment.onPauseFragment();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {}
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager.getCurrentItem() == 1){
                    if(!validateDateAvail() | !validateTimeAvail() | !validatePhone() | !validateAddress() ){
                        viewPager.setCurrentItem(0);
                        return;
                    }else if(!validateDesc() | !validateImages()){
                        viewPager.setCurrentItem(1);
                        return;
                    }else {
                        btnNext.setClickable(false);
                        loadingContainer.setVisibility(View.VISIBLE);
                        sendRequest();
                    }
                }else{
                    viewPager.setCurrentItem(1);
                }
            }
        });
    }

    private void sendRequest(){
        List<MultipartBody.Part> listImg = new ArrayList<>();
        for (Uri uri : stepViewModel.getImageList().getValue()) {
            listImg.add(prepareFilePart("images",uri));
        }
        Long serviceId = getIntent().getLongExtra("serviceId",-1);
        ApiServices apiServices = ApiClient.getClient(this).create(ApiServices.class);
        Call<RespBase> call = apiServices.orderService(PrefUtils.getId(this),
                                                        PrefUtils.getApiKey(this) ,
                                                        serviceId == -1 ? null : serviceId,
                                                        stepViewModel.getTypeId().getValue(),
                                                        convert(stepViewModel.getDateAvail().getValue()),
                                                        convert(stepViewModel.getTimeAvail().getValue()),
                                                        convert(stepViewModel.getPhone().getValue()),
                                                        convert(stepViewModel.getAddress().getValue()),
                                                        convert(stepViewModel.getAddressLatLng().getValue()),
                                                        convert(stepViewModel.getDescription().getValue()),
                                                        listImg);
        call.enqueue(new Callback<RespBase>() {
            @Override
            public void onResponse(Call<RespBase> call, Response<RespBase> response) {
                //Toast.makeText(StepActivity.this, "OK", Toast.LENGTH_SHORT).show();
                if (response.body() != null && response.body().getStatus()){
                    //viewPager.setVisibility(View.VISIBLE);
                    loadingContainer.setVisibility(View.GONE);
                    SuccessDialog.showDialog(StepActivity.this,getString(R.string.text_request_success));
                }
            }

            @Override
            public void onFailure(Call<RespBase> call, Throwable t) {
                Toast.makeText(StepActivity.this, getString(R.string.please_check_the_internet), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean validateDateAvail(){
        if(stepViewModel.getDateAvail().getValue() == null){
            stepViewModel.setDateErr(getString(R.string.validate_date_avail));
            return false;
        }
        return true;
    }
    public boolean validateTimeAvail(){
        if(stepViewModel.getTimeAvail().getValue() == null){
            stepViewModel.setTimeErr(getString(R.string.validate_time_avail));
            return false;
        }
        return true;
    }
    public boolean validatePhone(){

        if(stepViewModel.getPhone().getValue().equals("")){
            stepViewModel.setPhoneErr(getString(R.string.validate_phone_text));
            return false;
        }
        return true;
    }
    public boolean validateAddress(){
        if(stepViewModel.getAddress().getValue().equals("")){
            stepViewModel.setAddressErr(getString(R.string.validate_address));
            return false;
        }
        return true;
    }

    public boolean validateDesc(){
        if( stepViewModel.getDescription().getValue() == null || stepViewModel.getDescription().getValue().equals("")){
            stepViewModel.setDescErr(getString(R.string.validate_desc));
            return false;
        }
        return true;
    }

    public boolean validateImages(){
        if(stepViewModel.getImageList().getValue() == null || stepViewModel.getImageList().getValue().isEmpty()){
            stepViewModel.setImagesErr(getString(R.string.validate_images));
            return false;
        }
        return true;
    }

    public RequestBody convert(String temp){
        return RequestBody.create(MediaType.parse("text/plain"), temp);
    }

    @NotNull
    private MultipartBody.Part prepareFilePart(String partName, Uri uri){
        File file = FileUtils.getFile(StepActivity.this,uri);
        File compressedImageFile = null ;
        try {
            compressedImageFile = new Compressor(this)
//                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
//                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFile(file,file.getName()+"@");

        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create( MediaType.parse("multipart/form-dataImg"), compressedImageFile);

        return MultipartBody.Part.createFormData(partName,  compressedImageFile.getName(), requestBody);

    }



}
