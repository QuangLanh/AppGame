package com.example.duan1.GuideScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1.R;

public class GuiderActivity extends AppCompatActivity {
    ImageView logoguider;
    TextView sloganguider,tvxemhuongdan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guider);
        logoguider = findViewById(R.id.logoguider);
        sloganguider = findViewById(R.id.sloganguider);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(logoguider, PropertyValuesHolder.ofFloat("scaleX",0.9f),
                PropertyValuesHolder.ofFloat("scaleY",0.9f));
        objectAnimator.setDuration(500);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();
        //nút xem hướng dẫn
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        tvxemhuongdan = findViewById(R.id.tvxemhuongdan);
        tvxemhuongdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuiderActivity.this, SlideActivity.class));
            }
        });
    }
}