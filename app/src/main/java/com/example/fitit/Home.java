package com.example.fitit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private TextView username,level,progress;
    private ProgressBar level_bar;
    private ImageView pet;
    private Button mission_btn,diary_btn,mail_btn;

    private DBHelper myDBHelper = new DBHelper(Home.this);
    private ArrayList<PetInfo> petInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findObject();
        setUsername();
        buttonClickEvent();
        updatePetInfo();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Toast.makeText(this,"onResume",Toast.LENGTH_SHORT).show();
//        updatePetInfo();
//    }

    public void findObject(){
        username = findViewById(R.id.username);
        level_bar = findViewById(R.id.level_bar);
        level = findViewById(R.id.level);
        progress = findViewById(R.id.progress);
        pet = findViewById(R.id.pet);
        mission_btn = findViewById(R.id.mission_btn);
        diary_btn = findViewById(R.id.diary_btn);
        mail_btn = findViewById(R.id.mail_btn);
    }

    public void setUsername(){
        String name = getIntent().getStringExtra("user_name");
        username.setText(name + "的小狗");
    }

    public void setLevel(int experience){
        level.setText("Lv." + String.valueOf(experience / 100 + 1));
        level_bar.setProgress(experience % 100);
        progress.setText(String.valueOf(experience % 100) + "/100");
    }

    public void updatePetInfo(){
        petInfo = myDBHelper.getPetInfo();
        if(petInfo.size() == 0){
            myDBHelper.insertToPet(0);
            setLevel(0);
        }else{
            setLevel(petInfo.get(0).getExperience());
        }

    }

    public void buttonClickEvent(){
        mission_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Home.this,Mission.class);
                startActivity(intent);
            }
        });

        diary_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Home.this,Diary.class);
                startActivity(intent);
            }
        });

        mail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Home.this,Mail.class);
                startActivity(intent);
            }
        });
    }


}