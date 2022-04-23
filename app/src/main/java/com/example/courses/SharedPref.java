package com.example.courses;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPref {
    SharedPreferences preferences;
    Context context;
    public SharedPref(Context context){
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setEmail(String email){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email",email);
        editor.apply();
    }

    public String loadEmail(){
        return preferences.getString("email","");
    }
    public void setPassword(String password){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("password",password);
        editor.apply();
    }

    public String loadPassword(){
        return preferences.getString("password","");
    }
}
