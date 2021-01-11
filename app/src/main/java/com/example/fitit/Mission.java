package com.example.fitit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Mission extends AppCompatActivity {
    private Button upperlimb_btn1, lowerlimb_btn1, softness_btn1, endurance_btn1, back_btn, ropeExe1_btn, ropeExe2_btn, Demo_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);
        findObject();
        clickBtnEvent();

    }
    public void clickBtnEvent(){
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

        upperlimb_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Mission.this, Upper1_exercise.class);
                startActivity(intent);
            }
        });
        lowerlimb_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Mission.this, Lower_exercise.class);
                startActivity(intent);
            }
        });
        softness_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Mission.this, Softness_exercise.class);
                startActivity(intent);
            }
        });
        endurance_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Mission.this, Endurance_exercise.class);
                startActivity(intent);
            }
        });
        ropeExe1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Mission.this, UpperRope_exercise.class);
                startActivity(intent);
            }
        });
        ropeExe2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Mission.this, LowerRope_exercise.class);
                startActivity(intent);
            }
        });
        Demo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Mission.this, Demo_exercise.class);
                startActivity(intent);
            }
        });
    }
    public void findObject(){
        back_btn = findViewById(R.id.back_btn);
        upperlimb_btn1 = findViewById(R.id.upperlimb_btn1);
        lowerlimb_btn1 = findViewById(R.id.lowerlimb_btn1);
        softness_btn1 = findViewById(R.id.softness_btn1);
        endurance_btn1 = findViewById(R.id.endurance_btn1);
        ropeExe1_btn = findViewById(R.id.ropeExe1_btn);
        ropeExe2_btn = findViewById(R.id.ropeExe2_btn);
        Demo_btn = findViewById(R.id.Demo_btn);
    }
}