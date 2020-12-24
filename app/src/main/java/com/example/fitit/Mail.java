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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Mail extends AppCompatActivity {
    private Button back_btn;
    private Spinner mail_spinner;
    private TextView content;

    private DBHelper myDBHelper = new DBHelper(Mail.this);
    private ArrayList<DiaryInfo> diaryList = new ArrayList<>();
    private ArrayList<WeekInfo> weekList = new ArrayList<>();
    private ArrayList<String> mailList = new ArrayList<>();
    private ArrayAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        findObject();
        buttonClickEvent();
        setInit();
    }

    public void findObject(){
        back_btn = findViewById(R.id.back_btn);
        mail_spinner = findViewById(R.id.mail_spinner);
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

    public void setInit(){
        diaryList = myDBHelper.getDiaryInfo();
        weekList = this.getWeekList();
        mailList = this.getMailList();

        if(diaryList.size() == 0){
            Toast.makeText(Mail.this,"尚未有紀錄",Toast.LENGTH_SHORT).show();
        }else{
            this.spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,mailList);
            mail_spinner.setAdapter(spinnerAdapter);

            mail_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    int indexOfClickItem = adapterView.getPositionForView(view);
                    WeekInfo weekInfo = weekList.get(indexOfClickItem);

                    String dateStart = weekInfo.getDateStart();
                    String dateEnd = weekInfo.getDateEnd();
                    int upperlimb = weekInfo.getUpperlimb();
                    int lowerlimb = weekInfo.getLowerlimb();
                    int softness = weekInfo.getSoftness();
                    int endurance = weekInfo.getEndurance();

                    generateReport(dateStart,dateEnd,upperlimb,lowerlimb,softness,endurance);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }

    public ArrayList<WeekInfo> getWeekList() {
        ArrayList<WeekInfo> totalWeekData = new ArrayList<>();
        int weekNum = this.diaryList.size() / 7;
        String dateStart="";
        String dateEnd="";
        int totalUpperlimb = 0;
        int totalLowerlimb = 0;
        int totalSoftness = 0;
        int totalEndurance = 0;

        for(int i=0;i<weekNum;i++){
            WeekInfo weekInfo = new WeekInfo();
            dateStart="";
            dateEnd="";
            totalUpperlimb = 0;
            totalLowerlimb = 0;
            totalSoftness = 0;
            totalEndurance = 0;

            for(int j=0;j<7;j++){
                DiaryInfo diaryInfo = this.diaryList.get(i*7+j);
                if(j==0){
                    dateStart = diaryInfo.getDate();
                }else if(j==6){
                    dateEnd = diaryInfo.getDate();
                }
                totalUpperlimb += diaryInfo.getUpperlimb();
                totalLowerlimb += diaryInfo.getLowerlimb();
                totalSoftness += diaryInfo.getSoftness();
                totalEndurance += diaryInfo.getEndurance();
            }
            weekInfo.init(dateStart,dateEnd,totalUpperlimb,totalLowerlimb,totalSoftness,totalEndurance);
            totalWeekData.add(weekInfo);
        }
        return  totalWeekData;
    }


    public ArrayList<String> getMailList(){
        ArrayList<String> totalMailData = new ArrayList<>();

        for(int i=0;i<this.weekList.size();i++){
            WeekInfo weekInfo = this.weekList.get(i);
            String mailInfo = weekInfo.getDateStart() + "週活動量分析";
            totalMailData.add(mailInfo);
        }
        return totalMailData;
    }

    public String getDateFormat(String date){
        int date_num = Integer.parseInt(date);
        int year = date_num / 10000;
        int month = (date_num % 10000) / 100;
        int day = date_num % 100;

        return String.valueOf(year) + "年" + String.valueOf(month) + "月" + String.valueOf(day) + "日";
    }

    public void generateReport(String dateStart,String dateEnd,int upperlimb,int lowerlimb,int softness,int endurance){
        String report = "\n這是" + getDateFormat(dateStart) + "\n"
                        + "到" + getDateFormat(dateEnd)
                        + "\n的活動量分析報告\n\n"
                        + checkTotalTime(upperlimb,lowerlimb,softness,endurance)
                        + checkTraining(upperlimb,lowerlimb,softness,endurance)
                        + warning(upperlimb,lowerlimb,softness,endurance);
        content.setText(report);
    }

    public String checkTotalTime(int upperlimb,int lowerlimb,int softness,int endurance){
        int totalTime = (upperlimb + lowerlimb + softness + endurance) * 3;
        String time_report = "您這週的活動總時長為" + String.valueOf(totalTime) + "分鐘。\n";
        if(totalTime < 60){
            time_report += "相比建議的活動量，您的活動量有點不足喔！建議每日可以多花15分鐘在活動這方面，有助於促進健康！\n";
        }else if(totalTime >= 60 && totalTime < 120){
            time_report += "差一點就快達到建議的活動量了，如果一週內有幾天有空可以多做幾次，就太好了，加油！\n";
        }else{
            time_report += "哇！你很厲害喔！這週達到了建議的活動量，請繼續保持，你又朝健康更邁進了！\n";
        }
        return time_report;
    }

    public String checkTraining(int upperlimb,int lowerlimb,int softness,int endurance){
        int upperlimbTime = upperlimb*3;
        int lowerlimbTime = lowerlimb*3;
        int softnessTime = softness*3;
        int enduranceTime = endurance*3;
        int array[] = {upperlimbTime,lowerlimbTime,softnessTime,enduranceTime};
        Arrays.sort(array);
        String report = "\n接下來是各個項目的分析：\n"
                        + "您的的上肢肌力總共做了" + String.valueOf(upperlimbTime) +"分鐘，\n"
                        + "您的的下肢肌力總共做了" + String.valueOf(lowerlimbTime) +"分鐘，\n"
                        + "您的的柔軟訓練總共做了" + String.valueOf(softnessTime) +"分鐘，\n"
                        + "您的的心肺訓練總共做了" + String.valueOf(enduranceTime) +"分鐘。\n"
                        + "整體來說：";
        if(array[3] != array[0]){
            if(upperlimbTime == array[0]){
                report += "您的上肢訓練可能有些不足，可以多加強這個項目。\n";
            }else if(lowerlimbTime == array[0]){
                report += "您的下肢訓練可能有些不足，可以多加強這個項目。\n";
            }else if(softnessTime == array[0]){
                report += "您的柔軟訓練可能有些不足，可以多加強這個項目。\n";
            }else if(enduranceTime == array[0]){
                report += "您的心肺訓練可能有些不足，可以多加強這個項目。\n";
            }
        }else{
            report += "您的訓練很平均，可以繼續保持，或針對較弱的項目再加強！\n";
        }

        return report;
    }

    public String warning(int upperlimb,int lowerlimb,int softness,int endurance){
        String report="溫馨提醒：\n";
        if(upperlimb>=5 && lowerlimb>=5 && softness>=5 && endurance>=5){
            return "\n請持之以恆，食百二！！";
        }
        if(upperlimb < 5){
            report += "上肢訓練：與日常生活有關，故進行上肢訓練，可以預防上肢肌肉快速萎縮，維持生活能力自主。\n";
        }
        if(lowerlimb < 5){
            report += "下肢訓練：可以預防骨質疏鬆，避免身體變形、變矮。亦可維持腿部肌肉，若是下肢肌力不足將造成膝蓋疼痛及雙腳無力的問題。\n";
        }
        if(softness < 5){
            report += "柔軟訓練：可以維持體態，促進血液循環，以改善注意力不足的問題，另外柔軟訓練也可以放鬆肌肉，減少肩頸痠痛。\n";
        }
        if(endurance < 5){
            report += "心肺訓練：心肺功能與心血管疾病及慢性病有密切的關係，因此維持良好的心肺功能，有助於改善及預防上述疾病。\n";
        }
        return report;
    }


}