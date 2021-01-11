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

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            diaryList = myDBHelper.getDiaryInfo();
        }
    }

    public void newDiaryInfo(){
        getDiaryList();
        if(!diaryList.get(diaryList.size()-1).getDate().equals(getCurrentDate())){
            fillEmptyDiary();
        }
    }

    public void fillEmptyDiary(){
        int last_date = Integer.parseInt(diaryList.get(diaryList.size()-1).getDate());

        long days = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            long time1 = simpleDateFormat.parse(diaryList.get(diaryList.size()-1).getDate()).getTime();
            long time2 = simpleDateFormat.parse(getCurrentDate()).getTime();
            days = (int) ((time2 - time1) / (24 * 60 * 60 * 1000));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,String.valueOf(days),Toast.LENGTH_SHORT).show();

        for(int i=0;i<days;i++){
            int date = last_date + 1;
            int year = date / 10000;
            int month_day = (date % 10000);
            String new_date = "";
            if(month_day == 1232){
                year += 1;
                new_date = "0101";
            }else{
                if(month_day < 1000){
                    new_date = check_last("0" + String.valueOf(month_day),year);
                }else {
                    new_date = check_last(String.valueOf(month_day), year);
                }
            }
            new_date = String.valueOf(year) + new_date;
            last_date = Integer.parseInt(new_date);
            myDBHelper.insertToDiary(new_date,0,0,0,0);
        }
    }

    public String check_last(String mmdd, int yyyy){
        boolean special_year = false;
        if((yyyy % 4 == 0) && (yyyy % 100 != 0)){
            special_year = true;
        }
        if(special_year == true){
            if(mmdd.equals("0230")){
                return "0301";
            }
        }else{
            if(mmdd.equals("0229")){
                return "0301";
            }
        }
        if(mmdd.equals("0132"))return "0201";
        if(mmdd.equals("0332"))return "0401";
        if(mmdd.equals("0431"))return "0501";
        if(mmdd.equals("0532"))return "0601";
        if(mmdd.equals("0631"))return "0701";
        if(mmdd.equals("0732"))return "0801";
        if(mmdd.equals("0832"))return "0901";
        if(mmdd.equals("0931"))return "1001";
        if(mmdd.equals("1032"))return "1101";
        if(mmdd.equals("1131"))return "1201";

        return mmdd;
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