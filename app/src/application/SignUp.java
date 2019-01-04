package com.example.matt.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.matt.myapplication.db.UserDb;
import com.example.matt.myapplication.model.User;

public class SignUp extends Activity {
    Button btnSignUp;
    EditText edtEmail;
    EditText edtConfirm;
    EditText edtPassword;
    EditText edtUsername;

    UserDb userDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = findViewById(R.id.btn_signup);
        edtUsername = findViewById(R.id.edt_username);
        edtConfirm = findViewById(R.id.edt_confirm);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean success = true;

                //Checks that there is a value inputed in the appropriate area. there is no blank left blank
                if (TextUtils.isEmpty(edtUsername.toString())) {
                    edtUsername.setError("Enter Username");
                    success = false;
                }
                if (TextUtils.isEmpty(edtPassword.toString())) {
                    edtPassword.setError("Enter Password");
                    success = false;
                }
                if (TextUtils.isEmpty(edtEmail.toString())) {
                    edtEmail.setError("Enter Email");
                    success = false;
                }

                edtConfirm.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    //check that the password is matching the original password before saved in the database
                    @Override
                    public void afterTextChanged(Editable s) {
                        String password = edtPassword.getText().toString();
                        if (s.length() > 0 && password.length() > 0) {
                            if (!edtConfirm.equals(edtPassword)) {
                                edtConfirm.setError("Password does't match");
                            }
                        }

                    }
                });

                if (success) {
//
//                    Intent intent = new Intent(getApplicationContext(), homepage.class);
//                    intent.putExtra()
//                    startActivity(intent);
                }


            }
        });
    }

    private void addSignUp() {
        userDb = new UserDb(getApplicationContext());

        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        String email = edtEmail.getText().toString();

        userDb.addUser(new User(username, password, email));

    }
}

