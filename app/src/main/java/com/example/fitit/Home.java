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
    private TextView username,upperlimb_tv,lowerlimb_tv,softness_tv,endurance_tv;
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePetInfo();
    }

    public void findObject(){
        username = findViewById(R.id.username);
        upperlimb_tv = findViewById(R.id.upperlimb);
        lowerlimb_tv = findViewById(R.id.lowerlimb);
        softness_tv = findViewById(R.id.softness);
        endurance_tv = findViewById(R.id.endurance);
        pet = findViewById(R.id.pet);
        mission_btn = findViewById(R.id.mission_btn);
        diary_btn = findViewById(R.id.diary_btn);
        mail_btn = findViewById(R.id.mail_btn);
    }

    public void setUsername(){
        String name = getIntent().getStringExtra("user_name");
        username.setText(name + "的小狗");
    }

    public void setValue(int upperlimb,int lowerlimb,int softness,int endurance){
        upperlimb_tv.setText(String.valueOf(upperlimb));
        lowerlimb_tv.setText(String.valueOf(lowerlimb));
        softness_tv.setText(String.valueOf(softness));
        endurance_tv.setText(String.valueOf(endurance));
    }

    public void updatePetInfo(){
        petInfo = myDBHelper.getPetInfo();
        if(petInfo.size() == 0){
            myDBHelper.insertToPet(0,0,0,0);
            setValue(0,0,0,0);
        }else{
            setValue(petInfo.get(0).getUpperlimb(),petInfo.get(0).getLowerlimb(),
                    petInfo.get(0).getSoftness(),petInfo.get(0).getEndurance());
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