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

public class lowerRope_exercise extends AppCompatActivity {
    private Button back_btn, start_btn;
    private ImageView exercise_pic;
    private TextView clock_txt, exercise_txt;
    private Timer timer;
    private int sec = 60, min = 4, num=0;
    private int[] Img = { R.drawable.lower_rope1, R.drawable.lower_rope2,
            R.drawable.lower_rope1,R.drawable.lower_rope3,
            R.drawable.lower_rope4, R.drawable.lower_rope5 };

    private DBHelper myDBHelper = new DBHelper(lowerRope_exercise.this);
    private ArrayList<DiaryInfo> diaryList = new ArrayList<>();
    private ArrayList<PetInfo> petInfo = new ArrayList<>();
    private CountDownTimer cdt; //for countdown
    private boolean pause = false;  //for countdown
    private long milliLeft, timeLengthMilli= 180000;// for countdown

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lower_rope_exercise2);
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
        myDBHelper.updateToPet(1,petInfo.get(0).getUpperlimb(),petInfo.get(0).getLowerlimb()+1,
                petInfo.get(0).getSoftness(),petInfo.get(0).getEndurance());
    }

    public void updateDiaryInfo(){
        getDiaryList();
        for(int i=0;i<diaryList.size();i++){
            if(diaryList.get(i).getDate().equals(getCurrentDate())){
                myDBHelper.updateToDiary(getCurrentDate(),diaryList.get(i).getUpperlimb(),diaryList.get(i).getLowerlimb()+1,
                        diaryList.get(i).getSoftness(),diaryList.get(i).getEndurance());
                return;
            }
        }
        myDBHelper.insertToDiary(getCurrentDate(),0,1,0,0);
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
                    countDown(timeLengthMilli);
                    start_btn.setText("暫停");
                }
                else if(start_btn.getText().equals("重新開始")){
                    pause = false;
                    cdt.cancel();
                    num = 0;
                    timeLengthMilli = 180000;
                    countDown(timeLengthMilli);
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
                if(min == 2 && sec == 00){
                    num = 2;
                }
                if(min == 1 && sec == 00){
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
                start_btn.setText("重新開始");
                updatePetInfo();
                updateDiaryInfo();
            }
        }.start();
    }
    public void changePicture(){

        if(min == 2 && sec >= 0 && sec <= 60){
            exercise_txt.setText("彈力繩向後拉開");
            exercise_pic.setImageResource(Img[num]);
            num++;
            if(num == 2) {  num = 0; }
        }
        else if(min == 1 && sec >= 0 && sec <= 60){
            exercise_txt.setText("彈力繩向後勾起");
            exercise_pic.setImageResource(Img[num]);
            num++;
            if(num == 4) {  num = 2; }
        }
        else{
            exercise_txt.setText("彈力繩外側拉開");
            exercise_pic.setImageResource(Img[num]);
            num++;
            if(num == 6) {  num = 4; }
        }
    }

}