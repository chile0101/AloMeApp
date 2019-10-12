package com.thesis.alome.config;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtils {

    public PrefUtils() {
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("ALOME", Context.MODE_PRIVATE);
    }

    public static void storeProfile(Context context, String short_name,int id){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("SHORT_NAME", short_name);
        editor.putInt("ID", id);
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

    public static String getShortName(Context context){
        return getSharedPreferences(context).getString("SHORT_NAME","");
    }
}
