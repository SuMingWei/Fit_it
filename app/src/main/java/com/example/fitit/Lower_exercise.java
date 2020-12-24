package com.example.fitit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;

public class Lower_exercise extends AppCompatActivity {
    private Button back_btn, start_btn;
    private ImageView exercise_pic;
    private TextView clock_txt, exercise_txt;
    private Timer timer;
    private int min = 3, sec = 60, num = 0;
    private int[] Img = {R.drawable.exercise_lower1, R.drawable.exercise_lower2, R.drawable.exercise_lower1, R.drawable.exercise_lower3,
                        R.drawable.exercise_lower4, R.drawable.exercise_lower5, R.drawable.exercise_lower6, R.drawable.exercise_lower7,
                        R.drawable.exercise_lower8, R.drawable.exercise_lower9};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lower_exercise);
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
    public void clickBtnEvent(){
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish(); }
        });
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_btn.setVisibility(View.INVISIBLE);
                countDown();
            }
        });
    }
    public void countDown(){
        new CountDownTimer(180000, 1000) {
            public void onTick(long millisUntilFinished) {
                min = (int) (millisUntilFinished/60000);
                sec = (int)(millisUntilFinished%60000)/1000;
                if(min == 2 && sec == 00){
                    num = 4;
                }
                if(min == 1 && sec == 00){
                    num = 6;
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

            }
        }.start();
    }
    public void changePicture(){

        if(min == 2 && sec >= 0 && sec <= 60){
            exercise_txt.setText("抬起左右腿");
            exercise_pic.setImageResource(Img[num]);
            num++;
            if(num == 4) {  num = 0; }
        }
        else if(min == 1 && sec >= 0 && sec <= 60){
            exercise_txt.setText("左右腿上舉");
            exercise_pic.setImageResource(Img[num]);
            num++;
            if(num == 8) {  num = 4; }
        }
        else{
            exercise_txt.setText("踮起腳尖");
            exercise_pic.setImageResource(Img[num]);
            num++;
            if(num == 10) {  num = 8; }
        }
    }
}