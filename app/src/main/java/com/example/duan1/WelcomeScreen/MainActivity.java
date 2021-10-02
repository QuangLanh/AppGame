package com.example.duan1.WelcomeScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.LoginScreen.LoginScreen;
import com.example.duan1.Model.magChao;
import com.example.duan1.R;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIMER = 5000;
    ImageView imageView;
    Animation topanima,bottomanima;
    private View view;
    ProgressBar progressBar;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = getWindow().getDecorView();
        view.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility==0)
                    view.setSystemUiVisibility(hideSystemBars());
            }
        });
        imageView = findViewById(R.id.background_image);

        //tao animation
        topanima = AnimationUtils.loadAnimation(this,R.anim.side_anima);
        bottomanima  = AnimationUtils.loadAnimation(this,R.anim.bottom_anima);



        imageView.setAnimation(topanima);
        imageView.setAnimation(bottomanima);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIMER);
        progressBar = findViewById(R.id.loand);
        textView = findViewById(R.id.textloand);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                 WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar.setMax(100);
        progressBar.setScaleY(3f);
        magChao();

    }
    public void magChao(){
        magChao anim  = new magChao(this, progressBar, textView, 0f, 100f);
        anim.setDuration(5000);
        progressBar.setAnimation(anim);
    }

    private int hideSystemBars(){
        return  View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

}