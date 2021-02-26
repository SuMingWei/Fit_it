package com.example.fitit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Upper1_exercise extends AppCompatActivity {
    private Button back_btn, start_btn, next_btn;
    private ImageView exercise_pic;
    private TextView clock_txt, exercise_txt;
    private Timer timer;
    private int sec = 60, min = 4, num=0;
    private int[] Img = { R.drawable.exercise_upper1, R.drawable.exercise_upper2,
                                R.drawable.exercise_upper3,R.drawable.exercise_upper4,
                                R.drawable.exercise_upper3, R.drawable.exercise_upper5 };

    private DBHelper myDBHelper = new DBHelper(Upper1_exercise.this);
    private ArrayList<DiaryInfo> diaryList = new ArrayList<>();
    private ArrayList<PetInfo> petInfo = new ArrayList<>();
    private CountDownTimer cdt; //for countdown
    private boolean pause = false;  //for countdown
    private long milliLeft, timeLengthMilli= 180000;// for countdown
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upper1_exercise);
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
                myDBHelper.updateToDiary(getCurrentDate(),diaryList.get(i).getUpperlimb(),diaryList.get(i).getLowerlimb(),
                        diaryList.get(i).getSoftness(),diaryList.get(i).getEndurance(),
                        diaryList.get(i).getUpperrope()+1,diaryList.get(i).getLowerrope());
                return;
            }
        }
        myDBHelper.insertToDiary(getCurrentDate(),0,0,0,0,1,0);
    }

    private void showPopUpWindow(View v){
        View view = LayoutInflater.from(this).inflate(R.layout.upperlimb_popup1, null, false);
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL,0,0);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                countDown(5100);
                clock_txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40);
                clock_txt.setPadding(0,0,0,0);
                backgroundAlpha(1);
            }
        });
        Button exe1_btn = (Button) view.findViewById(R.id.exe1_btn);
        Button exe2_btn = (Button) view.findViewById(R.id.exe2_btn);
        Button exe3_btn = (Button) view.findViewById(R.id.exe3_btn);
        next_btn = (Button) view.findViewById(R.id.next_btn);

        //set exe1btn listener
        exe1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(0.4f);
                showExe1Popup();
            }
        });

        exe2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(0.4f);
                showExe2Popup();
            }
        });

        exe3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(0.4f);
                showExe3Popup();
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
                countDown(5100);
                clock_txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40);
                clock_txt.setPadding(0,0,0,0);
            }
        });
    }
    private void showExe1Popup(){
        View view = LayoutInflater.from(this).inflate(R.layout.upperlimb_popup2, null, false);
        PopupWindow tmpWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        tmpWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL,0,0);
        Button next_btn = (Button) view.findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmpWindow.dismiss();
                backgroundAlpha(1);
            }
        });
    }
    private void showExe2Popup(){
        View view = LayoutInflater.from(this).inflate(R.layout.upperlimb_popup3, null, false);
        PopupWindow tmpWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        tmpWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL,0,0);
        Button next_btn = (Button) view.findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmpWindow.dismiss();
                backgroundAlpha(1);
            }
        });
    }
    private void showExe3Popup(){
        View view = LayoutInflater.from(this).inflate(R.layout.upperlimb_popup4, null, false);
        PopupWindow tmpWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        tmpWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL,0,0);
        Button next_btn = (Button) view.findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmpWindow.dismiss();
                backgroundAlpha(1);
            }
        });
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
                timerPause();
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
                    pause = false;
                    start_btn.setText("倒數中");
                    clock_txt.setText("5");
                    exercise_txt.setText("即將開始運動！");
                }
                else if(start_btn.getText().equals("倒數中")){
                }
                else if(start_btn.getText().equals("再挑戰")){
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
                if(start_btn.getText().equals("倒數中")){
                    sec = (int) (millisUntilFinished % 60000) / 1000;
                    if(sec == 0){
                        clock_txt.setText("GO");
                    }
                    else {
                        clock_txt.setText(String.valueOf(sec));
                    }
                }
                else{
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


            }

            public void onFinish() {
                if(start_btn.getText().equals("倒數中")){
                    start_btn.setText("暫停");
                    cdt.cancel();
                    countDown(180000);
                }
                else {
                    exercise_txt.setText("完成！");
                    clock_txt.setText("00:00");
                    start_btn.setText("再挑戰");
                    updatePetInfo();
                    updateDiaryInfo();
                }
            }
        }.start();
    }
    public void changePicture(){

        if(min == 2 && sec >= 0 && sec <= 60){
            exercise_txt.setText("擺動雙臂");
            exercise_pic.setImageResource(Img[num]);
            num++;
            if(num == 2) {  num = 0; }
        }
        else if(min == 1 && sec >= 0 && sec <= 60){
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