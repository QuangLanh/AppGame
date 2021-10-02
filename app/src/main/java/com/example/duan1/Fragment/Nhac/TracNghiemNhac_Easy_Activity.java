package com.example.duan1.Fragment.Nhac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.duan1.Fragment.Game.TracNghiemGame_Easy_Activity;
import com.example.duan1.Fragment.KetQuaActivity;
import com.example.duan1.Model.CauHoi;
import com.example.duan1.R;
import com.example.duan1.Service.Sevice_media;
import com.example.duan1.Service.Sevice_media_nhacnnen_amnhac;
import com.example.duan1.TheLoaiScreen.TheLoaiActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TracNghiemNhac_Easy_Activity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ImageView img_game;
    LottieAnimationView btnhome;
    TextView tvTitle_amnhac,tv_cauhoi_amnhac,tv_dapanA_amnhac,tv_dapanB_amnhac,tv_dapanC_amnhac,tv_dapanD_amnhac,tv_count_question,txthienthidiem,txtdiemcong;
    int total = 0;
    int diem = 0;
    int caudung = 0;
    int causai = 0;
    private long lastime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trac_nghiem_nhac__easy_);
        tvTitle_amnhac = findViewById(R.id.tvTitle_amnhac);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(tvTitle_amnhac, PropertyValuesHolder.ofFloat("scaleX",0.9f),
                PropertyValuesHolder.ofFloat("scaleY",0.9f));
        objectAnimator.setDuration(500);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();
        Intent i = new Intent(TracNghiemNhac_Easy_Activity.this, Sevice_media_nhacnnen_amnhac.class);
        Intent i1 = new Intent(TracNghiemNhac_Easy_Activity.this, Sevice_media.class);
        startService(i);
        stopService(i1);
        txtdiemcong = findViewById(R.id.txtdiemcong_nhac_easy);
        txthienthidiem = findViewById(R.id.txthienthidiem_nhac_easy);
        btnhome = findViewById(R.id.btnhome_nhac_easy);
        tv_count_question = findViewById(R.id.count_question_amnhac);
        tv_cauhoi_amnhac= findViewById(R.id.tv_cauhoi_amnhac);
        tv_dapanA_amnhac = findViewById(R.id.tv_dapanA_amnhac);
        tv_dapanB_amnhac= findViewById(R.id.tv_dapanB_amnhac);
        tv_dapanC_amnhac = findViewById(R.id.tv_dapanC_amnhac);
        tv_dapanD_amnhac = findViewById(R.id.tv_dapanD_amnhac);
        tvTitle_amnhac = findViewById(R.id.tvTitle_amnhac);
        //btnhome
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TracNghiemNhac_Easy_Activity.this, TheLoaiActivity.class);
                startActivity(i);
                finish();
            }
        });
        chuyenCauhoi();
    }
    //đưa Firebase lên layout
    private void chuyenCauhoi()
    {
        total++;
        if (total > 15)
        {
            KetThuc();
        }
        else
        {
            databaseReference = database.getInstance().getReference().child("TheLoai").child("AmNhac").child("De").child(String.valueOf(total));
            tv_count_question.setText(total + "/" + "15");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    final CauHoi cauhoi = snapshot.getValue(CauHoi.class);

                    tv_cauhoi_amnhac.setText(cauhoi.getCauHoi());
                    tv_dapanA_amnhac.setText(cauhoi.getA());
                    tv_dapanB_amnhac.setText(cauhoi.getB());
                    tv_dapanC_amnhac.setText(cauhoi.getC());
                    tv_dapanD_amnhac.setText(cauhoi.getD());

                    tv_dapanA_amnhac.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastime<1500){
                                return;
                            }
                            lastime = SystemClock.elapsedRealtime();
                            if (tv_dapanA_amnhac.getText().toString().equals(cauhoi.getDapAn()))
                            {
                                tv_dapanA_amnhac.setBackgroundResource(R.drawable.bg_dapan_amnhac_dung);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        diem = diem + 15;
                                        txthienthidiem.setText(String.valueOf(diem));
                                        txtdiemcong.setText("+ 15");
                                        Handler handler1 = new Handler();
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                txtdiemcong.setText("");
                                            }
                                        },1000);
                                        caudung = caudung + 1;
                                        tv_dapanA_amnhac.setBackgroundResource(R.drawable.bg_dapan_amnhac);

                                        chuyenCauhoi();
                                    }
                                },1000);
                            }
                            else
                            {
                                //nếu đáp án chọn là sai thì sẽ tìm ra câu đúng

                                causai = causai + 1;

                                tv_dapanA_amnhac.setBackgroundResource(R.drawable.bg_dapan_amnhac_sai);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanA_amnhac.setBackgroundResource(R.drawable.bg_dapan_amnhac);
                                    }
                                },1000);


                                Handler handler1 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanA_amnhac.setBackgroundResource(R.drawable.bg_dapan_amnhac);
                                        chuyenCauhoi();
                                    }
                                },1500);
                            }
                        }
                    });

                    tv_dapanB_amnhac.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastime<1500){
                                return;
                            }
                            lastime = SystemClock.elapsedRealtime();
                            if (tv_dapanB_amnhac.getText().toString().equals(cauhoi.getDapAn()))
                            {
                                tv_dapanB_amnhac.setBackgroundResource(R.drawable.bg_dapan_amnhac_dung);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        diem = diem + 15;
                                        txthienthidiem.setText(String.valueOf(diem));
                                        txtdiemcong.setText("+ 15");
                                        Handler handler1 = new Handler();
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                txtdiemcong.setText("");
                                            }
                                        },1000);
                                        caudung = caudung + 1;
                                        tv_dapanB_amnhac.setBackgroundResource(R.drawable.bg_dapan_amnhac);

                                        chuyenCauhoi();
                                    }
                                },1000);
                            }
                            else
                            {
                                //nếu đáp án chọn là sai thì sẽ tìm ra câu đúng

                                causai = causai + 1;

                                tv_dapanB_amnhac.setBackgroundResource(R.drawable.bg_dapan_amnhac_sai);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanB_amnhac.setBackgroundResource(R.drawable.bg_dapan_amnhac);
                                    }
                                },1000);


                                Handler handler1 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanB_amnhac.setBackgroundResource(R.drawable.bg_dapan_amnhac);
                                        chuyenCauhoi();
                                    }
                                },1500);
                            }
                        }

                    });
                    tv_dapanC_amnhac.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastime<1500){
                                return;
                            }
                            lastime = SystemClock.elapsedRealtime();
                            if (tv_dapanC_amnhac.getText().toString().equals(cauhoi.getDapAn()))
                            {
                                tv_dapanC_amnhac.setBackgroundResource(R.drawable.bg_dapan_amnhac_dung);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        diem = diem + 15;
                                        txthienthidiem.setText(String.valueOf(diem));
                                        txtdiemcong.setText("+ 15");
                                        Handler handler1 = new Handler();
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                txtdiemcong.setText("");
                                            }
                                        },1000);
                                        caudung = caudung + 1;
                                        tv_dapanC_amnhac.setBackgroundResource(R.drawable.bg_dapan_amnhac);

                                        chuyenCauhoi();
                                    }
                                },1000);
                            }
                            else
                            {
                                //nếu đáp án chọn là sai thì sẽ tìm ra câu đúng

                                causai = causai + 1;

                                tv_dapanC_amnhac.setBackgroundResource(R.drawable.bg_dapan_amnhac_sai);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanC_amnhac.setBackgroundResource(R.drawable.bg_dapan_amnhac);
                                    }
                                },1000);



                                Handler handler1 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        tv_dapanC_amnhac.setBackgroundResource(R.drawable.bg_dapan_amnhac);
                                        chuyenCauhoi();
                                    }
                                },1500);
                            }

                        }
                    });
                    tv_dapanD_amnhac.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastime<1500){
                                return;
                            }
                            lastime = SystemClock.elapsedRealtime();
                            if (tv_dapanD_amnhac.getText().toString().equals(cauhoi.getDapAn()))
                            {
                                tv_dapanD_amnhac.setBackgroundResource(R.drawable.bg_dapan_amnhac_dung);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        diem = diem + 15;
                                        txthienthidiem.setText(String.valueOf(diem));
                                        txtdiemcong.setText("+ 15");
                                        Handler handler1 = new Handler();
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                txtdiemcong.setText("");
                                            }
                                        },1000);
                                        caudung = caudung + 1;
                                        tv_dapanD_amnhac.setBackgroundResource(R.drawable.bg_dapan_amnhac);

                                        chuyenCauhoi();
                                    }
                                },1000);
                            }
                            else
                            {
                                //nếu đáp án chọn là sai thì sẽ tìm ra câu đúng

                                causai = causai + 1;

                                tv_dapanD_amnhac.setBackgroundResource(R.drawable.bg_dapan_amnhac_sai);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanA_amnhac.setBackgroundResource(R.drawable.bg_dapan_amnhac);
                                    }
                                },1000);

                                Handler handler1 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanD_amnhac.setBackgroundResource(R.drawable.bg_dapan_amnhac);
                                        chuyenCauhoi();
                                    }
                                },1500);
                            }

                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
    public void KetThuc(){
        Intent i =  new Intent(TracNghiemNhac_Easy_Activity.this, KetQuaActivity.class);
        i.putExtra("theloai","Âm Nhạc");
        i.putExtra("chedo","Dễ");
        i.putExtra("diem",String.valueOf(diem));
        i.putExtra("caudung",String.valueOf(caudung));
        i.putExtra("causai",String.valueOf(causai));
        startActivity(i);
        finish();

    }
}