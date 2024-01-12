package com.example.flavorquest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SpalshScreenActivity extends AppCompatActivity
{
    ImageView img;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);

        img = findViewById(R.id.greenlogosplash);
        Animation anime = AnimationUtils.loadAnimation(SpalshScreenActivity.this,R.anim.anim);
        img.startAnimation(anime);

        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if(mAuth.getCurrentUser() != null)
                {
                    startActivity(new Intent(SpalshScreenActivity.this,MapsActivity.class));

                }
                else
                {
                    startActivity(new Intent(SpalshScreenActivity.this,MainActivity.class));
                }
                finish();
            }
        },2000);
    }
}