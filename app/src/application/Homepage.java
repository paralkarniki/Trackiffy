package com.example.matt.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Homepage extends Activity {

    Button btnSignIn;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        btnSignIn = findViewById(R.id.btn_signin);
        btnSignUp = findViewById(R.id.btn_signup);

        //Button Sign in to send back to the Homepage
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this, SignIn.class);
                startActivity(intent);
            }
        });
        //Button Sign up to send back to the Homepage
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this, SignUp.class);
                startActivity(intent);
            }
        });

    }

    protected void onResume() {
        super.onResume();

        // get the Shared Preferences that are used by the preference screens
        SharedPreferences userPrefs =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        // first we do the CheckBox
        boolean savePassword = userPrefs.
                getBoolean(getString(R.string.preference_save_password), false);
        boolean paymentReminder = userPrefs.
                getBoolean(getString(R.string.preference_payment_reminder), false);
    }


    //Menu button fo the user to reverte back and forth to the setting and reminder page
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    //allows user to select between settings page and reminder page
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == 0) {
            Intent intent1 = new Intent(getApplicationContext(),
                    UserPrefActivity.class);
            startActivity(intent1);
        } else {
            Intent intent2 = new Intent(getApplicationContext(),
                    Remind.class);
            startActivity(intent2);
        }

        return super.onOptionsItemSelected(item);

    }

}
