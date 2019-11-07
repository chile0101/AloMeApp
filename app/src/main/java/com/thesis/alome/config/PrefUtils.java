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

    public static void storeProfile(Context context, long id, String shortName,String phone){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong("ID", id);
        editor.putString("SHORT_NAME", shortName);
        editor.putString("PHONE", phone);
        editor.commit();
    }

    public static void storeApiKey(Context context, String access_token) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("ACCESS_TOKEN", access_token);
        editor.commit();
    }

    public static String getApiKey(Context context) {
        return getSharedPreferences(context).getString("ACCESS_TOKEN", null);
    }

    public static long getId(Context context){
        return getSharedPreferences(context).getLong("ID",-1);
    }

    public static String getShortName(Context context){
        return getSharedPreferences(context).getString("SHORT_NAME","");
    }

    public static String getPhoneNumber(Context context){
        return getSharedPreferences(context).getString("PHONE","");
    }


}
