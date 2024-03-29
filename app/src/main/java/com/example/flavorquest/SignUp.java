package com.example.flavorquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity
{

    ImageButton i1,i2,i3;
    Button btnsignup;
    EditText signuname,signmail,signpass,signcpass;
    String tsignuname,tsignmail,tsignpass,tsigncpass;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        i1 = findViewById(R.id.google);
        i2 = findViewById(R.id.fb);
        i3 = findViewById(R.id.twitter);
        signuname = findViewById(R.id.signuname);
        signmail = findViewById(R.id.signmail);
        signpass = findViewById(R.id.signpass);
        signcpass = findViewById(R.id.signcpass);
        btnsignup = findViewById(R.id.btnsignup);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

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

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tsignuname = signuname.getText().toString();
                tsignmail = signmail.getText().toString().trim();
                tsignpass = signpass.getText().toString().trim();
                tsigncpass = signcpass.getText().toString().trim();

                if(!TextUtils.isEmpty(tsignuname))
                {
                    if(!TextUtils.isEmpty(tsignmail))
                    {
                        if(tsignmail.matches(emailPattern))
                        {
                            if(!TextUtils.isEmpty(tsignpass))
                            {
                                if(!TextUtils.isEmpty(tsigncpass))
                                {
                                    if(tsigncpass.equals(tsignpass))
                                    {
                                        SignUpUser();
                                    }
                                    else
                                    {
                                        signcpass.setError("Confirm Password Should be Same As Password");
                                    }
                                }
                                else
                                {
                                    signcpass.setError("Confirm Password Field Can't Be Empty");
                                }
                            }
                            else
                            {
                                signpass.setError("Password Field Can't Be Empty");
                            }
                        }
                        else
                        {
                            signmail.setError("Enter Valid Email Address");
                        }
                    }
                    else
                    {
                        signmail.setError("Email Field Can't Be Empty");
                    }
                }
                else
                {
                    signuname.setError("Username Field Can't Be Empty");
                }
            }
        });

    }

    private void SignUpUser() {
        btnsignup.setVisibility(View.INVISIBLE);

        mAuth.createUserWithEmailAndPassword(tsignmail,tsignpass).addOnSuccessListener(new OnSuccessListener<AuthResult>()
        {
            @Override
            public void onSuccess(AuthResult authResult) {
                Map<String , Object> user = new HashMap<>();
                user.put("Name",tsignuname);
                user.put("Email",tsignmail);
                user.put("Password",tsignpass);

                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(SignUp.this, "Sign Up Successful !", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUp.this, login_page.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(SignUp.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                btnsignup.setVisibility(View.VISIBLE);
            }
        });
    }
}