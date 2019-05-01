package com.example.vinitkumaragarwal.orderguru;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    ImageView imageView;
    Animation anim;

    private static int TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_welcome);

        imageView = (ImageView)findViewById(R.id.imageView) ;
        anim = AnimationUtils.loadAnimation(this,R.anim.uptodowm);
        imageView.setAnimation(anim);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(WelcomeActivity.this, loginActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, TIME_OUT);
            }

}
