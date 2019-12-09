package com.thesis.alome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ethanhua.skeleton.ViewReplacer;
import com.thesis.alome.R;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.config.ApiServices;
import com.thesis.alome.config.PrefUtils;
import com.thesis.alome.model.RespSignIn;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaunchActivity extends AppCompatActivity {

    private LinearLayout wrapper;
    private LinearLayout logoWrapper;
    private ViewReplacer mViewReplacer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        wrapper = (LinearLayout) findViewById(R.id.wrapper);
        logoWrapper = (LinearLayout) findViewById(R.id.logoWrapper);
        mViewReplacer = new ViewReplacer(logoWrapper);

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


        String userName = PrefUtils.getUserName(this); // is email
        String password = PrefUtils.getPassword(this);


        if(("").equals(userName) || ("").equals(password) ){
            Intent intent = new Intent(this,LandingActivity.class);
            //Clear the back stack
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else {
            ApiServices apiServices1 = ApiClient.getAuthClient().create(ApiServices.class);
            Call<RespSignIn> callLogin = apiServices1.signIn(userName,password,"password","CUSTOMER");
            callLogin.enqueue(new Callback<RespSignIn>() {
                @Override
                public void onResponse(Call<RespSignIn> callLogin, Response<RespSignIn> resp) {
                    if(resp.code() == 400){
                        Toast.makeText(getApplicationContext(), getString(R.string.email_or_password_is_invalid), Toast.LENGTH_SHORT).show();
                    }else{
                        PrefUtils.storeApiKey(getApplicationContext(),resp.body().getAccessToken());
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
                @Override
                public void onFailure(Call<RespSignIn> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), getString(R.string.please_check_the_internet), Toast.LENGTH_SHORT).show();
                    mViewReplacer.replace(R.layout.layout_no_internet_connection_include_icon);
                }
            });
        }


    }

}
