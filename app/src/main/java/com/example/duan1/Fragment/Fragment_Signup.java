package com.example.duan1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duan1.LoginScreen.LoginScreen;
import com.example.duan1.Model.TaiKhoan;
import com.example.duan1.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment_Signup extends Fragment {
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    TextInputEditText edt_dk_User , edt_dk_Pass;
    Button btnSignup;
    String user , pass;
    int total = 0;
    TaiKhoan dstaikhoan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_registration,container,false);
        edt_dk_User = root.findViewById(R.id.edt_dk_user);
        edt_dk_Pass = root.findViewById(R.id.edt_dk_pass);
        btnSignup = root.findViewById(R.id.btnSignup);

        dstaikhoan = new TaiKhoan();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("TaiKhoan");

        final String user = edt_dk_User.getText().toString();
        final String pass = edt_dk_Pass.getText().toString();

        if (user.isEmpty())
        {
            edt_dk_User.setError("Tên Tài Khoản bị trống !");
            edt_dk_User.requestFocus();
        }
        if (pass.isEmpty())
        {
            edt_dk_Pass.setError("Mật Khẩu bị trống !");
            edt_dk_Pass.requestFocus();
        }
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
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_dk_User.getText().toString().isEmpty()||edt_dk_Pass.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(), "Vui Lòng Nhập Đầy Đủ Thông Tin !", Toast.LENGTH_SHORT).show();
                }
                else {
                    dstaikhoan.User = edt_dk_User.getText().toString();
                    dstaikhoan.Pass = edt_dk_Pass.getText().toString();
                    databaseReference.child(String.valueOf(total + 1)).setValue(dstaikhoan);
                    edt_dk_User.setText("");
                    edt_dk_Pass.setText("");
                    Toast.makeText(getActivity(), "Tạo Tài Khoản Thành Công !", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return root;
    }

}
