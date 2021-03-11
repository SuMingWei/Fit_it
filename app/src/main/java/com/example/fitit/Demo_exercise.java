package com.example.fitit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

public class Demo_exercise extends AppCompatActivity {
    private Button back_btn, start_btn, next_btn, intro_next_btn;
    private ImageView exercise_pic;
    //pop up window imgvw
    private ImageView intro_title_iv, intro_iv1, intro_iv2, intro_iv3;
    private TextView clock_txt, exercise_txt, counter, exeHint;
    private Timer timer;
    private int sec = 30, min = 0, num=0, countNumber = 10;
    private int[] Img = { R.drawable.exercise_upper1, R.drawable.exercise_upper2,
            R.drawable.exercise_upper3,R.drawable.exercise_upper4,
            R.drawable.exercise_upper3, R.drawable.exercise_upper5 };

    private DBHelper myDBHelper = new DBHelper(Demo_exercise.this);
    private ArrayList<DiaryInfo> diaryList = new ArrayList<>();
    private ArrayList<PetInfo> petInfo = new ArrayList<>();
    private CountDownTimer cdt; //for countdown
    private boolean pause = false, counting = false, intro1=false;  //for countdown
    private long milliLeft, timeLengthMilli;// for countdown
    private PopupWindow IntroExe1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_exercise);
        findObject();
        clickBtnEvent();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(counting){
                cdt.cancel();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void findObject(){
        exercise_pic = findViewById(R.id.exercise_pic);
        back_btn = findViewById(R.id.back_btn);
        start_btn = findViewById(R.id.start_btn);
        clock_txt = findViewById(R.id.clock_txt);
        exercise_txt = findViewById(R.id.exercise_txt);
        exeHint = findViewById(R.id.exeHint);
        exeHint.setVisibility(View.INVISIBLE);
    }

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
        myDBHelper.updateToPet(1,petInfo.get(0).getUpperlimb()+1,petInfo.get(0).getLowerlimb(),
                petInfo.get(0).getSoftness(),petInfo.get(0).getEndurance());
    }

    public void updateDiaryInfo(){
        getDiaryList();
        for(int i=0;i<diaryList.size();i++){
            if(diaryList.get(i).getDate().equals(getCurrentDate())){
                myDBHelper.updateToDiary(getCurrentDate(),diaryList.get(i).getUpperlimb()+1,diaryList.get(i).getLowerlimb(),
                        diaryList.get(i).getSoftness(),diaryList.get(i).getEndurance(),
                        diaryList.get(i).getUpperrope(),diaryList.get(i).getLowerrope());
                return;
            }
        }
        myDBHelper.insertToDiary(getCurrentDate(),1,0,0,0,0,0);
    }

    private void showPopUpWindow(View v){
        View view = LayoutInflater.from(this).inflate(R.layout.upperlimb_popup1, null, false);
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL,0,0);
        next_btn = (Button) view.findViewById(R.id.next_btn);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
                countDown(45000);
                clock_txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40);
                clock_txt.setPadding(0,0,0,0);
            }
        });
    }

    public void showPopUp_IntroExe1(){
        View view = LayoutInflater.from(this).inflate(R.layout.upperlimb_popup2, null, false);
        IntroExe1 = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        IntroExe1.showAtLocation(view, Gravity.CENTER_HORIZONTAL,0,0);
        counter = (TextView) view.findViewById(R.id.counter);
        intro_iv1 = (ImageView) view.findViewById(R.id.intro_iv1);
        intro_iv2 = (ImageView) view.findViewById(R.id.intro_iv2);
        intro_iv3 = (ImageView) view.findViewById(R.id.intro_iv3);
        intro_title_iv = (ImageView) view.findViewById(R.id.intro_title_iv);
        intro_next_btn = (Button) view.findViewById(R.id.intro_next_btn);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        this.getWindow().setAttributes(lp); //act 是上下文context

    }
    public void clickBtnEvent(){
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counting){
                    cdt.cancel();
                }
                finish();
            }
        });
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(start_btn.getText().equals("開始")){
                    showPopUpWindow(v);
                    start_btn.setText("暫停");
                    clock_txt.setText("0:30");
                    pause = false;
                }
                else if(start_btn.getText().equals("再挑戰")){
                    pause = false;
                    cdt.cancel();
                    num = 0;
                    sec = 30;
                    timeLengthMilli = 45000;
                    exercise_txt.setText("準備開始運動");
                    clock_txt.setText("0:30");
                    showPopUpWindow(v);
                    start_btn.setText("暫停");
                }
                else {
                    if (!pause) {
                        start_btn.setText("繼續");
                        timerPause();
                        pause = true;
                    } else {
                        pause = false;
                        timerResume();
                        start_btn.setText("暫停");
                    }
                }
            }
        });
    }
    public void timerPause() {
        cdt.cancel();
        counting = false;
    }
    private void timerResume() {
        countDown(milliLeft);
    }
    public void countDown(long timeLengthMilli){
        cdt = new CountDownTimer(timeLengthMilli, 1000) {
            public void onTick(long millisUntilFinished) {
                counting = true;
                milliLeft=millisUntilFinished;
                //sec = (int) (millisUntilFinished % 60000) / 1000;
                countNumber--;
                countDownEvent();
            }

            public void onFinish() {
                counting = false;
                exercise_txt.setText("完成！");
                clock_txt.setText("00:00");
                start_btn.setText("再挑戰");
                updatePetInfo();
                updateDiaryInfo();
            }
        }.start();
    }

    private void countDownEvent(){
        if(milliLeft >= 40500 && milliLeft <= 45000){
            //show the exe intr
            if(!intro1) {
                showPopUp_IntroExe1();
                intro1 = true;
                countNumber = 5;
                intro_next_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntroExe1.dismiss();
                        intro1 = false;
                        timerPause();
                        milliLeft = 40000;
                        timerResume();
                    }
                });
            }
            changeCountDownNum_TV();
        }
        else if(milliLeft >=39500  && milliLeft < 40500){
            //close the exe intr and start to countdown
            if(intro1){
                num = 0;
                intro1 = false;
                IntroExe1.dismiss();
            }
        }
        else if(milliLeft >= 25500 && milliLeft <= 30000 ){
            if(!intro1) {
                showPopUp_IntroExe1();
                countNumber = 5;
                intro1 = true;
                intro_title_iv.setImageResource(R.drawable.pop_upper2);
                intro_iv1.setImageResource(R.drawable.introduction_up3);
                intro_iv2.setImageResource(R.drawable.introduction_up4);
                intro_iv3.setImageResource(R.drawable.introduction_up5);
                intro_next_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntroExe1.dismiss();
                        intro1 = false;
                        num = 2;
                        sec = 20;
                        timerPause();
                        milliLeft = 25000;
                        timerResume();
                    }
                });
            }
            changeCountDownNum_TV();
        }
        else if(milliLeft >= 24500 && milliLeft < 25500){
            if(intro1){
                IntroExe1.dismiss();
                num = 2;
                sec = 20;
                clock_txt.setText("0:20");
                intro1 = false;

            }
        }
        else if(milliLeft >= 10500 && milliLeft <= 15000){
            if(!intro1) {
                intro1 = true;
                countNumber = 5;
                showPopUp_IntroExe1();
                intro_title_iv.setImageResource(R.drawable.pop_upper3);
                intro_iv1.setImageResource(R.drawable.introduction_up3);
                intro_iv2.setImageResource(R.drawable.introduction_up6);
                intro_iv3.setImageResource(R.drawable.introduction_up7);
                intro_next_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntroExe1.dismiss();
                        intro1 = false;
                        sec = 10;
                        num = 4;
                        timerPause();
                        milliLeft = 10000;
                        timerResume();
                    }
                });
            }
            changeCountDownNum_TV();
        }
        else if(milliLeft >= 9500 && milliLeft < 10500){
            if(intro1){
                IntroExe1.dismiss();
                sec = 10;
                num = 4;
                clock_txt.setText("0:10");
            }
        }
        else{
            sec-=1;
            //set Clock Text and check if need to hint
            String clockText = "0:"+String.valueOf(sec);
            clock_txt.setText(clockText);
            //change pic
            changePicture();
        }
    }

    private void changeCountDownNum_TV(){
        counter.setText(String.valueOf(countNumber));
        if(countNumber > 4 && countNumber <= 5){
            counter.setBackground(getResources().getDrawable(R.drawable.counter0));
        }
        else if(countNumber > 2 && countNumber <= 4){
            counter.setBackground(getResources().getDrawable(R.drawable.counter1));
        }
        else {
            counter.setBackground(getResources().getDrawable(R.drawable.counter2));
        }
    }


    public void changePicture(){
        exeHint.setVisibility(View.INVISIBLE);
        if(sec >= 20 && sec <= 30){
            exercise_txt.setText("擺動雙臂");
            if(sec >= 20 && sec < 25){
                exeHint.setVisibility(View.VISIBLE);
            }
            exercise_pic.setImageResource(Img[num]);
            num++;
            if(num == 2) {  num = 0; }
        }
        else if(sec >= 10 && sec <= 20){
            exercise_txt.setText("手臂外舉");
            if(sec >= 10 && sec < 15){
                exeHint.setVisibility(View.VISIBLE);
            }
            exercise_pic.setImageResource(Img[num]);
            num++;
            if(num == 4) {  num = 2; }
        }
        else{
            exercise_txt.setText("手臂上舉");
            exercise_pic.setImageResource(Img[num]);
            num++;
            if(num == 6) {  num = 4; }
        }
    }
}