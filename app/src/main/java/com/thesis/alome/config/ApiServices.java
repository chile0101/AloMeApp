package com.thesis.alome.config;

import com.thesis.alome.model.Customer;
import com.thesis.alome.model.Provider;
import com.thesis.alome.model.ReqOrder;
import com.thesis.alome.model.ReqSignUp;
import com.thesis.alome.model.RespBase;
import com.thesis.alome.model.RespSignIn;
import com.thesis.alome.model.Service;
import com.thesis.alome.model.ServiceType;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Call<RespBase<List<ServiceType>>> getMainData();

    @GET("service/types/{id}")
    Call<RespBase<ServiceType>> getServicesByTypeId(@Path("id") long typeId);

    @GET("services/detail/{id}")
    Call<RespBase<Service>> getServiceById(@Path("id") long serviceId);

    @POST("customers/{id}/request")
    @Multipart
    Call<RespBase> orderService(@Path("id") Long id,
                                @Query("access_token") String accessToken,
                                @Part("serviceId") Long serviceId,
                                @Part("serviceTypeId") Long serviceTypeId,
                                @Part("date") RequestBody date,
                                @Part("time") RequestBody time,
                                @Part("phone") RequestBody phone,
                                @Part("address") RequestBody addressStr,
                                @Part("latlng") RequestBody addressLatLng,
                                @Part("description") RequestBody description,
                                @Part List<MultipartBody.Part> images);

    @GET("customers/provider/{id}")
    Call<RespBase<List<Provider>>> getProviderNearMe(@Path("id") long serviceId,
                                                     @Query("access_token") String accessToken);


}
