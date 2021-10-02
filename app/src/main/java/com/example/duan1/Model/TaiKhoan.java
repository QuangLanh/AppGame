package com.example.duan1.Model;

public class TaiKhoan {
    public String User;
    public String Pass;
    public  String key;
    public  int diem1,diem2,diem3;

    public TaiKhoan(String user, String pass, String key, int diem1, int diem2, int diem3) {
        User = user;
        Pass = pass;
        this.key = key;
        this.diem1 = diem1;
        this.diem2 = diem2;
        this.diem3 = diem3;
    }

    public TaiKhoan() {
    }
}
