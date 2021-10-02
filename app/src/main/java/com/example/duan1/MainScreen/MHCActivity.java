package com.example.duan1.MainScreen;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.duan1.Fragment.RankActivity;
import com.example.duan1.GuideScreen.GuiderActivity;
import com.example.duan1.LoginScreen.LoginScreen;
import com.example.duan1.Model.TaiKhoan;
import com.example.duan1.R;
import com.example.duan1.Service.Sevice_media;
import com.example.duan1.TheLoaiScreen.TheLoaiActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MHCActivity extends AppCompatActivity {
    ImageView battatnhac,btnclose;
    boolean nhac = true;
    TextView user_name;
    Dialog dialog;
    Button btnnewgame,btnguider,btnExit,btnSetting,btnDiem;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    AudioManager audioManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_h_c);
        AudioManager audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        //animation nút New Game
        user_name = findViewById(R.id.user_name);
         btnnewgame = findViewById(R.id.btnnewgame);
         ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(btnnewgame, PropertyValuesHolder.ofFloat("scaleX",0.9f),
                 PropertyValuesHolder.ofFloat("scaleY",0.9f));
         objectAnimator.setDuration(500);
         objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
         objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
         objectAnimator.start();
         //hiển thị tên đăng nhập người dùng
        final Intent username = getIntent();
        String User_name = username.getStringExtra("user_name");
        user_name.setText("Xin Chào " + User_name);
        //SharedPreferences
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("USERNAME",User_name);
        edit.commit();
        /*database = FirebaseDatabase.getInstance();
                databaseReference = database.getReference("TaiKhoan");
                if (Integer.parseInt(diem)>diem1){
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot d:snapshot.getChildren())
                            {
                                final TaiKhoan taiKhoan = d.getValue(TaiKhoan.class);
                                if (username.equals(taiKhoan.User)){
                                    databaseReference = database.getReference("diem1");
                                    databaseReference.setValue(diem);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }*/
         //nút trợ giúp
        btnguider = findViewById(R.id.btntrogiup);
        btnguider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MHCActivity.this, GuiderActivity.class);
                startActivity(i);
            }
        });
        //nút new game
        btnnewgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MHCActivity.this, TheLoaiActivity.class);
                startActivity(i);
            }
        });
         //Bật tắt nhạc nền
        battatnhac=findViewById(R.id.tatam);
        if (nhac==true){
        Intent i = new Intent(MHCActivity.this, Sevice_media.class);
        startService(i);
        battatnhac.setImageResource(R.drawable.batam);
        }
        if (nhac==false){
            Intent i = new Intent(MHCActivity.this,Sevice_media.class);
            stopService(i);
            battatnhac.setImageResource(R.drawable.tatam);
        }
        battatnhac.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(nhac==true)
                    nhac =false;
                else
                    nhac=true;

                if (nhac==true){
                    Intent i = new Intent(MHCActivity.this,Sevice_media.class);
                    startService(i);
                    battatnhac.setImageResource(R.drawable.batam);
                }
                if (nhac==false){
                    Intent i = new Intent(MHCActivity.this,Sevice_media.class);
                    stopService(i);
                    battatnhac.setImageResource(R.drawable.tatam);
                }
            }
        });

        //nút thoát
        btnExit = findViewById(R.id.btnthoat);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginScreen.class));
                finish();
            }
        });
        //nút cài đặt
        btnSetting = findViewById(R.id.btncaidat);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              openDialogSetting(MHCActivity.this);
            }
        });
        //nút xếp hạng
        btnDiem = findViewById(R.id.btnxephang);
        btnDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MHCActivity.this, RankActivity.class);
                startActivity(i);
            }
        });


    }
    protected void openDialogSetting(final Context context)
    {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_setting);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        SeekBar seekBar = findViewById(R.id.seekbar_volume);
        dialog.show();
        btnclose = dialog.findViewById(R.id.btn_close);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}