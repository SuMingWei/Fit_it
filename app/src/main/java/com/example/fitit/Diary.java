package com.example.fitit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Diary extends AppCompatActivity {
    private Button back_btn;
    private Spinner date_spinner;
    private TextView content;

    private DBHelper myDBHelper = new DBHelper(Diary.this);
    private ArrayList<DiaryInfo> diaryList = new ArrayList<>();
    private ArrayList<String> dateList = new ArrayList<>();
    private ArrayAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        findObject();
        buttonClickEvent();
        setInit();
    }

    public void findObject(){
        back_btn = findViewById(R.id.back_btn);
        date_spinner = findViewById(R.id.date_spinner);
        content = findViewById(R.id.content);
    }

    public void buttonClickEvent(){
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void insert(){
        myDBHelper.insertToDiary("20201221",1,3,2,1);
        myDBHelper.insertToDiary("20201222",1,1,1,1);
        myDBHelper.insertToDiary("20201223",2,0,1,1);
        myDBHelper.insertToDiary("20201224",3,1,2,1);
    }

    public void setInit(){
        diaryList = myDBHelper.getDiaryInfo();
        dateList = this.getDateList();

        if(diaryList.size() == 0){
            Toast.makeText(Diary.this,"尚未有紀錄",Toast.LENGTH_SHORT).show();
            insert();
        }else{
            this.spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,dateList);
            date_spinner.setAdapter(spinnerAdapter);

            date_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    int indexOfClickItem = adapterView.getPositionForView(view);
                    DiaryInfo diaryInfo = diaryList.get(indexOfClickItem);

                    String date = diaryInfo.getDate();
                    int upperlimb = diaryInfo.getUpperlimb();
                    int lowerlimb = diaryInfo.getLowerlimb();
                    int softness = diaryInfo.getSoftness();
                    int endurance = diaryInfo.getEndurance();

                    generateDiary(date,upperlimb,lowerlimb,softness,endurance);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

    }

    public ArrayList<String> getDateList(){
        ArrayList<String> totalDateData = new ArrayList<>();

        for(int i=0;i<this.diaryList.size();i++){
            DiaryInfo diaryInfo = this.diaryList.get(i);
            String dateInfo = getDateFormat(diaryInfo.getDate());
            totalDateData.add(dateInfo);
        }
        return totalDateData;
    }

    public String getDateFormat(String date){
        int date_num = Integer.parseInt(date);
        int year = date_num / 10000;
        int month = (date_num % 10000) / 100;
        int day = date_num % 100;

        return String.valueOf(year) + "年" + String.valueOf(month) + "月" + String.valueOf(day) + "日";
    }

    public void generateDiary(String date,int upperlimb,int lowerlimb,int softness,int endurance){
        content.setText("\n今天是" + getDateFormat(date) + "\n"
                        + "我總共完成了\n"
                        + "上肢肌力：" + String.valueOf(upperlimb * 5) + " 分鐘\n"
                        + "下肢肌力：" + String.valueOf(lowerlimb * 5) + " 分鐘\n"
                        + "柔軟訓練：" + String.valueOf(softness * 5) + " 分鐘\n"
                        + "心肺訓練：" + String.valueOf(endurance * 5) + " 分鐘\n");
    }

}