package com.example.fitit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Attribute extends AppCompatActivity {
    private TextView attribute;
    private ImageView dog1,dog2,dog3;
    private Button back_btn;

    private DBHelper myDBHelper = new DBHelper(Attribute.this);
    private ArrayList<PetInfo> petInfo = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribute);

        findObject();
        buttonClickEvent();
        updateInfo();
    }

    public void findObject(){
        attribute = findViewById(R.id.attribute);
        dog1 = findViewById(R.id.dog1);
        dog2 = findViewById(R.id.dog2);
        dog3 = findViewById(R.id.dog3);
        back_btn = findViewById(R.id.back_btn);
    }

    public void setValue(int upperlimb,int lowerlimb,int softness,int endurance){
        int total = upperlimb + lowerlimb + softness + endurance;
        int lv = 1 + (total/150);
        String status = "型態一";
        if(lv >= 5 && lv < 10){
            dog2.setImageDrawable(getResources().getDrawable(R.drawable.doggypron));
            status = "型態二";
        }else if(lv >= 10){
            dog2.setImageDrawable(getResources().getDrawable(R.drawable.doggypron));
            dog3.setImageDrawable(getResources().getDrawable(R.drawable.doggypronpron));
            status = "型態三";
        }
        attribute.setText("\n型態：" + status +
                        "\n上肢肌力：" + upperlimb +
                        "\n下肢肌力：" + lowerlimb +
                        "\n柔軟度： " + softness +
                        "\n心肺耐力：" + endurance + "\n");
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
}