package com.example.unemployed.momapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;
    public Session(Context context){
        this.context = context;
        preferences = context.getSharedPreferences("Momapp",Context.MODE_PRIVATE);
        editor = preferences.edit();
    }
    public void isLogin(Boolean logined){
        editor.putBoolean("login",logined);
        editor.commit();

    }
    public Boolean loggedin(){
        return preferences.getBoolean("login",false);
    }
}
