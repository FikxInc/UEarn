package com.example.phiiphiroberts.uearn;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Login extends AppCompatActivity {
    RelativeLayout rellay1; //rellay2;
    Button btnSignIn;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText Email,Password;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rellay1 = findViewById(R.id.rellay1);

        handler.postDelayed(runnable, 1000);//time out for the splash screen

        Email = findViewById(R.id.userEmail);
        Password = findViewById(R.id.userPassword);
        mAuth = FirebaseAuth.getInstance();
        mDatabase  = FirebaseDatabase.getInstance().getReference().child("users");
        btnSignIn = findViewById(R.id.logBttn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean connected = false;
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
                {

                    final String user_email = Email.getText().toString().trim();
                    final String user_pass = Password.getText().toString().trim();

                    if (!TextUtils.isEmpty(user_email) && !TextUtils.isEmpty(user_pass)) {
                            mAuth.createUserWithEmailAndPassword(user_email, user_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        String user_id = mAuth.getCurrentUser().getUid();
                                        DatabaseReference current_user = mDatabase.child((user_id));
                                        current_user.child("EMAIL").setValue(user_email);
                                        FancyToast.makeText(Login.this, "Login Successful", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                    }else {
                                        FancyToast.makeText(Login.this, "Account Exist", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                    }
                                }
                            });
                    }

                    //Checking for Empty Fields and setting errors to fields
                    if (TextUtils.isEmpty(user_email) && TextUtils.isEmpty(user_pass)) {
                        Email.setError("Required Field");
                        Password.setError("Required Field");
                        return;
                    } else if (TextUtils.isEmpty(user_email) && !TextUtils.isEmpty(user_pass)) {
                        Email.setError("Required Field");
                        return;
                    } else if (!TextUtils.isEmpty(user_email) && TextUtils.isEmpty(user_pass)){
                        Password.setError("Required Field");

                    } else {

                    }

                    connected = true;
                }
                else{
                    FancyToast.makeText(Login.this, "Please Check your Internet Connection", FancyToast.LENGTH_LONG, FancyToast.INFO, false).show();
                    connected = false;
                }

            }
        });

    }
}
