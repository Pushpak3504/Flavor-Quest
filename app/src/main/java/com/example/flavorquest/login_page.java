package com.example.flavorquest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_page extends AppCompatActivity
{
    ImageButton i1,i2,i3;
    TextView forgot;
    TextView now;
    Button btnlogin;
    EditText logmail,logpass;
    String tlogmail,tlogpass;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        i1 = findViewById(R.id.google);
        i2 = findViewById(R.id.fb);
        i3 = findViewById(R.id.twitter);
        now = findViewById(R.id.signupnow);
        btnlogin = findViewById(R.id.btnlogin);
        logmail = findViewById(R.id.logmail);
        logpass = findViewById(R.id.logpass);
        forgot = findViewById(R.id.forgottxt);
        mAuth = FirebaseAuth.getInstance();


        i1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://myaccount.google.com/"));
                startActivity(intent);
            }
        });

        i2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"));
                startActivity(intent);
            }
        });

        i3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/i/flow/login"));
                startActivity(intent);
            }
        });

        now.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_page.this,SignUp.class);
                startActivity(intent);
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_page.this,ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                tlogmail = logmail.getText().toString().trim();
                tlogpass = logpass.getText().toString().trim();

                if(!TextUtils.isEmpty(tlogmail))
                {
                    if(tlogmail.matches(emailPattern))
                    {
                        if(!TextUtils.isEmpty(tlogpass))
                        {
                            SignInUser();
                        }
                        else
                        {
                            logpass.setError("Password Field Can't be Empty");
                        }
                    }
                    else
                    {
                        logmail.setError("Enter Valid Email Address");
                    }
                }
                else
                {
                    logmail.setError("Email Field Can't be Empty");
                }
            }
        });
    }

    private void SignInUser() {
        btnlogin.setVisibility(View.INVISIBLE);
        mAuth.signInWithEmailAndPassword(tlogmail,tlogpass).addOnSuccessListener(new OnSuccessListener<AuthResult>()
        {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(login_page.this, "Login Successful !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(login_page.this,MapsActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(login_page.this, "Error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                btnlogin.setVisibility(View.VISIBLE);
            }
        });
    }
}