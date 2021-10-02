package com.example.duan1.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.duan1.Fragment.Fragment_Login;
import com.example.duan1.Fragment.Fragment_Signup;

public class LoginAdapter extends FragmentPagerAdapter {
   private Context context;
   int totalTabs;

   public LoginAdapter(FragmentManager fm, Context context,int totalTabs){
      super(fm);
      this.context = context;
      this.totalTabs = totalTabs;
   }

   @Override
   public int getCount() {
      return totalTabs;
   }

   public  Fragment getItem(int position){
      switch (position){
         case 0:
            Fragment_Login fragment_login = new Fragment_Login();
            return  fragment_login;
         case 1:
            Fragment_Signup fragmentSignup = new Fragment_Signup();
            return fragmentSignup;
         default:
            return null;
      }
   }
}
