package com.example.duan1.WelcomeScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1.R;

public class WelcomeActivity extends AppCompatActivity {
    private  static  int SPLASH_TIMER = 5000;

    ImageView imageView;
    TextView tv;
    Animation topanima,bottomanima;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        view = getWindow().getDecorView();
        view.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int i) {
                if(i ==0)
                    view.setSystemUiVisibility(hideSystemBars());
            }
        });
        imageView = findViewById(R.id.background_image);

        //tạo animation
        topanima  = AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottomanima  = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        imageView.setAnimation(topanima);
        imageView.setAnimation(bottomanima);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIMER);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            view.setSystemUiVisibility(hideSystemBars());
        }
    }
    private int hideSystemBars(){
        return  View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }
}
