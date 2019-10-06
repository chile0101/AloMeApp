package com.thesis.alome.config;

import com.thesis.alome.dao.ReqSignUp;
import com.thesis.alome.dao.RespBase;
import com.thesis.alome.dao.RespSignIn;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


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
    Call<RespBase> getProfile();

    @GET("service/types")
    Call<RespBase> getMainData();

    @GET("service/types/{typeId}")
    Call<RespBase> getServicesByTypeId(@Path("typeId") int typeId);

}
