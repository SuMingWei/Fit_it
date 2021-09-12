package com.example.fitit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class TeachPagerAdapter extends PagerAdapter {
    Context context;
    List<PagerModel> pagerArr;
    LayoutInflater inflater;

    private String[] exe_type_set = {"上肢運動", "下肢運動", "柔軟運動", "心肺運動", "彈力繩上肢運動"};
    private String type;

    public TeachPagerAdapter(Context context, List<PagerModel> pagerArr) {
        this.context = context;
        this.pagerArr = pagerArr;

        inflater = ((Activity) context).getLayoutInflater();
    }

    @Override
    public int getCount() {
        return pagerArr.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.exercise_list_item,container,false);
        // for exercise type choose
        GifImageView gif = (GifImageView)view.findViewById(R.id.exercise_gif);
        TextView title = view.findViewById(R.id.exec_type);
        Button enter_btn = view.findViewById(R.id.enter_btn);

        view.setTag(position);
        ((ViewPager)container).addView(view);

        PagerModel pagerModel = pagerArr.get(position);

        type = exe_type_set[Integer.parseInt(pagerModel.getId())-1];
        title.setText(type);
        gif.setImageResource(pagerModel.getSource());
        clickBtnEvent(enter_btn);

        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View) object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    public void clickBtnEvent(Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVideo(v);
            }
        });
    }

    public void showVideo(View v){
        Dialog dialog = new Dialog(this.context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(R.layout.report_popup);
        dialog.setCancelable(false);

        ImageView exit_btn = dialog.findViewById(R.id.exit_btn);

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}
