package com.thesis.alome.config;

import com.thesis.alome.model.Customer;
import com.thesis.alome.model.ReqSignUp;
import com.thesis.alome.model.RespBase;
import com.thesis.alome.model.RespSignIn;
import com.thesis.alome.model.Service;
import com.thesis.alome.model.TypeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiServices {

    @FormUrlEncoded
    @POST("/api/oauth/token")
    Call<RespSignIn> signIn(
            @Field("username") String email,
            @Field("password") String password,
            @Field("grant_type") String grant_type,
            @Field("client_id") String client_id
    );
    @POST("public/customer/signup")
    Call<RespBase> signUp(
            @Body ReqSignUp reqSignUp
    );

    @GET("customers/profile")
    Call<RespBase<Customer>> getProfile(@Query("access_token") String accessToken);

    @GET("service/types")
    Call<RespBase<List<TypeService>>> getMainData();

    @GET("service/types/{id}")
    Call<RespBase<TypeService>> getServicesByTypeId(@Path("id") long typeId);

    @GET("services/detail/{id}")
    Call<RespBase<Service>> getServiceById(@Path("id") long serviceId);

}
