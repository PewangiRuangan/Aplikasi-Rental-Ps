package com.example.rentalps;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPrefHelper {
    private static final String PREF_NAME = "billing_prefs";
    private static final String KEY_LIST = "billing_list";

    private SharedPreferences prefs;
    private Gson gson;

    public SharedPrefHelper(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveBilling(Billing data) {
        List<Billing> list = getBillingList();
        list.add(data);
        prefs.edit().putString(KEY_LIST, gson.toJson(list)).apply();
    }

    public List<Billing> getBillingList() {
        String json = prefs.getString(KEY_LIST, "");
        if (!json.isEmpty()) {
            Type type = new TypeToken<List<Billing>>() {}.getType();
            return gson.fromJson(json, type);
        }
        return new ArrayList<>();
    }
}
