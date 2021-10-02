package com.example.duan1.Fragment.DiaLy;

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
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.duan1.Fragment.KetQuaActivity;
import com.example.duan1.Model.CauHoi;
import com.example.duan1.R;
import com.example.duan1.TheLoaiScreen.TheLoaiActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class TracNghiemDiaLy_Easy_Activity extends AppCompatActivity {
    TextView tv_title_dialy,txthienthidiem,txtdiemcong;
    //
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    //
    LottieAnimationView btnhome;
    TextView tv_cauhoi_dialy,tv_dapanA_dialy,tv_dapanB_dialy,tv_dapanC_dialy,tv_dapanD_dialy,tv_count_question;
    int total = 0;
    int diem = 0;
    int caudung = 0;
    int causai = 0;
    private long lastime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trac_nghiem_dia_ly__easy_);
        //ánh xạ giao diện
        txtdiemcong = findViewById(R.id.txtdiemcong_dialy);
        txthienthidiem = findViewById(R.id.txthienthidiem_dialy);
        btnhome = findViewById(R.id.btnhome_dialy);
        tv_count_question = findViewById(R.id.count_question);

        tv_cauhoi_dialy= findViewById(R.id.tv_cauhoi_dialy);
        tv_dapanA_dialy = findViewById(R.id.tv_dapanA_dialy);
        tv_dapanB_dialy =  findViewById(R.id.tv_dapanB_dialy);
        tv_dapanC_dialy = findViewById(R.id.tv_dapanC_dialy);
        tv_dapanD_dialy = findViewById(R.id.tv_dapanD_dialy);

        tv_title_dialy = findViewById(R.id.tvTitle_dialy);
        //animation title
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(tv_title_dialy, PropertyValuesHolder.ofFloat("scaleX",0.9f),
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
                Intent i = new Intent(TracNghiemDiaLy_Easy_Activity.this, TheLoaiActivity.class);
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
            databaseReference = database.getInstance().getReference().child("TheLoai").child("DiaLy").child("De").child(String.valueOf(total));
            tv_count_question.setText(total + "/" + "15");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    final CauHoi cauhoi = snapshot.getValue(CauHoi.class);

                    tv_cauhoi_dialy.setText(cauhoi.getCauHoi());
                    tv_dapanA_dialy.setText(cauhoi.getA());
                    tv_dapanB_dialy.setText(cauhoi.getB());
                    tv_dapanC_dialy.setText(cauhoi.getC());
                    tv_dapanD_dialy.setText(cauhoi.getD());

                    tv_dapanA_dialy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //loi click chuot
                            if (SystemClock.elapsedRealtime() - lastime<1500){
                                return;
                            }
                            lastime = SystemClock.elapsedRealtime();
                            //
                            if (tv_dapanA_dialy.getText().toString().equals(cauhoi.getDapAn()))
                            {
                                tv_dapanA_dialy.setBackgroundResource(R.drawable.bg_dapan_dialy_dung);
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
                                        tv_dapanA_dialy.setBackgroundResource(R.drawable.bg_dapan_dialy);

                                        chuyenCauhoi();
                                    }
                                },1000);
                            }
                            else
                            {
                                //nếu đáp án chọn là sai thì sẽ tìm ra câu đúng

                                causai = causai + 1;

                                tv_dapanA_dialy.setBackgroundResource(R.drawable.bg_dapan_dialy_sai);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanA_dialy.setBackgroundResource(R.drawable.bg_dapan_dialy);
                                    }
                                },1000);


                                Handler handler1 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanA_dialy.setBackgroundResource(R.drawable.bg_dapan_dialy);
                                        chuyenCauhoi();
                                    }
                                },1500);
                            }
                        }
                    });

                    tv_dapanB_dialy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastime<1500){
                                return;
                            }
                            lastime = SystemClock.elapsedRealtime();
                            if (tv_dapanB_dialy.getText().toString().equals(cauhoi.getDapAn()))
                            {
                                tv_dapanB_dialy.setBackgroundResource(R.drawable.bg_dapan_dialy_dung);
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
                                        tv_dapanB_dialy.setBackgroundResource(R.drawable.bg_dapan_dialy);

                                        chuyenCauhoi();
                                    }
                                },1000);
                            }
                            else
                            {
                                //nếu đáp án chọn là sai thì sẽ tìm ra câu đúng

                                causai = causai +1;

                                tv_dapanB_dialy.setBackgroundResource(R.drawable.bg_dapan_dialy_sai);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanB_dialy.setBackgroundResource(R.drawable.bg_dapan_dialy);
                                    }
                                },1000);


                                Handler handler1 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanB_dialy.setBackgroundResource(R.drawable.bg_dapan_dialy);
                                        chuyenCauhoi();
                                    }
                                },1500);
                            }
                        }

                    });
                    tv_dapanC_dialy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastime<1500){
                                return;
                            }
                            lastime = SystemClock.elapsedRealtime();
                            if (tv_dapanC_dialy.getText().toString().equals(cauhoi.getDapAn()))
                            {
                                tv_dapanC_dialy.setBackgroundResource(R.drawable.bg_dapan_dialy_dung);
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
                                        tv_dapanC_dialy.setBackgroundResource(R.drawable.bg_dapan_dialy);

                                        chuyenCauhoi();
                                    }
                                },1000);
                            }
                            else
                            {
                                //nếu đáp án chọn là sai thì sẽ tìm ra câu đúng

                                causai = causai + 1;

                                tv_dapanC_dialy.setBackgroundResource(R.drawable.bg_dapan_dialy_sai);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanC_dialy.setBackgroundResource(R.drawable.bg_dapan_dialy);
                                    }
                                },1000);



                                Handler handler1 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        tv_dapanC_dialy.setBackgroundResource(R.drawable.bg_dapan_dialy);
                                        chuyenCauhoi();
                                    }
                                },1500);
                            }

                        }
                    });
                    tv_dapanD_dialy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastime<1500){
                                return;
                            }
                            lastime = SystemClock.elapsedRealtime();
                            if (tv_dapanD_dialy.getText().toString().equals(cauhoi.getDapAn()))
                            {
                                tv_dapanD_dialy.setBackgroundResource(R.drawable.bg_dapan_dialy_dung);
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
                                        tv_dapanD_dialy.setBackgroundResource(R.drawable.bg_dapan_dialy);

                                        chuyenCauhoi();
                                    }
                                },1000);
                            }
                            else
                            {
                                //nếu đáp án chọn là sai thì sẽ tìm ra câu đúng

                                causai = causai + 1;

                                tv_dapanD_dialy.setBackgroundResource(R.drawable.bg_dapan_dialy_sai);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanA_dialy.setBackgroundResource(R.drawable.bg_dapan_dialy);
                                    }
                                },1000);

                                Handler handler1 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanD_dialy.setBackgroundResource(R.drawable.bg_dapan_dialy);
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
        Intent i =  new Intent(TracNghiemDiaLy_Easy_Activity.this, KetQuaActivity.class);
        i.putExtra("theloai","Địa Lý");
        i.putExtra("chedo","Dễ");

        i.putExtra("diem",String.valueOf(diem));
        i.putExtra("caudung",String.valueOf(caudung));
        i.putExtra("causai",String.valueOf(causai));


        startActivity(i);
        finish();

    }
}