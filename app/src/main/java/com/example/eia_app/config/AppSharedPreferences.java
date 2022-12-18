package com.example.eia_app.config;

import static com.example.eia_app.api.APIClient.BASE_URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSharedPreferences {

    public static SharedPreferences sp;
    public static SharedPreferences.Editor spEdit;

    public static final String ACC_TOKEN ="acc_token";


    @SuppressLint({"applyPrefEdits", "CommitPrefEdits"})
    public static void initSharedPreferences(Context context) {
        if (sp == null) {
            sp = PreferenceManager.getDefaultSharedPreferences(context);
            spEdit = sp.edit();
        }
    }

    public static void CACHE_AUTH_DATA(String accToken){
        spEdit.putString(ACC_TOKEN,accToken).apply();
    }

    public static String GET_ACC_TOKEN(){
        return sp.getString(ACC_TOKEN,"");
    }

    public static void CACHE_USER(Long userId){
        spEdit.putLong("userId", userId).apply();
    }

    public static Long GET_USER_ID(){
        return sp.getLong("userId", 0);
    }
}
