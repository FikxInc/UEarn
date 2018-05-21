package com.example.phiiphiroberts.uearn;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    RelativeLayout rellay1,rellay2;

    private TextView userEmail,userPassword;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rellay1 = findViewById(R.id.rellay1);
        rellay2 = findViewById(R.id.rellay2);

        handler.postDelayed(runnable,1000);//time out for the splash screen

        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);

        //CALLING METHOD nextPage
        nextPage();
    }

    public void loginButtonClicked(View v)
    {
        final String user_email = userEmail.getText().toString().trim();
        final String user_pass = userPassword.getText().toString().trim();

        //Checking for Empty Fields and setting errors to fields
        if (TextUtils.isEmpty(user_email) && TextUtils.isEmpty(user_pass)) {
            userEmail.setError("This field is required");
            userPassword.setError("This field is required");
            return;
        } else if (TextUtils.isEmpty(user_email) && !TextUtils.isEmpty(user_pass)) {
            userEmail.setError("This field is required");
            return;
        } else if (!TextUtils.isEmpty(user_pass) && TextUtils.isEmpty(user_pass)) {
            userPassword.setError("This field is required");
            return;
        } else {

        }

        //setting a progress dialogue
        final ProgressDialog mDialog = new ProgressDialog(Login.this);
        mDialog.setMessage("Please Waiting...");
        mDialog.show();


    }
}
