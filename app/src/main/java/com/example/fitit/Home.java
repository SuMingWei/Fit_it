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
import java.util.Calendar;
import java.util.Random;

public class Home extends AppCompatActivity {
    private TextView username,level,level_value,dialog_tv;
    private ImageView pet,pet_front,appearance1,appearance2,appearance3;
    private ProgressBar level_bar;
    private Button mission_btn,diary_btn,mail_btn;

    private DBHelper myDBHelper = new DBHelper(Home.this);
    private ArrayList<PetInfo> petInfo = new ArrayList<>();
    private ArrayList<DiaryInfo> diaryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findObject();
        setUsername();
        updatePetInfo();
        newDiaryInfo();
        buttonClickEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePetInfo();
    }

    public void findObject(){
        username = findViewById(R.id.username);
        level = findViewById(R.id.level);
        level_value = findViewById(R.id.value);
        level_bar = findViewById(R.id.level_bar);
        pet_front = findViewById(R.id.pet_front);
        pet = findViewById(R.id.pet);
        dialog_tv = findViewById(R.id.dialog);
        mission_btn = findViewById(R.id.mission_btn);
        diary_btn = findViewById(R.id.diary_btn);
        mail_btn = findViewById(R.id.mail_btn);
    }

    public void setUsername(){
        String name = getIntent().getStringExtra("user_name");
        username.setText(name + "的小狗");
    }

    public void changeDialog(){
        String dialog[] = {
                "\n肌力訓練：\n可以強化骨頭和肌肉，\n防止因跌倒而骨折。\n",
                "\n柔軟訓練：\n可以訓練平衡，預防跌倒，\n也可以防止肌肉僵化。\n",
                "\n心肺訓練：\n可以提升心血管健康，\n增加全身代謝量\n"};

        int num = (int)(Math.random()*3);
        dialog_tv.setText(dialog[num]);
    }
    public void changeImage(int level){
        if(level < 5){
            pet.setImageDrawable(getResources().getDrawable(R.drawable.dog_walk1));
        }else if(level >=5 && level <10){
            pet.setImageDrawable(getResources().getDrawable(R.drawable.doggypron));
        }else{
            pet.setImageDrawable(getResources().getDrawable(R.drawable.doggypronpron));
        }
    }

    public void setLevel(int upperlimb,int lowerlimb,int softness,int endurance){
        int total = upperlimb + lowerlimb + softness + endurance;
        int lv = 1 + (total/150);
        int exp = total % 150;
        level.setText("Lv." + String.valueOf(lv));
        level_value.setText(String.valueOf(exp) + "/150");
        level_bar.setProgress(exp);
        changeImage(lv);
    }

    public void updatePetInfo(){
        changeDialog();

        petInfo = myDBHelper.getPetInfo();
        if(petInfo.size() == 0){
            myDBHelper.insertToPet(0,0,0,0);
            setLevel(0,0,0,0);
        }else{
            setLevel(petInfo.get(0).getUpperlimb()*3,petInfo.get(0).getLowerlimb()*3,
                    petInfo.get(0).getSoftness()*3,petInfo.get(0).getEndurance()*3);
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

    public void getDiaryList(){
        diaryList = myDBHelper.getDiaryInfo();
        if(diaryList.size() == 0){
            insert();
            myDBHelper.insertToDiary(getCurrentDate(),0,0,0,0);
            diaryList = myDBHelper.getDiaryInfo();
        }
    }

    public void newDiaryInfo(){
        getDiaryList();
        if(!diaryList.get(diaryList.size()-1).getDate().equals(getCurrentDate())){
            myDBHelper.insertToDiary(getCurrentDate(),0,0,0,0);
        }
    }

    public void buttonClickEvent(){
        pet_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Home.this,Attribute.class);
                startActivity(intent);
            }
        });
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

    public void insert(){
        myDBHelper.insertToDiary("20201205",1,3,2,1);
        myDBHelper.insertToDiary("20201206",1,1,1,1);
        myDBHelper.insertToDiary("20201207",2,0,1,1);
        myDBHelper.insertToDiary("20201208",3,1,2,1);
        myDBHelper.insertToDiary("20201209",1,3,2,1);
        myDBHelper.insertToDiary("20201210",1,1,1,1);
        myDBHelper.insertToDiary("20201211",2,0,1,1);
        myDBHelper.insertToDiary("20201212",3,1,2,1);
        myDBHelper.insertToDiary("20201213",1,3,2,1);
        myDBHelper.insertToDiary("20201214",1,1,1,1);
        myDBHelper.insertToDiary("20201215",2,0,1,1);
        myDBHelper.insertToDiary("20201216",3,1,2,1);
        myDBHelper.insertToDiary("20201217",1,3,2,1);
        myDBHelper.insertToDiary("20201218",1,1,1,1);
        myDBHelper.insertToDiary("20201219",2,0,1,1);
        myDBHelper.insertToDiary("20201220",3,1,2,1);
        myDBHelper.insertToDiary("20201221",1,3,2,1);
        myDBHelper.insertToDiary("20201222",1,1,1,1);
        myDBHelper.insertToDiary("20201223",2,0,1,1);
        myDBHelper.insertToDiary("20201224",3,1,2,1);
        myDBHelper.updateToPet(1,35,25,30,20);
    }


}