package com.example.duan1.Fragment.Game;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.duan1.Fragment.DoMeo.TracNghiemDoMeo_Easy_Activity;
import com.example.duan1.Fragment.KetQuaActivity;
import com.example.duan1.Model.CauHoi;
import com.example.duan1.R;
import com.example.duan1.TheLoaiScreen.TheLoaiActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class TracNghiemGame_Easy_Activity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ImageView img_game;
    LinearLayout linearLayout;
    LottieAnimationView btnhome;
    LinearLayout.LayoutParams lay = new LinearLayout.LayoutParams(100,100);
    TextView tv_title_game, tv_cauhoi_game,tv_dapanA_game,tv_dapanB_game,tv_dapanC_game,tv_dapanD_game,tv_count_question,txthienthidiem,txtdiemcong;
    int total = 0;
    int diem = 0;
    int caudung = 0;
    int causai = 0;
    private long lastime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trac_nghiem_game_easy);
        //ánh xạ giao diện
        txtdiemcong = findViewById(R.id.txtdiemcong_game_easy);
        txthienthidiem = findViewById(R.id.txthienthidiem_game_easy);
        btnhome = findViewById(R.id.btnhome_game_easy);
        linearLayout = findViewById(R.id.layout1);
        img_game = findViewById(R.id.img_cauhoi);
        tv_count_question = findViewById(R.id.count_question_game);
        tv_cauhoi_game= findViewById(R.id.tv_cauhoi_game);
        tv_dapanA_game = findViewById(R.id.tv_dapanA_game);
        tv_dapanB_game= findViewById(R.id.tv_dapanB_game);
        tv_dapanC_game = findViewById(R.id.tv_dapanC_game);
        tv_dapanD_game = findViewById(R.id.tv_dapanD_game);
        tv_title_game = findViewById(R.id.tvTitle_game);

        chuyenCauhoi();
        //animation title
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(tv_title_game, PropertyValuesHolder.ofFloat("scaleX",0.9f),
                PropertyValuesHolder.ofFloat("scaleY",0.9f));
        objectAnimator.setDuration(500);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();
        //btnhome
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TracNghiemGame_Easy_Activity.this, TheLoaiActivity.class);
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
            databaseReference = database.getInstance().getReference().child("TheLoai").child("Game").child("De").child(String.valueOf(total));
            tv_count_question.setText(total + "/" + "15");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    final CauHoi cauhoi = snapshot.getValue(CauHoi.class);

                    Picasso.get().load(cauhoi.getImage()).into(img_game);
                    /*img_game.setMinimumHeight(100);
                    img_game.setMinimumWidth(100);
                    img_game.setMaxHeight(100);
                    img_game.setMaxWidth(100);*/
                    //img_game.setLayoutParams(lay);
                    //linearLayout.addView(img_game);


                    tv_cauhoi_game.setText(cauhoi.getCauHoi());
                    tv_dapanA_game.setText(cauhoi.getA());
                    tv_dapanB_game.setText(cauhoi.getB());
                    tv_dapanC_game.setText(cauhoi.getC());
                    tv_dapanD_game.setText(cauhoi.getD());

                    tv_dapanA_game.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastime<1500){
                                return;
                            }
                            lastime = SystemClock.elapsedRealtime();
                            if (tv_dapanA_game.getText().toString().equals(cauhoi.getDapAn()))
                            {
                                tv_dapanA_game.setBackgroundResource(R.drawable.bg_dapan_game_dung);
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
                                        tv_dapanA_game.setBackgroundResource(R.drawable.bg_dapan_game);

                                        chuyenCauhoi();
                                    }
                                },1000);
                            }
                            else
                            {
                                //nếu đáp án chọn là sai thì sẽ tìm ra câu đúng

                                causai = causai + 1;

                                tv_dapanA_game.setBackgroundResource(R.drawable.bg_dapan_game_sai);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanA_game.setBackgroundResource(R.drawable.bg_dapan_game);
                                    }
                                },1000);


                                Handler handler1 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanA_game.setBackgroundResource(R.drawable.bg_dapan_game);
                                        chuyenCauhoi();
                                        tv_dapanA_game.setEnabled(true);
                                    }
                                },1500);
                            }
                        }
                    });

                    tv_dapanB_game.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastime<1500){
                                return;
                            }
                            lastime = SystemClock.elapsedRealtime();
                            if (tv_dapanB_game.getText().toString().equals(cauhoi.getDapAn()))
                            {
                                tv_dapanB_game.setBackgroundResource(R.drawable.bg_dapan_game_dung);
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
                                        tv_dapanB_game.setBackgroundResource(R.drawable.bg_dapan_game);

                                        chuyenCauhoi();
                                    }
                                },1000);
                            }
                            else
                            {
                                //nếu đáp án chọn là sai thì sẽ tìm ra câu đúng

                                causai = causai + 1;

                                tv_dapanB_game.setBackgroundResource(R.drawable.bg_dapan_game_sai);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanB_game.setBackgroundResource(R.drawable.bg_dapan_game);
                                    }
                                },1000);


                                Handler handler1 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanB_game.setBackgroundResource(R.drawable.bg_dapan_game);
                                        chuyenCauhoi();
                                    }
                                },1500);
                            }
                        }

                    });
                    tv_dapanC_game.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastime<1500){
                                return;
                            }
                            lastime = SystemClock.elapsedRealtime();
                            if (tv_dapanC_game.getText().toString().equals(cauhoi.getDapAn()))
                            {
                                tv_dapanC_game.setBackgroundResource(R.drawable.bg_dapan_game_dung);
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
                                        tv_dapanC_game.setBackgroundResource(R.drawable.bg_dapan_game);

                                        chuyenCauhoi();
                                    }
                                },1000);
                            }
                            else
                            {
                                //nếu đáp án chọn là sai thì sẽ tìm ra câu đúng

                                causai = causai + 1;

                                tv_dapanC_game.setBackgroundResource(R.drawable.bg_dapan_game_sai);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanC_game.setBackgroundResource(R.drawable.bg_dapan_game);
                                    }
                                },1000);



                                Handler handler1 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        tv_dapanC_game.setBackgroundResource(R.drawable.bg_dapan_game);
                                        chuyenCauhoi();
                                    }
                                },1500);
                            }

                        }
                    });
                    tv_dapanD_game.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastime<1500){
                                return;
                            }
                            lastime = SystemClock.elapsedRealtime();
                            if (tv_dapanD_game.getText().toString().equals(cauhoi.getDapAn()))
                            {
                                tv_dapanD_game.setBackgroundResource(R.drawable.bg_dapan_game_dung);
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
                                        tv_dapanD_game.setBackgroundResource(R.drawable.bg_dapan_game);

                                        chuyenCauhoi();
                                    }
                                },1000);
                            }
                            else
                            {
                                //nếu đáp án chọn là sai thì sẽ tìm ra câu đúng

                                causai = causai + 1;

                                tv_dapanD_game.setBackgroundResource(R.drawable.bg_dapan_game_sai);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanA_game.setBackgroundResource(R.drawable.bg_dapan_game);
                                    }
                                },1000);

                                Handler handler1 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_dapanD_game.setBackgroundResource(R.drawable.bg_dapan_game);
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
        Intent i =  new Intent(TracNghiemGame_Easy_Activity.this, KetQuaActivity.class);
        i.putExtra("theloai","Game");
        i.putExtra("chedo","Dễ");
        i.putExtra("diem",String.valueOf(diem));
        i.putExtra("caudung",String.valueOf(caudung));
        i.putExtra("causai",String.valueOf(causai));
        startActivity(i);
        finish();

    }
}