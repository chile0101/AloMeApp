package com.thesis.alome.config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.thesis.alome.utils.Constants.BASE_URL;


public class CallApi {
    public static LinkApi instance=null;

    public static LinkApi getInstance() {
        if (instance == null) {
            synchronized (CallApi.class) {
                if (instance == null) {
                    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                    httpClient.writeTimeout(15 * 60 * 1000, TimeUnit.MILLISECONDS);
                    httpClient.readTimeout(60 * 1000, TimeUnit.MILLISECONDS);
                    httpClient.connectTimeout(20 * 1000, TimeUnit.MILLISECONDS);

                    OkHttpClient client = httpClient.build();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build();
                    instance = retrofit.create(LinkApi.class);
                }
            }
        }
        return instance;
    }
}