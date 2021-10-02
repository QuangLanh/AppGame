package com.example.duan1.Model;

import android.provider.ContactsContract;

public class CauHoi
{
    private String CauHoi;
    private String A,B,C,D;
    private String DapAn;
    private  String Image;

    public String getImage() {
        return Image;
    }

    public String getCauHoi() {
        return CauHoi;
    }

    public String getA() {
        return A;
    }

    public String getB() {
        return B;
    }

    public String getC() {
        return C;
    }

    public String getD() {
        return D;
    }

    public String getDapAn() {
        return DapAn;
    }

    public CauHoi(String cauHoi, String A, String B, String C, String D, String dapAn, String Image) {
        CauHoi = cauHoi;
        Image = Image;
        A = A;
        B = B;
        C = C;
        D = D;
        DapAn = dapAn;
    }




    public CauHoi() {
    }
}
