package com.example.duan1.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duan1.LoginScreen.LoginScreen;
import com.example.duan1.MainScreen.MHCActivity;
import com.example.duan1.Model.TaiKhoan;
import com.example.duan1.R;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_Login extends Fragment {
    Button btndangnhap;
    CheckBox ckRemember;
    TextInputEditText edtUser,edtpass;
    LoginScreen context;
    SharedPreferences sharedPreferences;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_login,container,false);
        btndangnhap = root.findViewById(R.id.btnLogin);
        ckRemember = root.findViewById(R.id.remember);
        edtUser = root.findViewById(R.id.edt_user);
        edtpass = root.findViewById(R.id.edt_pass);
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("TaiKhoan");
        return root;
    }
    public  void checkLogin(){
            final String strUser = edtUser.getText().toString();
            final String strPass = edtpass.getText().toString();

            if (strUser.isEmpty()||strPass.isEmpty()){
                Toast.makeText(getContext(), "Vui Lòng Nhập Đầy Đủ !", Toast.LENGTH_SHORT).show();

            }
            else {

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot d:snapshot.getChildren())
                        {
                            final TaiKhoan taiKhoan = d.getValue(TaiKhoan.class);
                            if ((edtUser.getText().toString().equals(taiKhoan.User)) && (edtpass.getText().toString().equals(taiKhoan.Pass))) {
                                Toast.makeText(getContext(), "Đăng Nhập Thành Công !", Toast.LENGTH_SHORT).show();
                                Remember(strUser,strPass,ckRemember.isChecked());
                                Intent i = new Intent(getActivity(), MHCActivity.class);
                                i.putExtra("user_name",taiKhoan.User);
                                startActivity(i);
                            }

                        }


                   }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                /*if (strUser.equals("admin")&&strPass.equals("admin")){
                    Toast.makeText(getContext(), "Đăng Nhập Thành Công !", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), MHCActivity.class);
                    startActivity(i);
                }*/
            }
    }
    public  void Remember(String u , String p , boolean status){
        SharedPreferences pref = this.getActivity().getSharedPreferences("User_remember", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (!status){editor.clear();}
        else {
            editor.putString("USER",u);
            editor.putString("PASS",p);
            editor.putBoolean("REMEMBER",status);
        }
    }




}