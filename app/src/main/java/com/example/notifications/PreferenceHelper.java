package com.example.notifications;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    public static String FIRE_BASE_IF_PREF = "FIREBASE_IF_PREF";
    public static final String PREFERENCESNAME = "";

    public static void setPreferenceValue(Context context, String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCESNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStringPreferenceValue(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCESNAME, Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }
}
