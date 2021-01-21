package com.example.fitit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

public class Demo_exercise extends AppCompatActivity {
    private Button back_btn, start_btn;
    private ImageView exercise_pic;
    private TextView clock_txt, exercise_txt;
    private Timer timer;
    private int sec = 30, min = 0, num=0;
    private int[] Img = { R.drawable.exercise_upper1, R.drawable.exercise_upper2,
            R.drawable.exercise_upper3,R.drawable.exercise_upper4,
            R.drawable.exercise_upper3, R.drawable.exercise_upper5 };

    private DBHelper myDBHelper = new DBHelper(Demo_exercise.this);
    private ArrayList<DiaryInfo> diaryList = new ArrayList<>();
    private ArrayList<PetInfo> petInfo = new ArrayList<>();
    private CountDownTimer cdt; //for countdown
    private boolean pause = false;  //for countdown
    private long milliLeft, timeLengthMilli= 30000;// for countdown

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_exercise);
        findObject();
        clickBtnEvent();
    }

    public void findObject(){
        exercise_pic = findViewById(R.id.exercise_pic);
        back_btn = findViewById(R.id.back_btn);
        start_btn = findViewById(R.id.start_btn);
        clock_txt = findViewById(R.id.clock_txt);
        exercise_txt = findViewById(R.id.exercise_txt);
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
            myDBHelper.insertToDiary(getCurrentDate(),0,0,0,0);
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
                        diaryList.get(i).getSoftness(),diaryList.get(i).getEndurance());
                return;
            }
        }
        myDBHelper.insertToDiary(getCurrentDate(),1,0,0,0);
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
                    pause = false;
                    timeLengthMilli = 30000;
                    countDown(30000);
                    start_btn.setText("暫停");
                }
                else if(start_btn.getText().equals("再挑戰")){
                    pause = false;
                    cdt.cancel();
                    num = 0;
                    timeLengthMilli = 30000;
                    countDown(30000);
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
    }public void timerPause() {
        cdt.cancel();
    }
    private void timerResume() {
        countDown(milliLeft);
    }
    public void countDown(long timeLengthMilli){
        cdt = new CountDownTimer(timeLengthMilli, 1000) {
            public void onTick(long millisUntilFinished) {
                milliLeft=millisUntilFinished;
                min = (int) (millisUntilFinished/60000);
                sec = (int)(millisUntilFinished%60000)/1000;
                if(sec == 20){
                    num = 2;
                }
                if(sec == 10){
                    num = 4;
                }
                changePicture();
                if(sec>=0 && sec<10) {
                    clock_txt.setText( String.valueOf(min)+":0"+String.valueOf(sec)); }
                else {
                    clock_txt.setText( String.valueOf(min)+":"+String.valueOf(sec));
                }

            }

            public void onFinish() {
                exercise_txt.setText("完成！");
                clock_txt.setText("00:00");
                start_btn.setText("再挑戰");
                updatePetInfo();
                updateDiaryInfo();
            }
        }.start();
    }
    public void changePicture(){

        if(sec >= 20 && sec <= 30){
            exercise_txt.setText("擺動雙臂");
            exercise_pic.setImageResource(Img[num]);
            num++;
            if(num == 2) {  num = 0; }
        }
        else if(sec >= 10 && sec <= 20){
            exercise_txt.setText("手臂外舉");
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