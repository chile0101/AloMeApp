package com.thesis.alome.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thesis.alome.dao.Address;

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

    public static void setAddressList(Context context, String key, List<Address> addressList){
        Gson gson = new Gson();
        String addressListJson = gson.toJson(addressList);

        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("AddressList", addressListJson);
        editor.commit();
    }

    public static List<Address> getAddressList(Context context){
        Gson gson = new Gson();
        String addressListStr = getSharedPreferences(context).getString("AddressList","");
        List<Address> addressList = new ArrayList<Address>();
        Type type = new TypeToken<List<Address>>() {}.getType();
        addressList = gson.fromJson(addressListStr,type);
        return addressList;
    }
}
