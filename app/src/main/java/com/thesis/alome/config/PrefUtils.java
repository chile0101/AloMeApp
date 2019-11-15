package com.thesis.alome.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrefUtils {

    private static final String PREFS_TAG = "SharedPrefs";

    public PrefUtils() {
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE);
    }

    public static void storeProfile(Context context, long id, String shortName,String phone,String address, String latitude, String longitude){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong("id", id);
        editor.putString("shortName", shortName);
        editor.putString("phone", phone);
        editor.putString("address", address);
        editor.putString("latitude", latitude);
        editor.putString("longitude", longitude);
        editor.commit();
    }

    public static void storeApiKey(Context context, String access_token) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("accessToken", access_token);
        editor.commit();
    }

    public static String getApiKey(Context context) {
        return getSharedPreferences(context).getString("accessToken", null);
    }

    public static long getId(Context context){
        return getSharedPreferences(context).getLong("id",-1);
    }

    public static String getShortName(Context context){
        return getSharedPreferences(context).getString("shortName","");
    }

    public static String getPhoneNumber(Context context){
        return getSharedPreferences(context).getString("phone","");
    }

    public static String getAddress(Context context){
        return getSharedPreferences(context).getString("address","");
    }

    public static String getLatitude(Context context){
        return getSharedPreferences(context).getString("latitude","");
    }

    public static String getLongitude(Context context){
        return getSharedPreferences(context).getString("longitude","");
    }



}
