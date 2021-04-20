package com.example.fitit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import pl.droidsonroids.gif.GifImageView;

public class Exercise extends AppCompatActivity {
    //layout object
    private GifImageView exe_main_gif;
    private Button counter, back_btn;
    private TextView change_hint, exe_tv1, exe_tv2;
    private View top_background;
    private LinearLayout bottom_background;
    //info record
    private DBHelper myDBHelper = new DBHelper(Exercise.this);
    private ArrayList<DiaryInfo> diaryList = new ArrayList<>();
    private ArrayList<PetInfo> petInfo = new ArrayList<>();
    //else
    private int type;
    private int[] top_dog_img_set = {R.drawable.exercise_upper1, R.drawable.exercise_lower1, R.drawable.exercise_softness1,
            R.drawable.exercise_endurance1, R.drawable.upper_rope1, R.drawable.lower_rope1};
    private int[] gif_set = {R.drawable.upper_move1, R.drawable.upper_move2, R.drawable.upper_move3,
            R.drawable.lower_move1, R.drawable.lower_move2, R.drawable.lower_move3,
            R.drawable.softness_move1, R.drawable.softness_move2, R.drawable.softness_move3,
            R.drawable.endurance_move1, R.drawable.endurance_move1, R.drawable.endurance_move1,
            R.drawable.uprope_move1, R.drawable.uprope_move2, R.drawable.uprope_move3,
            R.drawable.lowrope_move1, R.drawable.lowrope_move2, R.drawable.lowrope_move3};
    private int[] pause_pic = {R.drawable.exercise_upper1, R.drawable.exercise_upper4, R.drawable.exercise_upper5,
            R.drawable.exercise_lower1, R.drawable.exercise_lower4, R.drawable.exercise_lower7,
            R.drawable.exercise_softness1, R.drawable.exercise_softness3, R.drawable.exercise_softness5,
            R.drawable.exercise_endurance1, R.drawable.exercise_endurance1, R.drawable.exercise_endurance1,
            R.drawable.upper_rope1, R.drawable.upper_rope5, R.drawable.upper_rope3,
            R.drawable.lower_rope1, R.drawable.lower_rope3, R.drawable.lower_rope5};
    private String[] exe_type_set = {"上肢運動", "下肢運動", "柔軟運動", "心肺運動", "彈力繩上肢運動", "彈力繩下肢運動"};
    private String[] exe_hint_set = {"手臂擺動", "手臂上舉", "手臂外舉",
            "小腿上舉", "小腿後抬", "踮起腳尖",
            "弓箭步", "轉動肩膀", "轉腰拉筋",
            "原地快走", " ", " ",
            "彈力繩向外拉", "彈力繩向上拉", "彈力繩向前拉",
            "彈力繩向後拉", "彈力繩向後勾", "彈力繩向外拉"};
    private CountDownTimer cdt;
    private long milliLeft, timeLengthMilli=210000;
    private int sec, countNumber = 11, step;
    private boolean pause = false, init = false, finish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        findObject();
        setLayout();
        clickEvent();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(!pause){
                cdt.cancel();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void findObject(){
        Intent intent = this.getIntent();//取得傳遞過來的資料
        type = intent.getIntExtra("type", 0);
        exe_main_gif = findViewById(R.id.exe_main_gif);
        counter = findViewById(R.id.counter);
        change_hint = findViewById(R.id.change_hint);
        exe_tv1 = findViewById(R.id.exe_tv1);
        exe_tv2 = findViewById(R.id.exe_tv2);
        top_background = findViewById(R.id.top_background);
        bottom_background = findViewById(R.id.bottom_background);
        back_btn = findViewById(R.id.back_btn);
        back_btn.setVisibility(View.INVISIBLE);
        clickEvent();
    }

    private void clickEvent(){
        counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finish){
                    back_btn.setVisibility(View.INVISIBLE);
                    finish = false;
                    if(type != 3){
                        countDown(210000);
                    }
                    else{
                        countDown(190000);
                    }
                }
                else{
                    if(!pause){
                        pause = true;
                        timerPause();
                        counter.setBackground(getResources().getDrawable(R.drawable.continue_btn));
                    }
                    else{
                        pause = false;
                        timerResume();
                        counter.setBackground(getResources().getDrawable(R.drawable.stop));
                    }
                }

            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setLayout(){
        if(type != 3){
            setContentView(R.layout.upperlimb_popup1);
            ImageView top_dog_pic;
            GifImageView exe_gifimg1, exe_gifimg2, exe_gifimg3;
            TextView exe_hint1, exe_hint2, exe_hint3, exe_type, tool_hint;
            //find object & set on listener
            exe_type = findViewById(R.id.exe_type);
            tool_hint = findViewById(R.id.tool_hint);
            top_dog_pic = findViewById(R.id.top_dog_pic);
            exe_gifimg1 = findViewById(R.id.exe_gifimg1);
            exe_gifimg2 = findViewById(R.id.exe_gifimg2);
            exe_gifimg3 = findViewById(R.id.exe_gifimg3);
            exe_hint1 = findViewById(R.id.exe_hint1);
            exe_hint2 = findViewById(R.id.exe_hint2);
            exe_hint3 = findViewById(R.id.exe_hint3);
            Button next_btn = findViewById(R.id.next_btn);
            next_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setContentView(R.layout.activity_exercise);
                    findObject();
                    exe_main_gif.setImageResource(gif_set[3*type]);
                    change_hint.setVisibility(View.INVISIBLE);
                    exe_tv2.setText(exe_type_set[type]);
                    countDown(210000);
                    pause = false;
                }
            });
            Button back_btn = findViewById(R.id.back_btn);
            back_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            if(type == 0 || type == 3){
                tool_hint.setText("不須準備道具");
            }
            if(type == 1 || type == 2){
                tool_hint.setText("請準備椅子");
            }
            if(type == 4){
                tool_hint.setText("請準備椅子、彈力繩");
            }
            if(type == 5){
                tool_hint.setText("請準備彈力繩");
            }
            exe_type.setText(exe_type_set[type]);
            top_dog_pic.setImageResource(top_dog_img_set[type]);
            exe_gifimg1.setImageResource(gif_set[3*type]);
            exe_gifimg2.setImageResource(gif_set[3*type+1]);
            exe_gifimg3.setImageResource(gif_set[3*type+2]);
            exe_hint1.setText(exe_hint_set[3*type]);
            exe_hint2.setText(exe_hint_set[3*type+1]);
            exe_hint3.setText(exe_hint_set[3*type+2]);
        }
        else{   //for endurance
            setContentView(R.layout.endurance_popup1);
            ImageView top_dog_pic;
            GifImageView exe_gifimg1;
            TextView exe_hint1, exe_type, tool_hint;
            //find object & set on listener
            exe_type = findViewById(R.id.exe_type);
            tool_hint = findViewById(R.id.tool_hint);
            top_dog_pic = findViewById(R.id.top_dog_pic);
            exe_gifimg1 = findViewById(R.id.exe_gifimg1);
            exe_hint1 = findViewById(R.id.exe_hint1);
            Button next_btn = findViewById(R.id.next_btn);
            next_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setContentView(R.layout.activity_exercise);
                    findObject();
                    exe_main_gif.setImageResource(gif_set[3*type]);
                    change_hint.setVisibility(View.INVISIBLE);
                    exe_tv2.setText(exe_type_set[type]);
                    countDown(190000);
                    pause = false;
                }
            });
            Button back_btn = findViewById(R.id.back_btn);
            back_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            exe_type.setText(exe_type_set[type]);
            top_dog_pic.setImageResource(top_dog_img_set[type]);
            exe_gifimg1.setImageResource(gif_set[3*type]);
            exe_hint1.setText(exe_hint_set[3*type]);
        }

    }

    //running
    public void timerPause() {
        sec+=1;
        exe_main_gif.setImageResource(pause_pic[3*type+step]);
        cdt.cancel();
    }
    private void timerResume() {
        exe_main_gif.setImageResource(gif_set[3*type+step]);
        countDown(milliLeft);
    }

    public void countDown(long timeLengthMilli){
        cdt = new CountDownTimer(timeLengthMilli, 1000) {
            public void onTick(long millisUntilFinished) {
                milliLeft=millisUntilFinished;
                countNumber--;
                countDownEvent();
            }

            public void onFinish() {
                exe_main_gif.setImageResource(pause_pic[3*type+step]);
                exe_tv2.setText("完成！");
                counter.setBackground(getResources().getDrawable(R.drawable.action));
                back_btn.setVisibility(View.VISIBLE);
                updatePetInfo();
                updateDiaryInfo();
                finish = true;
            }
        }.start();
    }

    private void countDownEvent(){
        if(type == 3){
            if(milliLeft > 180000 && milliLeft <= 210000){
                if(!init){
                    init = true;
                    counter.setEnabled(false);
                    exe_main_gif.setImageResource(gif_set[3*type]);
                    top_background.setVisibility(View.VISIBLE);
                    bottom_background.setBackgroundColor(getResources().getColor(R.color.tan));
                    exe_tv1.setText("即將開始");
                    exe_tv1.setTextColor(getResources().getColor(R.color.tablayout_dark));
                    exe_tv2.setText(exe_hint_set[3*type]);
                    exe_tv2.setTextColor(getResources().getColor(R.color.white));
                    step = 0;
                }
                changeCountDownTv();
            }
            else{
                if(init) {
                    init = false;
                    counter.setEnabled(true);
                    top_background.setVisibility(View.INVISIBLE);
                    bottom_background.setBackgroundColor(getResources().getColor(R.color.light_white));
                    counter.setText("");
                    counter.setBackground(getResources().getDrawable(R.drawable.stop));
                    exe_tv1.setText(exe_hint_set[3 * type]);
                    exe_tv1.setTextColor(getResources().getColor(R.color.green_button));
                }
                setClock();
                countNumber = 11;
            }
        }
        else{
            if(milliLeft > 200000 && milliLeft <= 210000){
                if(!init){
                    init = true;
                    counter.setEnabled(false);
                    exe_main_gif.setImageResource(gif_set[3*type]);
                    top_background.setVisibility(View.VISIBLE);
                    bottom_background.setBackgroundColor(getResources().getColor(R.color.tan));
                    exe_tv1.setText("即將開始");
                    exe_tv1.setTextColor(getResources().getColor(R.color.tablayout_dark));
                    exe_tv2.setText(exe_hint_set[3*type]);
                    exe_tv2.setTextColor(getResources().getColor(R.color.white));
                    step = 0;
                }
                changeCountDownTv();
            }
            else if(milliLeft > 140000 && milliLeft <= 200000){
                if(init) {
                    init = false;
                    counter.setEnabled(true);
                    top_background.setVisibility(View.INVISIBLE);
                    bottom_background.setBackgroundColor(getResources().getColor(R.color.light_white));
                    counter.setText("");
                    counter.setBackground(getResources().getDrawable(R.drawable.stop));
                    exe_tv1.setText(exe_hint_set[3 * type]);
                    exe_tv1.setTextColor(getResources().getColor(R.color.green_button));
                }
                setClock();
                countNumber = 11;
            }
            else if(milliLeft > 130000 && milliLeft <= 140000){
                if(!init){
                    change_hint.setVisibility(View.INVISIBLE);
                    init = true;
                    counter.setEnabled(false);
                    exe_main_gif.setImageResource(gif_set[3*type+1]);
                    top_background.setVisibility(View.VISIBLE);
                    bottom_background.setBackgroundColor(getResources().getColor(R.color.tan));
                    exe_tv1.setText("即將開始");
                    exe_tv1.setTextColor(getResources().getColor(R.color.tablayout_dark));
                    exe_tv2.setTextColor(getResources().getColor(R.color.white));
                    exe_tv2.setText(exe_hint_set[3*type+1]);
                    step = 1;
                }
                changeCountDownTv();
            }
            else if(milliLeft > 70000 && milliLeft <= 130000){
                if(init){
                    init = false;
                    counter.setEnabled(true);
                    top_background.setVisibility(View.INVISIBLE);
                    bottom_background.setBackgroundColor(getResources().getColor(R.color.light_white));
                    counter.setText("");
                    counter.setBackground(getResources().getDrawable(R.drawable.stop));
                    exe_tv1.setText(exe_hint_set[3*type+1]);
                    exe_tv1.setTextColor(getResources().getColor(R.color.green_button));
                }
                setClock();
                countNumber = 11;
            }
            else if(milliLeft > 60000 && milliLeft <= 70000){
                if(!init){
                    change_hint.setVisibility(View.INVISIBLE);
                    init = true;
                    counter.setEnabled(false);
                    top_background.setVisibility(View.VISIBLE);
                    bottom_background.setBackgroundColor(getResources().getColor(R.color.tan));
                    exe_main_gif.setImageResource(gif_set[3*type+2]);
                    exe_tv1.setText("即將開始");
                    exe_tv2.setTextColor(getResources().getColor(R.color.white));
                    exe_tv2.setText(exe_hint_set[3*type+2]);
                    step = 2;
                }
                changeCountDownTv();
            }
            else{
                if(init){
                    init = false;
                    counter.setEnabled(true);
                    top_background.setVisibility(View.INVISIBLE);
                    bottom_background.setBackgroundColor(getResources().getColor(R.color.light_white));
                    counter.setText("");
                    counter.setBackground(getResources().getDrawable(R.drawable.stop));
                    exe_tv1.setText(exe_hint_set[3*type+2]);
                }
                setClock();
                countNumber = 11;
            }
        }
    }

    private void setClock(){
        if(step == 0 && type != 3){
            sec = (int) (milliLeft/1000 - 20);
        }
        else if(step == 1){
            sec = (int) (milliLeft/1000 - 10);
        }
        else {
            sec = (int)(milliLeft/1000);
        }
        String minStr = String.valueOf(sec/60);
        String secStr = String.valueOf(sec%60);
        if(sec <= 0 ){
            sec = 0;
        }
        if(secStr.length()==1){
            secStr = "0"+String.valueOf(sec%60);
        }
        if(sec%60 <= 10 && step != 2 && type != 3){
            change_hint.setVisibility(View.VISIBLE);
        }
        String clockText =minStr+":"+secStr;
        exe_tv2.setText(clockText);
        exe_tv2.setTextColor(getResources().getColor(R.color.dark_tan));
    }
    private void changeCountDownTv(){
        counter.setText(String.valueOf(countNumber));
        if(countNumber > 5 && countNumber <= 10){
            counter.setBackground(getResources().getDrawable(R.drawable.counter0));
        }
        else if(countNumber > 3 && countNumber <= 5){
            counter.setBackground(getResources().getDrawable(R.drawable.counter1));
        }
        else {
            counter.setBackground(getResources().getDrawable(R.drawable.counter2));
        }
    }

    //for Info recording
    public void getPetInfo(){
        petInfo = myDBHelper.getPetInfo();
        if(petInfo.size() == 0){
            myDBHelper.insertToPet(0,0,0,0);
            petInfo = myDBHelper.getPetInfo();
        }
    }

    public void getDiaryList(){
        diaryList = myDBHelper.getDiaryInfo();
        if(diaryList.size() == 0){
            myDBHelper.insertToDiary(getCurrentDate(),0,0,0,0,0,0);
            diaryList = myDBHelper.getDiaryInfo();
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

    public void updatePetInfo(){
        getPetInfo();
        int[] update = {0, 0, 0, 0};
        if(type == 0 || type == 4){
            update[0] = 1;
        }
        else if(type == 1 || type == 5){
            update[1] = 1;
        }
        else {
            update[type] = 1;
        }
        myDBHelper.updateToPet(1,petInfo.get(0).getUpperlimb()+update[0],petInfo.get(0).getLowerlimb()+update[1],
                petInfo.get(0).getSoftness()+update[2],petInfo.get(0).getEndurance()+update[3]);
    }

    public void updateDiaryInfo(){
        getDiaryList();
        int[] update = {0, 0, 0, 0, 0, 0};
        update[type] = 1;
        for(int i=0;i<diaryList.size();i++){
            if(diaryList.get(i).getDate().equals(getCurrentDate())){
                myDBHelper.updateToDiary(getCurrentDate(),diaryList.get(i).getUpperlimb()+update[0],diaryList.get(i).getLowerlimb()+update[1],
                        diaryList.get(i).getSoftness()+update[2],diaryList.get(i).getEndurance()+update[3],
                        diaryList.get(i).getUpperrope()+update[4],diaryList.get(i).getLowerrope()+update[5]);
                return;
            }
        }
        myDBHelper.insertToDiary(getCurrentDate(),update[0],update[1],update[2],update[3],update[4],update[5]);
    }
}