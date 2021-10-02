package com.example.duan1.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.duan1.Model.TaiKhoan;
import com.example.duan1.Model.XepHangDiem;
import com.example.duan1.R;
import com.example.duan1.TheLoaiScreen.TheLoaiActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;

public class KetQuaActivity extends AppCompatActivity {
    TextView tv_count_dung,tv_count_sai,tv_count_diem;
    LottieAnimationView btn_home;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    int diem1 = 0 ;
    XepHangDiem xepHangDiem1;
    int total = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_qua);
        //ánh xạ
        tv_count_dung = findViewById(R.id.tv_count_caudung);
        tv_count_sai = findViewById(R.id.tv_count_causai);
        tv_count_diem = findViewById(R.id.tv_count_diem);
        btn_home = findViewById(R.id.btn_home);
        //lấy extra
        Intent i = getIntent();
        final String theloai = i.getStringExtra("theloai");
        final String chedo = i.getStringExtra("chedo");
        final String diem = i.getStringExtra("diem");
        String caudung = i.getStringExtra("caudung");
        String causai = i.getStringExtra("causai");
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("getDiem");
        //get SharedPreferences
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        final String user_name = pref.getString("USERNAME","");
        Toast.makeText(this, "xin chào "+ user_name, Toast.LENGTH_SHORT).show();

        xepHangDiem1 = new XepHangDiem();
        tv_count_diem.setText(diem);
        tv_count_dung.setText(caudung);
        tv_count_sai.setText(causai);
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    total = (int) snapshot.getChildrenCount();
                }
                else {}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                xepHangDiem1.diem1 = diem;
                xepHangDiem1.User = user_name;
                xepHangDiem1.Tloai = theloai;
                xepHangDiem1.chedo = chedo;
                databaseReference.child(String.valueOf(total+1)).setValue(xepHangDiem1);

                Intent i1 = new Intent(KetQuaActivity.this, TheLoaiActivity.class);
                i1.putExtra("diem",diem);
                startActivity(i1);
                finish();
            }
        });


    }
}