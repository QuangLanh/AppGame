package com.example.duan1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.viewpager.widget.PagerAdapter;
import com.airbnb.lottie.LottieAnimationView;
import com.example.duan1.R;

public class slideAdapter extends PagerAdapter
{

    private Context context;

    public slideAdapter(Context context) {
        this.context = context;
    }

    private int[] slider_image = {R.raw.theloai,R.raw.mucdo,R.raw.diemcong};
    private String[] slider_title = {"Các Thể Loại Câu Hỏi","Mức Độ","Quy Tắc Cộng Điểm"};
    private String[] slider_desc = {"Sẽ có 6 thể loại cho các bạn lựa chọn để bắt đầu trắc nghiệm : Thể Thao, Âm Nhạc, Phim Ảnh, Game, Đố Mẹo, Địa lý",
                                    "Mỗi thể loại sẽ có 2 mức độ khó và dễ cho bạn lựa chọn, mỗi mức độ sẽ có tổng cộng 15 câu hỏi",
                                    "Trả lời đúng 1 câu dễ sẽ được cộng 15 điểm, trả lời đúng 1 câu khó sẽ được cộng 20 điểm"};

    @Override
    public int getCount() {
        return slider_title.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        LottieAnimationView imgbanner = view.findViewById(R.id.imgbanner);
        TextView tv_title_slider = view.findViewById(R.id.tv_title_slide);
        TextView tv_desc = view.findViewById(R.id.tv_desc);

        imgbanner.setAnimation(slider_image[position]);
        tv_title_slider.setText(slider_title[position]);
        tv_desc.setText(slider_desc[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
