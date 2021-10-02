package com.example.duan1.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.duan1.Model.XepHangDiem;
import com.example.duan1.R;
import com.example.duan1.adapter.RankAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RankActivity<n> extends AppCompatActivity {
    DatabaseReference databaseReference,databaseReference1;
    FirebaseDatabase database,database1;
    TextView txt_user_rank, txt_score_tank;
    ArrayList<XepHangDiem> dsrank;
    RankAdapter adapter;
    String user_name;
    XepHangDiem xepHangDiem1;
    ListView lv_ranker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        lv_ranker = findViewById(R.id.lv_score);
        txt_user_rank = findViewById(R.id.txt_user_rank);
        txt_score_tank = findViewById(R.id.txt_score_rank);

        //get Username References user
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        user_name = pref.getString("USERNAME", "");
        txt_user_rank.setText(user_name);
        //listview
        dsrank = new ArrayList<XepHangDiem>();
        database1 = FirebaseDatabase.getInstance();
        databaseReference1 = database1.getReference().child("getDiem");
        adapter = new RankAdapter(RankActivity.this,dsrank);
        lv_ranker.setAdapter(adapter);
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d:snapshot.getChildren())
                {
                    xepHangDiem1 = d.getValue(XepHangDiem.class);
                    xepHangDiem1.key =d.getKey();
                    dsrank.add(xepHangDiem1);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        getDiem();

    }
    public void getDiem(){
        //get điểm
        final int[] scoretam = null;
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("getDiem");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    final XepHangDiem xepHangDiem = d.getValue(XepHangDiem.class);
                    if (user_name.equals(xepHangDiem.User))
                    {
                        int diemtam = Integer.parseInt(xepHangDiem.diem1);
                        txt_score_tank.setText(String.valueOf(Integer.parseInt(txt_score_tank.getText().toString())+diemtam));

                    }

                }

                /*for (int num=0;num<scoretam.length ; num++)
                {
                    sumscore = sumscore + scoretam[num];
                }
                txt_score_tank.setText(String.valueOf(sumscore));*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    }

