package com.chen.keepsort;

import android.content.Context;
import android.content.SharedPreferences;

public class SP {
    public static final String currentSportGroupKey = "currentSportGroup";
    public static final String sportGroupList = "sportGroupList";

    public static void save(String key, String value) {
        SharedPreferences sp = getSharedPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static SharedPreferences getSharedPreferences() {
        return App.getContext().getSharedPreferences("myfile", Context.MODE_PRIVATE);
    }

    public static String read(String key) {
        SharedPreferences sp = getSharedPreferences();
        return sp.getString(key, null);

    }
}
