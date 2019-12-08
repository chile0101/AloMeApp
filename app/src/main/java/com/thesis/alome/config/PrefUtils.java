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

    public static void storeProfile(Context context, long id,
                                    String userName,String shortName,
                                    String fullName,String phone,
                                    String address, String latitude,
                                    String longitude, String gender,
                                    String dateOfBirth, String avatar){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong("id", id);
        editor.putString("userName", userName);  // is email
        editor.putString("shortName", shortName);
        editor.putString("fullName", fullName);
        editor.putString("phone", phone);
        editor.putString("address", address);
        editor.putString("latitude", latitude);
        editor.putString("longitude", longitude);
        editor.putString("gender",gender);
        editor.putString("dateOfBirth",dateOfBirth);
        editor.putString("avatar",avatar);

        editor.commit();
    }

    public static void storeApiKey(Context context, String access_token) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("accessToken", access_token);
        editor.commit();
    }

    public static void storePassword(Context context, String pass) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("password", pass);
        editor.commit();
    }

    public static void storeShortName(Context context, String shortName) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("shortName", shortName);
        editor.commit();
    }

    public static String getApiKey(Context context) {
        return getSharedPreferences(context).getString("accessToken", null);
    }

    public static long getId(Context context){
        return getSharedPreferences(context).getLong("id",0);
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

    public static String getGender(Context context){
        return getSharedPreferences(context).getString("gender","");
    }

    public static String getDateOfBirth(Context context){
        return getSharedPreferences(context).getString("dateOfBirth","");
    }

    public static String getAvatar(Context context){
        return getSharedPreferences(context).getString("avatar","");
    }

    public static boolean clearAll(Context context){
        return getSharedPreferences(context).edit().clear().commit();
    }

}
