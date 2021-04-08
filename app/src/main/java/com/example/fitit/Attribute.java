package com.example.fitit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Attribute extends AppCompatActivity {
    private TextView type,dog_info,total_minute;
    private ImageView dog_img;
    private Button left_btn,right_btn,back_btn;
    private ProgressBar minute_bar;

    private int lv = 1;
    private int choose_type = 1;
    private int total = 0;

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

    public void findObject() {
        type = findViewById(R.id.type);
        dog_img = findViewById(R.id.dog_img);
        dog_info = findViewById(R.id.dog_info);
        left_btn = findViewById(R.id.left_btn);
        right_btn = findViewById(R.id.right_btn);
        minute_bar = findViewById(R.id.minute_bar);
        total_minute = findViewById(R.id.total_minute);
        back_btn = findViewById(R.id.back_btn);
    }

    public void setValue(int upperlimb,int lowerlimb,int softness,int endurance){
        total = upperlimb + lowerlimb + softness + endurance;
        lv = 1 + (total/150);
        String status = "型態：型態一";

        if(lv < 5){
            choose_type = 1;
            dog_img.setImageDrawable(getResources().getDrawable(R.drawable.dog_walk1));
            dog_info.setText("型態一");
            setEvolutionProgress(total,1);
        }
        else if(lv >= 5 && lv < 10){
            choose_type = 2;
            dog_img.setImageDrawable(getResources().getDrawable(R.drawable.doggypron));
            dog_info.setText("型態二");
            setEvolutionProgress(total,2);
            status = "型態：型態二";
        }
        else if(lv >= 10){
            choose_type = 3;
            dog_img.setImageDrawable(getResources().getDrawable(R.drawable.doggypronpron));
            dog_info.setText("型態三");
            setEvolutionProgress(total,3);
            status = "型態：型態三";
        }

        type.setText(status);
    }

    public void setEvolutionProgress(int total, int type){
        if(type == 1){
            if(total < 600){
                total_minute.setText(String.valueOf(total) + "/600");
                minute_bar.setProgress(total/4);
            }else{
                total_minute.setText("600/600");
                minute_bar.setProgress(150);
            }
        }else if(type == 2){
            if(total < 600){
                total_minute.setText("0/750");
                minute_bar.setProgress(0);
            }else if(total >= 600 && total < 1350){
                total_minute.setText(String.valueOf(total-600) + "/750");
                minute_bar.setProgress((total-600)/5);
            }else if(total > 1350){
                total_minute.setText("750/750");
                minute_bar.setProgress(150);
            }
        }else if(type == 3){
            // max set lv30(1 -> 30)
            if(total < 1350){
                total_minute.setText("0/MAX");
                minute_bar.setProgress(0);
            }else if(total >= 1350 && total < 4350){
                total_minute.setText(String.valueOf(total-750) + "/MAX");
                minute_bar.setProgress((total-1350)/20);
            }else if(total > 4350){
                total_minute.setText("MAX/MAX");
                minute_bar.setProgress(150);
            }
        }

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
                    setEvolutionProgress(total,1);
                    dog_img.setImageDrawable(getResources().getDrawable(R.drawable.dog_walk1));
                    type.setText("型態：型態一");
                    dog_info.setText("型態一");
                    choose_type = 1;
                }else if(choose_type == 3){
                    setEvolutionProgress(total,2);
                    if(lv < 5){
                        dog_img.setImageDrawable(getResources().getDrawable(R.drawable.unknowndog1));
                        type.setText("型態：型態二");
                        dog_info.setText("型態二");
                        choose_type = 2;
                    }else{
                        dog_img.setImageDrawable(getResources().getDrawable(R.drawable.doggypron));
                        type.setText("型態：型態二");
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
                    setEvolutionProgress(total,2);
                    if(lv < 5){
                        dog_img.setImageDrawable(getResources().getDrawable(R.drawable.unknowndog1));
                        type.setText("型態：型態二");
                        dog_info.setText("型態二");
                        choose_type = 2;
                    }else{
                        dog_img.setImageDrawable(getResources().getDrawable(R.drawable.doggypron));
                        type.setText("型態：型態二");
                        dog_info.setText("型態二");
                        choose_type = 2;
                    }
                }else if(choose_type == 2){
                    setEvolutionProgress(total,3);
                    if(lv < 10){
                        dog_img.setImageDrawable(getResources().getDrawable(R.drawable.unknowndog2));
                        type.setText("型態：型態三");
                        dog_info.setText("型態三");
                        choose_type = 3;
                    }else{
                        dog_img.setImageDrawable(getResources().getDrawable(R.drawable.doggypronpron));
                        type.setText("型態：型態三");
                        dog_info.setText("型態三");
                        choose_type = 3;
                    }
                }else if(choose_type == 3){

                }
            }
        });

    }
}