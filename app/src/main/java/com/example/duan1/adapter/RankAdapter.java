package com.example.duan1.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.duan1.Model.XepHangDiem;
import com.example.duan1.R;

import java.util.ArrayList;

public class RankAdapter extends BaseAdapter {
    ArrayList<XepHangDiem> dsrank = new ArrayList<XepHangDiem>();
    Context context;

    class View_1_ranker{
        TextView txt_user;
        TextView txt_diem;
        TextView txt_theloai;
        TextView txt_chedo;
    }
    public RankAdapter(Context context,ArrayList<XepHangDiem> dsrank){
        this.dsrank = dsrank;
        this.context = context;
    }
    @Override
    public int getCount() {
        return dsrank.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View_1_ranker view1Ranker;
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();

        if (convertView == null)
        {
            view1Ranker = new View_1_ranker();
            convertView = layoutInflater.inflate(R.layout.lv_item_rank, null);
            view1Ranker.txt_user = convertView.findViewById(R.id.txt_user_rank_lv);
            view1Ranker.txt_diem = convertView.findViewById(R.id.txt_score_rank_lv);
            view1Ranker.txt_theloai = convertView.findViewById(R.id.txt_theloai_rank_lv);
            view1Ranker.txt_chedo = convertView.findViewById(R.id.txt_chedo_rank_lv);
            convertView.setTag(view1Ranker);

        } else

            view1Ranker = (View_1_ranker)convertView.getTag();
            view1Ranker.txt_user.setText(dsrank.get(position).User.toString());
            view1Ranker.txt_diem.setText(dsrank.get(position).diem1.toString());
            view1Ranker.txt_theloai.setText(dsrank.get(position).Tloai.toString());
            view1Ranker.txt_chedo.setText(dsrank.get(position).chedo.toString());
        return convertView;

    }
}
