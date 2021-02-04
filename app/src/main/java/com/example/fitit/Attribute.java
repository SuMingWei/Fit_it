package com.example.fitit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Attribute extends AppCompatActivity {
    private TextView type,upperlimb_tv,lowerlimb_tv,softness_tv,endurance_tv,dog_info;
    private ImageView dog_img;
    private Button left_btn,right_btn,back_btn;

    private int lv = 1;
    private int choose_type = 1;

    private DBHelper myDBHelper = new DBHelper(Attribute.this);
    private ArrayList<PetInfo> petInfo = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribute);

        findObject();
        buttonClickEvent();
        updateInfo();
        evolutionClickEvent();
    }

    public void findObject(){
        type = findViewById(R.id.type);
        upperlimb_tv = findViewById(R.id.upperlimb_tv);
        lowerlimb_tv = findViewById(R.id.lowerlimb_tv);
        softness_tv = findViewById(R.id.softness_tv);
        endurance_tv = findViewById(R.id.endurance_tv);
        dog_img = findViewById(R.id.dog_img);
        dog_info = findViewById(R.id.dog_info);
        left_btn = findViewById(R.id.left_btn);
        right_btn = findViewById(R.id.right_btn);
        back_btn = findViewById(R.id.back_btn);
    }

    public void setValue(int upperlimb,int lowerlimb,int softness,int endurance){
        int total = upperlimb + lowerlimb + softness + endurance;
        lv = 1 + (total/150);
        String status = "型態：型態一";

        if(lv < 5){
            choose_type = 1;
            dog_img.setImageDrawable(getResources().getDrawable(R.drawable.dog_walk1));
            dog_info.setText("型態一");
            left_btn.setEnabled(false);
            right_btn.setEnabled(true);
        }
        else if(lv >= 5 && lv < 10){
            choose_type = 2;
            dog_img.setImageDrawable(getResources().getDrawable(R.drawable.doggypron));
            dog_info.setText("型態二");
            left_btn.setEnabled(true);
            right_btn.setEnabled(true);
            status = "型態：型態二";
        }
        else if(lv >= 10){
            choose_type = 3;
            dog_img.setImageDrawable(getResources().getDrawable(R.drawable.doggypronpron));
            dog_info.setText("型態三");
            left_btn.setEnabled(true);
            right_btn.setEnabled(false);
            status = "型態：型態三";
        }

        type.setText(status);
        upperlimb_tv.setText("上肢肌力：" + upperlimb);
        lowerlimb_tv.setText("下肢肌力：" + lowerlimb);
        softness_tv.setText("柔軟度： " + softness);
        endurance_tv.setText("心肺耐力：" + endurance);

    }

    public void updateInfo(){
        petInfo = myDBHelper.getPetInfo();
        if(petInfo.size() == 0){
            myDBHelper.insertToPet(0,0,0,0);
            setValue(0,0,0,0);
        }else{
            setValue(petInfo.get(0).getUpperlimb()*3,petInfo.get(0).getLowerlimb()*3,
                    petInfo.get(0).getSoftness()*3,petInfo.get(0).getEndurance()*3);
        }
    }

    public void buttonClickEvent(){
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });
    }

    public void evolutionClickEvent(){

        left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choose_type == 1){

                }else if(choose_type == 2){
                    dog_img.setImageDrawable(getResources().getDrawable(R.drawable.dog_walk1));
                    dog_info.setText("型態一");
                    choose_type = 1;
                }else if(choose_type == 3){
                    if(lv < 5){
                        dog_img.setImageDrawable(getResources().getDrawable(R.drawable.unknowndog1));
                        dog_info.setText("型態二");
                        choose_type = 2;
                    }else{
                        dog_img.setImageDrawable(getResources().getDrawable(R.drawable.doggypron));
                        dog_info.setText("型態二");
                        choose_type = 2;
                    }
                }
            }
        });

        right_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choose_type == 1){
                    if(lv < 5){
                        dog_img.setImageDrawable(getResources().getDrawable(R.drawable.unknowndog1));
                        dog_info.setText("型態二");
                        choose_type = 2;
                    }else{
                        dog_img.setImageDrawable(getResources().getDrawable(R.drawable.doggypron));
                        dog_info.setText("型態二");
                        choose_type = 2;
                    }
                }else if(choose_type == 2){
                    if(lv < 10){
                        dog_img.setImageDrawable(getResources().getDrawable(R.drawable.unknowndog2));
                        dog_info.setText("型態三");
                        choose_type = 3;
                    }else{
                        dog_img.setImageDrawable(getResources().getDrawable(R.drawable.doggypronpron));
                        dog_info.setText("型態三");
                        choose_type = 3;
                    }
                }else if(choose_type == 3){

                }
            }
        });

    }
}