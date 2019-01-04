package com.example.matt.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.matt.myapplication.db.UserDb;

public class SignIn extends Activity {

    EditText edtPassword, edtUsername;
    Button btnSignIn;
    UserDb userDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = findViewById(R.id.edt_password);
        edtUsername = findViewById(R.id.edt_username);

        btnSignIn = findViewById(R.id.btn_signin);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean failed = false;

                //prompts users that they have not entered a name or uername in the approriate destination
                if (TextUtils.isEmpty(edtUsername.getText())) {
                    edtUsername.setError("Please Enter Username");
                    failed = true;
                }
                if (TextUtils.isEmpty(edtPassword.getText())) {
                    edtPassword.setError("Please Enter Password");
                    failed = true;
                }

                if (!failed) {
                    signInUser();
                }
            }
        });

    }

    //checks if the user has actually signed in or not
    private void signInUser() {
        userDb = new UserDb(getApplicationContext());
        userDb.userSignIn(edtUsername.getText().toString(),
                edtPassword.getText().toString());
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("username", edtUsername.getText().toString());
        startActivity(intent);

    }
}
