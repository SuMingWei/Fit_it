package com.example.fitit;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class GuidePagerAdapter extends PagerAdapter {
    Context context;
    List<PagerModel> pagerArr;
    LayoutInflater inflater;

    public GuidePagerAdapter(Context context, List<PagerModel> pagerArr) {
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
        View view = inflater.inflate(R.layout.page_list_item,container,false);

        ImageView img = (ImageView)view.findViewById(R.id.image);
        view.setTag(position);
        ((ViewPager)container).addView(view);

        PagerModel pagerModel = pagerArr.get(position);

        img.setImageResource(pagerModel.getSource());

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
}
