package com.example.duan1.Fragment.DoMeo;

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
import com.example.duan1.Fragment.DiaLy.TracNghiemDiaLy_Hard_Activity;
import com.example.duan1.Fragment.KetQuaActivity;
import com.example.duan1.Model.CauHoi;
import com.example.duan1.R;
import com.example.duan1.TheLoaiScreen.TheLoaiActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TracNghiemDoMeo_Easy_Activity extends AppCompatActivity {
    TextView tv_title_domeo,txthienthidiem,txtdiemcong;
    LottieAnimationView btnhome;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ImageView img_game;
    TextView tvTitle_domeo,tv_cauhoi_domeo,tv_dapanA_domeo,tv_dapanB_domeo,tv_dapanC_domeo,tv_dapanD_domeo,tv_count_question;
    int total = 0;
    int diem = 0;
    int caudung = 0;
    int causai = 0;
    private long lastime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trac_nghiem_do_meo__easy_);
        //ánh xạ giao diện
        txtdiemcong = findViewById(R.id.txtdiemcong_domeo_easy);
        txthienthidiem = findViewById(R.id.txthienthidiem_domeo_easy);
        btnhome = findViewById(R.id.btnhome_domeo_easy);
        tv_count_question = findViewById(R.id.count_question_domeo);
        tv_title_domeo = findViewById(R.id.tvTitle_domeo);
        tv_cauhoi_domeo= findViewById(R.id.tv_cauhoi_domeo);
        tv_dapanA_domeo = findViewById(R.id.tv_dapanA_domeo);
        tv_dapanB_domeo= findViewById(R.id.tv_dapanB_domeo);
        tv_dapanC_domeo = findViewById(R.id.tv_dapanC_domeo);
        tv_dapanD_domeo = findViewById(R.id.tv_dapanD_domeo);
        tv_title_domeo = findViewById(R.id.tvTitle_domeo);
        //animation title
        chuyenCauhoi();
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(tv_title_domeo, PropertyValuesHolder.ofFloat("scaleX",0.9f),
                PropertyValuesHolder.ofFloat("scaleY",0.9f));
        objectAnimator.setDuration(500);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();
        //btnhome
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TracNghiemDoMeo_Easy_Activity.this, TheLoaiActivity.class);
                startActivity(i);
                finish();
            }
        });
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
            databaseReference = database.getInstance().getReference().child("TheLoai").child("DoMeo").child("De").child(String.valueOf(total));
            tv_count_question.setText(total + "/" + "15");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    final CauHoi cauhoi = snapshot.getValue(CauHoi.class);

                    tv_cauhoi_domeo.setText(cauhoi.getCauHoi());
                    tv_dapanA_domeo.setText(cauhoi.getA());
                    tv_dapanB_domeo.setText(cauhoi.getB());
                    tv_dapanC_domeo.setText(cauhoi.getC());
                    tv_dapanD_domeo.setText(cauhoi.getD());

                    tv_dapanA_domeo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastime<1500){
                                return;
                            }
                            lastime = SystemClock.elapsedRealtime();
                            if (tv_dapanA_domeo.getText().toString().equals(cauhoi.getDapAn()))
                            {
                                tv_dapanA_domeo.setBackgroundResource(R.drawable.bg_dapan_domeo_dung);
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
                                        tv_dapanA_domeo.setBackgroundResource(R.drawable.bg_dapan_domeo);

                                        chuyenCauhoi();
                                    }
                                },1000);
                            }
                            else
                            {
                                //nếu đáp án chọn là sai thì sẽ tìm ra câu đúng

                                causai = causai + 1;

                                tv_dapanA_domeo.setBackgroundResource(R.drawable.bg_dapan_domeo_sai);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanA_domeo.setBackgroundResource(R.drawable.bg_dapan_domeo);
                                    }
                                },1000);


                                Handler handler1 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanA_domeo.setBackgroundResource(R.drawable.bg_dapan_domeo);
                                        chuyenCauhoi();
                                    }
                                },1500);
                            }
                        }
                    });

                    tv_dapanB_domeo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastime<1500){
                                return;
                            }
                            lastime = SystemClock.elapsedRealtime();
                            if (tv_dapanB_domeo.getText().toString().equals(cauhoi.getDapAn()))
                            {
                                tv_dapanB_domeo.setBackgroundResource(R.drawable.bg_dapan_domeo_dung);
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
                                        tv_dapanB_domeo.setBackgroundResource(R.drawable.bg_dapan_domeo);

                                        chuyenCauhoi();
                                    }
                                },1000);
                            }
                            else
                            {
                                //nếu đáp án chọn là sai thì sẽ tìm ra câu đúng

                                causai = causai + 1;

                                tv_dapanB_domeo.setBackgroundResource(R.drawable.bg_dapan_domeo_sai);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanB_domeo.setBackgroundResource(R.drawable.bg_dapan_domeo);
                                    }
                                },1000);


                                Handler handler1 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanB_domeo.setBackgroundResource(R.drawable.bg_dapan_domeo);
                                        chuyenCauhoi();
                                    }
                                },1500);
                            }
                        }

                    });
                    tv_dapanC_domeo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastime<1500){
                                return;
                            }
                            lastime = SystemClock.elapsedRealtime();
                            if (tv_dapanC_domeo.getText().toString().equals(cauhoi.getDapAn()))
                            {
                                tv_dapanC_domeo.setBackgroundResource(R.drawable.bg_dapan_domeo_dung);
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
                                        tv_dapanC_domeo.setBackgroundResource(R.drawable.bg_dapan_domeo);

                                        chuyenCauhoi();
                                    }
                                },1000);
                            }
                            else
                            {
                                //nếu đáp án chọn là sai thì sẽ tìm ra câu đúng

                                causai = causai + 1;

                                tv_dapanC_domeo.setBackgroundResource(R.drawable.bg_dapan_domeo_sai);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanC_domeo.setBackgroundResource(R.drawable.bg_dapan_domeo);
                                    }
                                },1000);



                                Handler handler1 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        tv_dapanC_domeo.setBackgroundResource(R.drawable.bg_dapan_domeo);
                                        chuyenCauhoi();
                                    }
                                },1500);
                            }

                        }
                    });
                    tv_dapanD_domeo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastime<1500){
                                return;
                            }
                            lastime = SystemClock.elapsedRealtime();
                            if (tv_dapanD_domeo.getText().toString().equals(cauhoi.getDapAn()))
                            {
                                tv_dapanD_domeo.setBackgroundResource(R.drawable.bg_dapan_domeo_dung);
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
                                        tv_dapanD_domeo.setBackgroundResource(R.drawable.bg_dapan_domeo);

                                        chuyenCauhoi();
                                    }
                                },1000);
                            }
                            else
                            {
                                //nếu đáp án chọn là sai thì sẽ tìm ra câu đúng

                                causai = causai + 1;

                                tv_dapanD_domeo.setBackgroundResource(R.drawable.bg_dapan_domeo_sai);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanA_domeo.setBackgroundResource(R.drawable.bg_dapan_domeo);
                                    }
                                },1000);

                                Handler handler1 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanD_domeo.setBackgroundResource(R.drawable.bg_dapan_domeo);
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
        Intent i =  new Intent(TracNghiemDoMeo_Easy_Activity.this, KetQuaActivity.class);
        i.putExtra("theloai","Đố Mẹo");
        i.putExtra("chedo","Dễ");
        i.putExtra("diem",String.valueOf(diem));
        i.putExtra("caudung",String.valueOf(caudung));
        i.putExtra("causai",String.valueOf(causai));
        startActivity(i);
        finish();

    }
}