package com.thesis.alome.config;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtils {

    private static final String PREFS_TAG = "SharedPrefs";

    public PrefUtils() {
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE);
    }

    public static void storeProfile(Context context, long id,String userName,String password, String shortName, String fullName,String phone,String address, String latitude, String longitude){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong("id", id);
        editor.putString("userName", userName);  // is email
        editor.putString("password",password);
        editor.putString("shortName", shortName);
        editor.putString("fullName", fullName);
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

    public static String getUserName(Context context){
        return getSharedPreferences(context).getString("userName","");
    }

    public static String getPassword(Context context){
        return getSharedPreferences(context).getString("password","");
    }

    public static String getShortName(Context context){
        return getSharedPreferences(context).getString("shortName","");
    }

    public static String getFullName(Context context){
        return getSharedPreferences(context).getString("fullName","");
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

    public static boolean clearAll(Context context){
        return getSharedPreferences(context).edit().clear().commit();
    }

}
