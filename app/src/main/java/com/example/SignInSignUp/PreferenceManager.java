package com.example.SignInSignUp;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.AppUtils.VariableBag;

public class PreferenceManager {

    private final SharedPreferences sharedpreferences;

    private final SharedPreferences.Editor editor;

    public PreferenceManager(Context context) {
        this.sharedpreferences = context.getSharedPreferences(VariableBag.PREF_NAME,Context.MODE_PRIVATE);
        this.editor = sharedpreferences.edit();
    }

    public void setKeyValueString(String key, String value){
        try{
            editor.putString(key, value);
            editor.commit();
        }
        catch (Exception e){
            e.getStackTrace();
        }
    }

    public String getKeyValueString(String key){
        return sharedpreferences.getString(key,"Empty");
    }

    public void clearPreference(){
        try{
            editor.clear();
            editor.commit();
        }
        catch(Exception e){
            e.getStackTrace();
        }
    }

    public void removePreference(String keyR){
        editor.remove(keyR);
        editor.commit();
    }

    public void setUserLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(VariableBag.USER_LOGGED_IN, isLoggedIn).commit();
    }

    public boolean getUserLoggedIn() {
        return sharedpreferences.getBoolean(VariableBag.USER_LOGGED_IN, false);
    }

    public void setUserId(String userId) {
       editor.putString(VariableBag.USER_ID,userId).commit();
    }

    public String getUserId() {
        return sharedpreferences.getString(VariableBag.USER_ID,"");
    }

}
