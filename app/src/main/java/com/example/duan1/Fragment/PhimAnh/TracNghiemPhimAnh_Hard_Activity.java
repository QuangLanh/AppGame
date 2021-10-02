package com.example.duan1.Fragment.PhimAnh;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.duan1.Fragment.KetQuaActivity;
import com.example.duan1.Fragment.Nhac.TracNghiemNhac_Hard_Activity;
import com.example.duan1.Model.CauHoi;
import com.example.duan1.R;
import com.example.duan1.TheLoaiScreen.TheLoaiActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TracNghiemPhimAnh_Hard_Activity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    LottieAnimationView btnhome;
    TextView tvTitle_phimanh,tv_cauhoi_phimanh,tv_dapanA_phimanh,tv_dapanB_phimanh,tv_dapanC_phimanh,tv_dapanD_phimanh,tv_count_question,txthienthidiem,txtdiemcong;
    int total = 0;
    int diem = 0;
    int caudung = 0;
    int causai = 0;
    private long lastime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trac_nghiem_phim_anh__hard_);
        //ánh xạ giao diện
        txtdiemcong = findViewById(R.id.txtdiemcong_phim_hard);
        txthienthidiem = findViewById(R.id.txthienthidiem_phim_hard);
        btnhome = findViewById(R.id.btnhome_phim_hard);
        tv_count_question = findViewById(R.id.count_question_phim);
        tvTitle_phimanh = findViewById(R.id.tvTitle_phimanh_hard);
        tv_cauhoi_phimanh= findViewById(R.id.tv_cauhoi_phimanh_hard);
        tv_dapanA_phimanh = findViewById(R.id.tv_dapanA_phimanh_hard);
        tv_dapanB_phimanh= findViewById(R.id.tv_dapanB_phimanh_hard);
        tv_dapanC_phimanh = findViewById(R.id.tv_dapanC_phimanh_hard);
        tv_dapanD_phimanh = findViewById(R.id.tv_dapanD_phimanh_hard);
        tvTitle_phimanh = findViewById(R.id.tvTitle_phimanh_hard);
        //animation title
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(tvTitle_phimanh, PropertyValuesHolder.ofFloat("scaleX",0.9f),
                PropertyValuesHolder.ofFloat("scaleY",0.9f));
        objectAnimator.setDuration(500);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();
        chuyenCauhoi();
        //btnhome
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TracNghiemPhimAnh_Hard_Activity.this, TheLoaiActivity.class);
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
            databaseReference = database.getInstance().getReference().child("TheLoai").child("PhimAnh").child("Kho").child(String.valueOf(total));
            tv_count_question.setText(total + "/" + "15");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    final CauHoi cauhoi = snapshot.getValue(CauHoi.class);

                    tv_cauhoi_phimanh.setText(cauhoi.getCauHoi());
                    tv_dapanA_phimanh.setText(cauhoi.getA());
                    tv_dapanB_phimanh.setText(cauhoi.getB());
                    tv_dapanC_phimanh.setText(cauhoi.getC());
                    tv_dapanD_phimanh.setText(cauhoi.getD());

                    tv_dapanA_phimanh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastime<1500){
                                return;
                            }
                            lastime = SystemClock.elapsedRealtime();
                            if (tv_dapanA_phimanh.getText().toString().equals(cauhoi.getDapAn()))
                            {
                                tv_dapanA_phimanh.setBackgroundResource(R.drawable.bg_dapan_phimanh_dung);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        diem = diem + 20;
                                        txthienthidiem.setText(String.valueOf(diem));
                                        txtdiemcong.setText("+ 20");
                                        Handler handler1 = new Handler();
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                txtdiemcong.setText("");
                                            }
                                        },1000);
                                        caudung = caudung + 1;
                                        tv_dapanA_phimanh.setBackgroundResource(R.drawable.bg_dapan_phimanh);

                                        chuyenCauhoi();
                                    }
                                },1000);
                            }
                            else
                            {
                                //nếu đáp án chọn là sai thì sẽ tìm ra câu đúng

                                causai = causai + 1;

                                tv_dapanA_phimanh.setBackgroundResource(R.drawable.bg_dapan_phimanh_sai);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanA_phimanh.setBackgroundResource(R.drawable.bg_dapan_phimanh);
                                    }
                                },1000);


                                Handler handler1 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanA_phimanh.setBackgroundResource(R.drawable.bg_dapan_phimanh);
                                        chuyenCauhoi();
                                    }
                                },1500);
                            }
                        }
                    });

                    tv_dapanB_phimanh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastime<1500){
                                return;
                            }
                            lastime = SystemClock.elapsedRealtime();
                            if (tv_dapanB_phimanh.getText().toString().equals(cauhoi.getDapAn()))
                            {
                                tv_dapanB_phimanh.setBackgroundResource(R.drawable.bg_dapan_phimanh_dung);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        diem = diem + 20;
                                        txthienthidiem.setText(String.valueOf(diem));
                                        txtdiemcong.setText("+ 20");
                                        Handler handler1 = new Handler();
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                txtdiemcong.setText("");
                                            }
                                        },1000);
                                        caudung = caudung + 1;
                                        tv_dapanB_phimanh.setBackgroundResource(R.drawable.bg_dapan_phimanh);

                                        chuyenCauhoi();
                                    }
                                },1000);
                            }
                            else
                            {
                                //nếu đáp án chọn là sai thì sẽ tìm ra câu đúng

                                causai = causai + 1;

                                tv_dapanB_phimanh.setBackgroundResource(R.drawable.bg_dapan_phimanh_sai);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanB_phimanh.setBackgroundResource(R.drawable.bg_dapan_phimanh);
                                    }
                                },1000);


                                Handler handler1 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanB_phimanh.setBackgroundResource(R.drawable.bg_dapan_game);
                                        chuyenCauhoi();
                                    }
                                },1500);
                            }
                        }

                    });
                    tv_dapanC_phimanh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastime<1500){
                                return;
                            }
                            lastime = SystemClock.elapsedRealtime();
                            if (tv_dapanC_phimanh.getText().toString().equals(cauhoi.getDapAn()))
                            {
                                tv_dapanC_phimanh.setBackgroundResource(R.drawable.bg_dapan_phimanh_dung);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        diem = diem + 20;
                                        txthienthidiem.setText(String.valueOf(diem));
                                        txtdiemcong.setText("+ 20");
                                        Handler handler1 = new Handler();
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                txtdiemcong.setText("");
                                            }
                                        },1000);
                                        caudung = caudung + 1;
                                        tv_dapanC_phimanh.setBackgroundResource(R.drawable.bg_dapan_phimanh);

                                        chuyenCauhoi();
                                    }
                                },1000);
                            }
                            else
                            {
                                //nếu đáp án chọn là sai thì sẽ tìm ra câu đúng

                                causai = causai + 1;

                                tv_dapanC_phimanh.setBackgroundResource(R.drawable.bg_dapan_phimanh_sai);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanC_phimanh.setBackgroundResource(R.drawable.bg_dapan_phimanh);
                                    }
                                },1000);



                                Handler handler1 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        tv_dapanC_phimanh.setBackgroundResource(R.drawable.bg_dapan_phimanh);
                                        chuyenCauhoi();
                                    }
                                },1500);
                            }

                        }
                    });
                    tv_dapanD_phimanh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastime<1500){
                                return;
                            }
                            lastime = SystemClock.elapsedRealtime();
                            if (tv_dapanD_phimanh.getText().toString().equals(cauhoi.getDapAn()))
                            {
                                tv_dapanD_phimanh.setBackgroundResource(R.drawable.bg_dapan_phimanh_dung);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        diem = diem + 20;
                                        txthienthidiem.setText(String.valueOf(diem));
                                        txtdiemcong.setText("+ 20");
                                        Handler handler1 = new Handler();
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                txtdiemcong.setText("");
                                            }
                                        },1000);
                                        caudung = caudung + 1;
                                        tv_dapanD_phimanh.setBackgroundResource(R.drawable.bg_dapan_phimanh);

                                        chuyenCauhoi();
                                    }
                                },1000);
                            }
                            else
                            {
                                //nếu đáp án chọn là sai thì sẽ tìm ra câu đúng

                                causai = causai + 1;

                                tv_dapanD_phimanh.setBackgroundResource(R.drawable.bg_dapan_phimanh_sai);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanA_phimanh.setBackgroundResource(R.drawable.bg_dapan_phimanh);
                                    }
                                },1000);

                                Handler handler1 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanD_phimanh.setBackgroundResource(R.drawable.bg_dapan_phimanh);
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
        Intent i =  new Intent(TracNghiemPhimAnh_Hard_Activity.this, KetQuaActivity.class);
        i.putExtra("theloai","Phim Ảnh");
        i.putExtra("chedo","Khó");
        i.putExtra("diem",String.valueOf(diem));
        i.putExtra("caudung",String.valueOf(caudung));
        i.putExtra("causai",String.valueOf(causai));
        startActivity(i);
        finish();

    }
}