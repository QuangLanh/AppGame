package com.example.duan1.TheLoaiScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.duan1.Fragment.DiaLy.TracNghiemDiaLy_Easy_Activity;
import com.example.duan1.Fragment.DiaLy.TracNghiemDiaLy_Hard_Activity;
import com.example.duan1.Fragment.DoMeo.TracNghiemDoMeo_Hard_Activity;
import com.example.duan1.Fragment.Game.TracNghiemGame_Hard_Activity;
import com.example.duan1.Fragment.Nhac.TracNghiemNhac_Hard_Activity;
import com.example.duan1.Fragment.PhimAnh.TracNghiemPhimAnh_Hard_Activity;
import com.example.duan1.Fragment.TheThao.TracNghiemTheThao_Hard_Activity;
import com.example.duan1.Fragment.DoMeo.TracNghiemDoMeo_Easy_Activity;
import com.example.duan1.Fragment.Game.TracNghiemGame_Easy_Activity;
import com.example.duan1.Fragment.Nhac.TracNghiemNhac_Easy_Activity;
import com.example.duan1.Fragment.PhimAnh.TracNghiemPhimAnh_Easy_Activity;
import com.example.duan1.Fragment.TheThao.TracNghiemTheThao_Easy_Activity;
import com.example.duan1.R;

public class TheLoaiActivity extends AppCompatActivity {
    Dialog dialog;
    Button btnDe,btnKho,btnTheThao,btnGame,btnAmNhac,btnPhim,btnDoMeo,btnDiaLy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_loai);
        //Ánh Xạ giao diện
        btnDe = findViewById(R.id.btnDe);
        btnKho = findViewById(R.id.btnKho);
        btnTheThao = findViewById(R.id.btnTheThao);
        btnGame = findViewById(R.id.btnGame);
        btnAmNhac = findViewById(R.id.btnAmNhac);
        btnPhim = findViewById(R.id.btnPhim);
        btnDoMeo = findViewById(R.id.btnDoMeo);
        btnDiaLy = findViewById(R.id.btnDiaLy);


        btnTheThao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogThethao(TheLoaiActivity.this);

            }
        });

        btnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogGame(TheLoaiActivity.this);

            }
        });
        btnAmNhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAmNhac(TheLoaiActivity.this);

            }
        });
        btnPhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogPhimAnh(TheLoaiActivity.this);

            }
        });
        btnDoMeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogDoMeo(TheLoaiActivity.this);

            }
        });
        btnDiaLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogDiaLy(TheLoaiActivity.this);

            }
        });

    }
    protected void openDialogThethao(final Context context)
    {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_dialog__che_do);
        btnDe = dialog.findViewById(R.id.btnDe);
        btnKho = dialog.findViewById(R.id.btnKho);
        dialog.show();
        btnDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TheLoaiActivity.this, TracNghiemTheThao_Easy_Activity.class);
                startActivity(i);
                dialog.dismiss();
            }
        });
        btnKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TheLoaiActivity.this, TracNghiemTheThao_Hard_Activity.class);
                startActivity(i);
                dialog.dismiss();
            }
        });
    }

    protected void openDialogGame(final Context context)
    {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_dialog__che_do);
        btnDe = dialog.findViewById(R.id.btnDe);
        btnKho = dialog.findViewById(R.id.btnKho);
        dialog.show();
        btnDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TheLoaiActivity.this, TracNghiemGame_Easy_Activity.class);
                startActivity(i);
                dialog.dismiss();
            }
        });
        btnKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TheLoaiActivity.this, TracNghiemGame_Hard_Activity.class);
                startActivity(i);
                dialog.dismiss();
            }
        });
    }

    protected void openDialogAmNhac(final Context context)
    {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_dialog__che_do);
        btnDe = dialog.findViewById(R.id.btnDe);
        btnKho = dialog.findViewById(R.id.btnKho);
        dialog.show();
        btnDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TheLoaiActivity.this, TracNghiemNhac_Easy_Activity.class);
                startActivity(i);
                dialog.dismiss();
            }
        });
        btnKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TheLoaiActivity.this, TracNghiemNhac_Hard_Activity.class);
                startActivity(i);
                dialog.dismiss();
            }
        });
    }

    protected void openDialogPhimAnh(final Context context)
    {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_dialog__che_do);
        btnDe = dialog.findViewById(R.id.btnDe);
        btnKho = dialog.findViewById(R.id.btnKho);
        dialog.show();
        btnDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TheLoaiActivity.this, TracNghiemPhimAnh_Easy_Activity.class);
                startActivity(i);
                dialog.dismiss();
            }
        });
        btnKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TheLoaiActivity.this, TracNghiemPhimAnh_Hard_Activity.class);
                startActivity(i);
                dialog.dismiss();
            }
        });
    }

    protected void openDialogDoMeo(final Context context)
    {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_dialog__che_do);
        btnDe = dialog.findViewById(R.id.btnDe);
        btnKho = dialog.findViewById(R.id.btnKho);
        dialog.show();
        btnDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TheLoaiActivity.this, TracNghiemDoMeo_Easy_Activity.class);
                startActivity(i);
                dialog.dismiss();
            }
        });
        btnKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TheLoaiActivity.this, TracNghiemDoMeo_Hard_Activity.class);
                startActivity(i);
                dialog.dismiss();
            }
        });
    }

    protected void openDialogDiaLy(final Context context)
    {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_dialog__che_do);
        btnDe = dialog.findViewById(R.id.btnDe);
        btnKho = dialog.findViewById(R.id.btnKho);
        dialog.show();
        btnDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TheLoaiActivity.this, TracNghiemDiaLy_Easy_Activity.class);
                startActivity(i);
                dialog.dismiss();
            }
        });
        btnKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TheLoaiActivity.this, TracNghiemDiaLy_Hard_Activity.class);
                startActivity(i);
                dialog.dismiss();
            }
        });
    }

}