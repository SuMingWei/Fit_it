package com.example.fitit;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class ExerciseFragment extends Fragment {
    private LinearLayout upperlimb_btn, lowerlimb_btn, softness_btn, endurance_btn, upperRope_btn, lowerRope_btn, Demo_btn;
    private TextView upperlimbNum_tv, lowerlimbNum_tv, softnessNum_tv, enduranceNum_tv, upperRopeNum_tv, lowerRopeNum_tv;
    private ImageView upperlimb_img,lowerlimb_img,softness_img,endurance_img,upperRope_img,lowerRope_img;
    private DBHelper myDBHelper;
    private LinearLayout testbtn;
    private ArrayList<DiaryInfo> diaryList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        myDBHelper = new DBHelper(container.getContext());
        return view;
    }


    public void onResume() {
        super.onResume();
        updateExerciseNum();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findObject();
        updateExerciseNum();
        clickBtnEvent();
    }

    private void updateExerciseNum(){
        diaryList = myDBHelper.getDiaryInfo();
        DiaryInfo todayInfo = diaryList.get(diaryList.size()-1);
        int upperlimbNum = todayInfo.getUpperlimb();
        int lowerlimbNum = todayInfo.getLowerlimb();
        int enduranceNum = todayInfo.getEndurance();
        int softnessNum= todayInfo.getSoftness();
        int upperropeNum = todayInfo.getUpperrope();
        int lowerropeNum = todayInfo.getLowerrope();

        upperlimbNum_tv.setText(String.valueOf(upperlimbNum)+"/3");
        lowerlimbNum_tv.setText(String.valueOf(lowerlimbNum)+"/3");
        enduranceNum_tv.setText(String.valueOf(enduranceNum)+"/3");
        softnessNum_tv.setText(String.valueOf(softnessNum)+"/3");
        upperRopeNum_tv.setText(String.valueOf(upperropeNum)+"/3");
        lowerRopeNum_tv.setText(String.valueOf(lowerropeNum)+"/3");


        changeColor(upperlimb_btn, upperlimbNum);
        changeColor(lowerlimb_btn, lowerlimbNum);
        changeColor(softness_btn, softnessNum);
        changeColor(endurance_btn, enduranceNum);
        changeColor(upperRope_btn, upperropeNum);
        changeColor(lowerRope_btn, lowerropeNum);

        // change icon color
        if(upperlimbNum >= 3){
            upperlimb_img.setImageDrawable(getResources().getDrawable(R.drawable.upper1));
        }else{
            upperlimb_img.setImageDrawable(getResources().getDrawable(R.drawable.fin_upper1));
        }
        if(lowerlimbNum >= 3){
            lowerlimb_img.setImageDrawable(getResources().getDrawable(R.drawable.lower1));
        }else{
            lowerlimb_img.setImageDrawable(getResources().getDrawable(R.drawable.fin_lower1));
        }
        if(softnessNum >= 3){
            softness_img.setImageDrawable(getResources().getDrawable(R.drawable.softness1));
        }else{
            softness_img.setImageDrawable(getResources().getDrawable(R.drawable.fin_softness1));
        }
        if(enduranceNum >= 3){
            endurance_img.setImageDrawable(getResources().getDrawable(R.drawable.indurance1));
        }else{
            endurance_img.setImageDrawable(getResources().getDrawable(R.drawable.fin_endurance1));
        }
        if(upperropeNum >= 3){
            upperRope_img.setImageDrawable(getResources().getDrawable(R.drawable.upper3));
        }else{
            upperRope_img.setImageDrawable(getResources().getDrawable(R.drawable.fin_upper3));
        }
        if(lowerropeNum >= 3){
            lowerRope_img.setImageDrawable(getResources().getDrawable(R.drawable.lower2));
        }else{
            lowerRope_img.setImageDrawable(getResources().getDrawable(R.drawable.fin_lower2));
        }
    }

    private void changeColor(LinearLayout btn, int num){
        if(num >= 3){
            btn.setBackground(getResources().getDrawable(R.drawable.complete_exercise_background));
        }
        else{
            btn.setBackground(getResources().getDrawable(R.drawable.title_form));
        }
    }

    public String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        String monthstr = String.valueOf(month);
        if(month < 10){
            monthstr = "0"+monthstr;
        }
        String daystr = String.valueOf(day);
        if(day < 10){
            daystr = "0"+daystr;
        }
        return String.valueOf(year) + monthstr + daystr;
    }
    private void clickBtnEvent() {
        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Demo_exercise.class);
                startActivity(intent);
            }
        });
        
        upperlimb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Upper1_exercise.class);
                startActivity(intent);
            }
        });
        lowerlimb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Lower_exercise.class);
                startActivity(intent);
            }
        });
        endurance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Endurance_exercise.class);
                startActivity(intent);
            }
        });
        softness_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Softness_exercise.class);
                startActivity(intent);
            }
        });
        upperRope_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), upperRope_exercise.class);
                startActivity(intent);
            }
        });
        lowerRope_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), lowerRope_exercise.class);
                startActivity(intent);
            }
        });
    }


    private void findObject() {
        //Linearlayout
        testbtn= this.getView().findViewById(R.id.testbtn);
        upperlimb_btn = this.getView().findViewById(R.id.upperlimb_btn);
        lowerlimb_btn = this.getView().findViewById(R.id.lowerlimb_btn);
        softness_btn = this.getView().findViewById(R.id.softness_btn);
        endurance_btn = this.getView().findViewById(R.id.endurance_btn);
        upperRope_btn = this.getView().findViewById(R.id.upperRope_btn);
        lowerRope_btn = this.getView().findViewById(R.id.lowerRope_btn);


        //textview
        upperlimbNum_tv = this.getView().findViewById(R.id.upperlimbNum_tv);
        lowerlimbNum_tv = this.getView().findViewById(R.id.lowerlimbNum_tv);
        softnessNum_tv = this.getView().findViewById(R.id.softnessNum_tv);
        enduranceNum_tv = this.getView().findViewById(R.id.enduranceNum_tv);
        upperRopeNum_tv = this.getView().findViewById(R.id.upperRopeNum_tv);
        lowerRopeNum_tv = this.getView().findViewById(R.id.lowerRopeNum_tv);

        upperlimb_img = this.getView().findViewById(R.id.upperlimb_img);
        lowerlimb_img = this.getView().findViewById(R.id.lowerlimb_img);
        softness_img = this.getView().findViewById(R.id.softness_img);
        endurance_img = this.getView().findViewById(R.id.endurance_img);
        upperRope_img = this.getView().findViewById(R.id.upperRope_img);
        lowerRope_img = this.getView().findViewById(R.id.lowerRope_img);
    }

}