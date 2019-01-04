package com.example.matt.myapplication;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class UserPrefActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //refers to the main settings page
        addPreferencesFromResource(R.xml.user_pref);

    }
}
