package com.example.fitit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

public class Endurance_exercise extends AppCompatActivity {
    private Button back_btn, start_btn, next_btn, intro_next_btn;
    private ImageView exercise_pic;
    private TextView clock_txt, exercise_txt, counter, exeHint;
    private Timer timer;
    private boolean pause=false, counting = true;
    private int sec = 180, num=0;
    private long milliLeft, timeLengthMilli= 180000;// for countdown
    private int[] Img = {R.drawable.exercise_endurance1, R.drawable.exercise_endurance2};
    private CountDownTimer cdt; //for countdown
    private DBHelper myDBHelper = new DBHelper(Endurance_exercise.this);
    private ArrayList<DiaryInfo> diaryList = new ArrayList<>();
    private ArrayList<PetInfo> petInfo = new ArrayList<>();
    //for pop up
    private ImageView intro_title_iv, intro_iv1, intro_iv2, intro_iv3;
    private boolean intro1 = false;
    private PopupWindow IntroExe1;
    private int countNumber=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endurance_exercise);
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
        myDBHelper.updateToPet(1,petInfo.get(0).getUpperlimb(),petInfo.get(0).getLowerlimb(),
                petInfo.get(0).getSoftness(),petInfo.get(0).getEndurance()+1);
    }

    public void updateDiaryInfo(){
        getDiaryList();
        for(int i=0;i<diaryList.size();i++){
            if(diaryList.get(i).getDate().equals(getCurrentDate())){
                myDBHelper.updateToDiary(getCurrentDate(),diaryList.get(i).getUpperlimb(),diaryList.get(i).getLowerlimb(),
                        diaryList.get(i).getSoftness(),diaryList.get(i).getEndurance()+1,
                        diaryList.get(i).getUpperrope(),diaryList.get(i).getLowerrope());
                return;
            }
        }
        myDBHelper.insertToDiary(getCurrentDate(),0,0,0,1,0,0);
    }

    public void clickBtnEvent(){
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cdt != null){
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
                    clock_txt.setText("3:00");
                    pause = false;
                    exercise_txt.setText("即將開始運動！");
                }
                else if(start_btn.getText().equals("再挑戰")){
                    pause = false;
                    cdt.cancel();
                    //reset value
                    countNumber = 10;
                    num = 0;
                    sec = 180;
                    timeLengthMilli = 190000;
                    exercise_txt.setText("即將開始運動");
                    clock_txt.setText("3:00");
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

    private void showPopUpWindow(View v){
        backgroundAlpha(0.5f);
        View view = LayoutInflater.from(this).inflate(R.layout.endurance_popup1, null, false);
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL,0,0);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
                countDown(190000);
                clock_txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40);
                clock_txt.setPadding(0,0,0,0);
            }
        });
        next_btn = (Button) view.findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
    }
    public void showPopUp_IntroExe1(){
        backgroundAlpha(0.5f);
        View view = LayoutInflater.from(this).inflate(R.layout.endurance_popup2, null, false);
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


    public void timerPause() {
        sec+=1;
        counting = false;
        cdt.cancel();
    }
    private void timerResume() {
        countDown(milliLeft);
    }

    public void countDown(long timeLengthMilli){
        cdt = new CountDownTimer(timeLengthMilli, 1000) {
            public void onTick(long millisUntilFinished) {
                counting = true;
                milliLeft=millisUntilFinished;
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
        if(milliLeft >= 180500 && milliLeft <= 190000){
            //show the exe intr
            if(!intro1) {
                showPopUp_IntroExe1();
                intro1 = true;
                countNumber = 10;
                intro_next_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntroExe1.dismiss();
                        backgroundAlpha(1);
                        timerPause();
                        milliLeft = 180000;
                        timerResume();
                        intro1 = false;
                    }
                });
            }
            changeCountDownNum_TV();
        }
        else if(milliLeft >=179500  && milliLeft < 180500){
            //close the exe intr and start to countdown
            if(intro1){
                num = 0;
                intro1 = false;
                IntroExe1.dismiss();
                backgroundAlpha(1);
            }
        }
        else{
            sec-=1;
            //set Clock Text and check if need to hint
            String minStr = String.valueOf(sec/60);
            String secStr = String.valueOf(sec%60);
            if(sec <= 0 ){
                sec = 0;
            }
            if(secStr.length()==1){
                secStr = "0"+String.valueOf(sec%60);
            }

            String clockText =minStr+":"+secStr;
            clock_txt.setText(clockText);
            //change pic
            changePicture();
        }
    }

    private void changeCountDownNum_TV(){
        if(intro1){
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
    }

    public void changePicture(){
        exercise_txt.setText("原地踏步");
        exercise_pic.setImageResource(Img[num]);
        num++;
        if(num == 2) {  num = 0; }
    }
}