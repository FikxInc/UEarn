package com.example.phiiphiroberts.uearn;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

public class AddDownliner extends AppCompatActivity {
    RelativeLayout DownRelay; //
    Button btnAddDown;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText DownUserName,DownMail,DownPass;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            DownRelay.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_downliner);

        DownRelay = findViewById(R.id.DownRelay);

        handler.postDelayed(runnable, 1000);//time out for the splash screen


        DownUserName = findViewById(R.id.DownUserName);
        DownMail = findViewById(R.id.DownEmail);
        DownPass = findViewById(R.id.DownPassword);
        mAuth = FirebaseAuth.getInstance();
        mDatabase  = FirebaseDatabase.getInstance().getReference().child("users");
        btnAddDown = findViewById(R.id.btnAddDown);


        btnAddDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean connected = false;
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
                {

                    final String user_name = DownUserName.getText().toString().trim();
                    final String user_mail = DownMail.getText().toString().trim();
                    final String user_password = DownPass.getText().toString().trim();

                    if (!TextUtils.isEmpty(user_name) && !TextUtils.isEmpty(user_mail) && !TextUtils.isEmpty(user_password)) {
                        mAuth.createUserWithEmailAndPassword(user_mail, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String user_id = mAuth.getCurrentUser().getUid();
                                    DatabaseReference current_user = mDatabase.child((user_id));
                                    current_user.child("EMAIL").setValue(user_mail);
                                    current_user.child("USERNAME").setValue(user_name);

                                    FancyToast.makeText(AddDownliner.this, "Sign Up Successful", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                }else {
                                    FancyToast.makeText(AddDownliner.this, "Account Exist", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                }
                            }
                        });
                    }

                    //Checking for Empty Fields and setting errors to fields
                    if (TextUtils.isEmpty(user_mail) && TextUtils.isEmpty(user_password)) {
                        DownUserName.setError("Required Field");
                        DownPass.setError("Required Field");
                        DownMail.setError("Required Field");
                        return;
                    } else if (TextUtils.isEmpty(user_mail) && !TextUtils.isEmpty(user_password) && !TextUtils.isEmpty(user_name)) {
                        DownMail.setError("Required Field");
                        return;
                    } else if (!TextUtils.isEmpty(user_mail) && TextUtils.isEmpty(user_password) && !TextUtils.isEmpty(user_name)){
                        DownPass.setError("Required Field");

                    } else if (!TextUtils.isEmpty(user_mail) && !TextUtils.isEmpty(user_password) && TextUtils.isEmpty(user_name)){
                        DownUserName.setError("Required Field");

                    } else if (TextUtils.isEmpty(user_mail) && TextUtils.isEmpty(user_password) && !TextUtils.isEmpty(user_name)) {
                        DownMail.setError("Required Field");
                        DownPass.setError("Required Field");
                        return;
                    }else if (TextUtils.isEmpty(user_mail) && !TextUtils.isEmpty(user_password) && TextUtils.isEmpty(user_name)) {
                        DownMail.setError("Required Field");
                        DownUserName.setError("Required Field");
                        return;
                    } else if (!TextUtils.isEmpty(user_mail) && TextUtils.isEmpty(user_password) && TextUtils.isEmpty(user_name)) {
                        DownUserName.setError("Required Field");
                        DownPass.setError("Required Field");
                        return;
                    } else {

                    }

                    connected = true;
                }
                else{
                    FancyToast.makeText(AddDownliner.this, "Please Check your Internet Connection", FancyToast.LENGTH_LONG, FancyToast.INFO, false).show();
                    connected = false;
                }

            }
        });

    }
}
