package com.thesis.alome.config;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.thesis.alome.activity.SignInSignUpActivity;
import com.thesis.alome.fragment.SignUpFragment;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.thesis.alome.utils.Constants.BASE_URL;
import static com.thesis.alome.utils.Constants.BASE_URL_TEST;

public class ApiClient {
    private static Retrofit retrofit = null;
    private static int REQUEST_TIMEOUT = 60;
    private static OkHttpClient okHttpClient;

    public static Retrofit getClient(Context context){
        if(okHttpClient == null)
            initOkHttp(context);

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        }
        return retrofit;
    }

    public static Retrofit getAuthClient(){
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor("CUSTOMER", "alome@1510305"));
            okHttpClient = httpClient.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        return retrofit;
    }

    public static Retrofit getClientTest() {
        Retrofit retrofit = null;
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_TEST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    private static void initOkHttp(final Context context) {
         OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        // Check log
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json");
                if(!(context instanceof SignInSignUpActivity)){
                    if (!TextUtils.isEmpty(PrefUtils.getApiKey(context))) {
                        requestBuilder.addHeader("Authorization", "bearer "+ PrefUtils.getApiKey(context));
                    }
                }
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        okHttpClient = httpClient.build();
    }
}



