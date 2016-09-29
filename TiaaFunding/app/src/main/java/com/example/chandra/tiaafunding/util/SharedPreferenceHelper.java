package com.example.chandra.tiaafunding.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.example.chandra.tiaafunding.dto.UserInfo;

/**
 * Created by chandra on 9/25/2016.
 */
public class SharedPreferenceHelper {




    public static void putSharedPreferences(Context context, UserInfo info) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();

        editor.putString("pin", info.getPin());
        editor.putString("lname",info.getLastname());
        editor.putString("fname",info.getFirstname());
        editor.putString("email",info.getEmail());
        editor.putString("phone",info.getPhonenumber());
        editor.commit();
    }

    public static UserInfo loadSavedPreferences(Context context) {
        SharedPreferences sharedPreferences;
        UserInfo info=new UserInfo();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (sharedPreferences.contains("pin")) {
            info.setPin(sharedPreferences.getString("pin",null));
            info.setFirstname(sharedPreferences.getString("fname", null));
            info.setLastname(sharedPreferences.getString("lname", null));
            info.setEmail(sharedPreferences.getString("email", null));
            info.setPhonenumber(sharedPreferences.getString("phone",null));
        }
        return info;
    }



    public static void putSharedPreferences(Context context, String balance) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();

        editor.putString("balance", balance);

        editor.commit();
    }

    public static Double loadSavedPreferencesForBalance(Context context) {
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (sharedPreferences.contains("balance")) {
            return Double.valueOf(sharedPreferences.getString("balance",null));

        }
        return null;
    }
}
