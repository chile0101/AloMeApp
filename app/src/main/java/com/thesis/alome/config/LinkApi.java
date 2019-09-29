package com.thesis.alome.config;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface LinkApi {

//    @POST("customers/signin")
//    Call<ResponseSignin> signin(@Body SigninCustomer loginUser);

    @FormUrlEncoded
    @POST("customers/signup")
    Call<String> signUp(
            @Field("fullname") String full_name,
            @Field("email") String email,
            @Field("password") String password
    );


}
