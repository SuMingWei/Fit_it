package com.example.fitit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;


public class ReportFragment extends Fragment {
    private Button left_btn, right_btn;
    private TextView choose_date_tv,total_time_tv;
    private TextView upperlimb_info,upperlimb_time,lowerlimb_info,lowerlimb_time,softness_info,softness_time,endurance_info,endurance_time;
    private LinearLayout upperlimb_background,lowerlimb_background,softness_background,endurance_background;
    private ImageView upperlimb_img,lowerlimb_img,softness_img,endurance_img;

    private BarChart barChart;

    private DBHelper myDBHelper;
    private ArrayList<DiaryInfo> diaryList = new ArrayList<>();
    private ArrayList<WeekInfo> weekList = new ArrayList<>();
    private ArrayList<String> reportDateList = new ArrayList<>();

    private int weekNum = 0;
    private int currentWeek = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        myDBHelper = new DBHelper(container.getContext());

        findObject(view);
        setInit();
        buttonClickEvent();

        return view;
    }

    

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getContext(),String.valueOf(currentWeek),Toast.LENGTH_SHORT).show();
        setInit();
    }

    public void findObject(View view){
        left_btn = view.findViewById(R.id.left_btn);
        right_btn = view.findViewById(R.id.right_btn);
        choose_date_tv = view.findViewById(R.id.choose_date_tv);
        total_time_tv = view.findViewById(R.id.total_time_tv);
        upperlimb_info = view.findViewById(R.id.upperlimb_info);
        upperlimb_img = view.findViewById(R.id.upperlimb_img);
        upperlimb_time = view.findViewById(R.id.upperlimb_time);
        upperlimb_background = view.findViewById(R.id.upperlimb_background);
        lowerlimb_info = view.findViewById(R.id.lowerlimb_info);
        lowerlimb_img = view.findViewById(R.id.lowerlimb_img);
        lowerlimb_time = view.findViewById(R.id.lowerlimb_time);
        lowerlimb_background = view.findViewById(R.id.lowerlimb_background);
        softness_info = view.findViewById(R.id.softness_info);
        softness_img = view.findViewById(R.id.softness_img);
        softness_time = view.findViewById(R.id.softness_time);
        softness_background = view.findViewById(R.id.softness_background);
        endurance_info = view.findViewById(R.id.endurance_info);
        endurance_img = view.findViewById(R.id.endurance_img);
        endurance_time = view.findViewById(R.id.endurance_time);
        endurance_background = view.findViewById(R.id.endurance_background);
        barChart = view.findViewById(R.id.bar_chart);
    }

    public void setInit(){
        diaryList = myDBHelper.getDiaryInfo();
        weekList = this.getWeekList();
        reportDateList = this.getReportDateList();

        currentWeek = weekNum;
        generateReport();

    }

    public ArrayList<WeekInfo> getWeekList() {
        ArrayList<WeekInfo> totalWeekData = new ArrayList<>();
        if(this.diaryList.size() % 7 == 0){
            weekNum = (this.diaryList.size() / 7);
        }else{
            weekNum = (this.diaryList.size() / 7) + 1;
        }
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

            if((i+1)*7 > this.diaryList.size()){
                for(int j=0;j<(this.diaryList.size()%7);j++){
                    DiaryInfo diaryInfo = this.diaryList.get(i*7+j);
                    if(j==0){
                        dateStart = diaryInfo.getDate();
                    }else if(j==(this.diaryList.size()%7-1)){
                        dateEnd = diaryInfo.getDate();
                    }
                    totalUpperlimb += diaryInfo.getUpperlimb();
                    totalUpperlimb += diaryInfo.getUpperrope();
                    totalLowerlimb += diaryInfo.getLowerlimb();
                    totalLowerlimb += diaryInfo.getLowerrope();
                    totalSoftness += diaryInfo.getSoftness();
                    totalEndurance += diaryInfo.getEndurance();
                }
            }else{
                for(int j=0;j<7;j++){
                    DiaryInfo diaryInfo = this.diaryList.get(i*7+j);
                    if(j==0){
                        dateStart = diaryInfo.getDate();
                    }else if(j==6){
                        dateEnd = diaryInfo.getDate();
                    }
                    totalUpperlimb += diaryInfo.getUpperlimb();
                    totalUpperlimb += diaryInfo.getUpperrope();
                    totalLowerlimb += diaryInfo.getLowerlimb();
                    totalLowerlimb += diaryInfo.getLowerrope();
                    totalSoftness += diaryInfo.getSoftness();
                    totalEndurance += diaryInfo.getEndurance();
                }
            }

            weekInfo.init(dateStart,dateEnd,totalUpperlimb,totalLowerlimb,totalSoftness,totalEndurance);
            totalWeekData.add(weekInfo);
        }
        return  totalWeekData;
    }

    public ArrayList<String> getReportDateList(){
        ArrayList<String> totalReportDateData = new ArrayList<>();

        for(int i=0;i<this.weekList.size();i++){
            WeekInfo weekInfo = this.weekList.get(i);
            String reportDateInfo = getDateFormat(weekInfo.getDateStart()) + "~" + getDateFormat(weekInfo.getDateEnd());
            totalReportDateData.add(reportDateInfo);
        }
        return totalReportDateData;
    }

    public String getDateFormat(String date){
        int date_num = Integer.parseInt(date);
        int year = date_num / 10000;
        int month = (date_num % 10000) / 100;
        int day = date_num % 100;

        return String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
    }

    public String getDateLabel(String date){
        int date_num = Integer.parseInt(date);
        int day = date_num % 100;

        return String.valueOf(day) + "日";
    }

    public void buttonClickEvent(){
        left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentWeek > 1){
                    currentWeek--;
                    generateReport();
                }
            }
        });

        right_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentWeek < weekNum){
                    currentWeek++;
                    generateReport();
                }
            }
        });
    }

    public void generateReport(){
        choose_date_tv.setText(reportDateList.get(currentWeek-1));
        setReportCard(currentWeek);
        barChart.clear();
        setBarChart();
    }

    public void setReportCard(int choseWeek) {
        WeekInfo weekInfo = weekList.get(choseWeek-1);

        int upperlimb = weekInfo.getUpperlimb()*3;
        int lowerlimb = weekInfo.getLowerlimb()*3;
        int softness = weekInfo.getSoftness()*3;
        int endurance = weekInfo.getEndurance()*3;

        // total time
        int totalTime = upperlimb + lowerlimb + softness + endurance;
        total_time_tv.setText(totalTime + " 分鐘");

        //upperlimb
        if(upperlimb < 20){
            upperlimb_img.setImageDrawable(getResources().getDrawable(R.drawable.miss_standand));
            upperlimb_info.setText("您的上肢訓練可能不足，可以多加強此項！");
            upperlimb_time.setText(upperlimb + " 分鐘");
            upperlimb_background.setBackground(getResources().getDrawable(R.drawable.failed_card_form));
        }else if(upperlimb >= 20 && upperlimb < 45){
            upperlimb_img.setImageDrawable(getResources().getDrawable(R.drawable.hit_standard));
            upperlimb_info.setText("就快達到上肢肌力訓練量，請繼續加油！");
            upperlimb_time.setText(upperlimb + " 分鐘");
            upperlimb_background.setBackground(getResources().getDrawable(R.drawable.complete_card_form));
        }else{
            upperlimb_img.setImageDrawable(getResources().getDrawable(R.drawable.over_standand));
            upperlimb_info.setText("已達到上肢肌力訓練量，很厲害喔！");
            upperlimb_time.setText(upperlimb + " 分鐘");
            upperlimb_background.setBackground(getResources().getDrawable(R.drawable.complete_card_form));
        }

        // lowerlimb
        if(lowerlimb < 20){
            lowerlimb_img.setImageDrawable(getResources().getDrawable(R.drawable.miss_standand));
            lowerlimb_info.setText("您的下肢訓練可能不足，可以多加強此項！");
            lowerlimb_time.setText(lowerlimb + " 分鐘");
            lowerlimb_background.setBackground(getResources().getDrawable(R.drawable.failed_card_form));
        }else if(lowerlimb >= 20 && lowerlimb < 45){
            lowerlimb_img.setImageDrawable(getResources().getDrawable(R.drawable.hit_standard));
            lowerlimb_info.setText("就快達到下肢肌力訓練量，請繼續加油！");
            lowerlimb_time.setText(lowerlimb + " 分鐘");
            lowerlimb_background.setBackground(getResources().getDrawable(R.drawable.complete_card_form));
        }else{
            lowerlimb_img.setImageDrawable(getResources().getDrawable(R.drawable.over_standand));
            lowerlimb_info.setText("已達到下肢肌力訓練量，很厲害喔！");
            lowerlimb_time.setText(lowerlimb + " 分鐘");
            lowerlimb_background.setBackground(getResources().getDrawable(R.drawable.complete_card_form));
        }

        // softness
        if(softness < 20){
            softness_img.setImageDrawable(getResources().getDrawable(R.drawable.miss_standand));
            softness_info.setText("您的柔軟訓練可能不足，可以多加強此項！");
            softness_time.setText(softness + " 分鐘");
            softness_background.setBackground(getResources().getDrawable(R.drawable.failed_card_form));
        }else if(softness >= 20 && softness < 45){
            softness_img.setImageDrawable(getResources().getDrawable(R.drawable.hit_standard));
            softness_info.setText("就快達到柔軟訓練訓練量，請繼續加油！");
            softness_time.setText(softness + " 分鐘");
            softness_background.setBackground(getResources().getDrawable(R.drawable.complete_card_form));
        }else{
            softness_img.setImageDrawable(getResources().getDrawable(R.drawable.over_standand));
            softness_info.setText("已達到柔軟訓練訓練量，很厲害喔！");
            softness_time.setText(softness + " 分鐘");
            softness_background.setBackground(getResources().getDrawable(R.drawable.complete_card_form));
        }

        // endurance
        if(endurance < 20){
            endurance_img.setImageDrawable(getResources().getDrawable(R.drawable.miss_standand));
            endurance_info.setText("您的耐力訓練可能不足，可以多加強此項！");
            endurance_time.setText(endurance + " 分鐘");
            endurance_background.setBackground(getResources().getDrawable(R.drawable.failed_card_form));
        }else if(endurance >= 20 && endurance < 45){
            endurance_img.setImageDrawable(getResources().getDrawable(R.drawable.hit_standard));
            endurance_info.setText("就快達到耐力訓練訓練量，請繼續加油！");
            endurance_time.setText(endurance + " 分鐘");
            endurance_background.setBackground(getResources().getDrawable(R.drawable.complete_card_form));
        }else{
            endurance_img.setImageDrawable(getResources().getDrawable(R.drawable.over_standand));
            endurance_info.setText("已達到耐力訓練訓練量，很厲害喔！");
            endurance_time.setText(endurance + " 分鐘");
            endurance_background.setBackground(getResources().getDrawable(R.drawable.complete_card_form));
        }

    }

    public void setBarChart(){
        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();
        XAxis xAxis = barChart.getXAxis();
        Legend legend = barChart.getLegend();
        Description description = barChart.getDescription();

        barChart.setData(getBarData());
        barChart.setDrawGridBackground(false);
        barChart.setTouchEnabled(false);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(getResources().getColor(R.color.dark_tan));
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getLabels()));

        leftAxis.setGranularity(30);
        leftAxis.setAxisMinimum(0);
        leftAxis.setAxisMaximum(140);
        leftAxis.setTextColor(getResources().getColor(R.color.dark_tan));
        leftAxis.setLabelCount(4, false);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);

        rightAxis.setEnabled(false);
        description.setEnabled(false);
        legend.setEnabled(false);

    }

    public BarData getBarData(){
        BarDataSet dataSet = new BarDataSet(getChartData(),"時數");
        dataSet.setColor(getResources().getColor(R.color.green_txt));
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.4f);
        barData.setValueTextColor(getResources().getColor(R.color.green_txt));

        return barData;
    }

    public ArrayList<BarEntry> getChartData(){
        ArrayList<BarEntry> chartData = new ArrayList<>();

        if(currentWeek < weekNum){
            for(int i=0;i<7;i++){
                DiaryInfo diaryInfo = this.diaryList.get((currentWeek-1)*7+i);
                int exerciseTime = (diaryInfo.getUpperlimb()+diaryInfo.getLowerlimb()
                                    +diaryInfo.getSoftness()+diaryInfo.getEndurance()
                                    +diaryInfo.getUpperrope()+diaryInfo.getLowerrope())*3;
                chartData.add(new BarEntry(i,exerciseTime));
            }
        }else{
            if(this.diaryList.size()%7 == 0){
                for(int i=0;i<7;i++){
                    DiaryInfo diaryInfo = this.diaryList.get((currentWeek-1)*7+i);
                    int exerciseTime = (diaryInfo.getUpperlimb()+diaryInfo.getLowerlimb()
                            +diaryInfo.getSoftness()+diaryInfo.getEndurance()
                            +diaryInfo.getUpperrope()+diaryInfo.getLowerrope())*3;
                    chartData.add(new BarEntry(i,exerciseTime));
                }
            }else{
                for(int i=0;i<this.diaryList.size()%7;i++){
                    DiaryInfo diaryInfo = this.diaryList.get((currentWeek-1)*7+i);
                    int exerciseTime = (diaryInfo.getUpperlimb()+diaryInfo.getLowerlimb()
                            +diaryInfo.getSoftness()+diaryInfo.getEndurance()
                            +diaryInfo.getUpperrope()+diaryInfo.getLowerrope())*3;
                    chartData.add(new BarEntry(i,exerciseTime));
                }
                for(int i=this.diaryList.size()%7;i<7;i++){
                    chartData.add(new BarEntry(i,0));
                }
            }
        }

        return chartData;
    }

    public ArrayList getLabels(){
        ArrayList<String> chartLabels = new ArrayList<>();
        if(currentWeek < weekNum){
            for(int i=0;i<7;i++){
                DiaryInfo diaryInfo = this.diaryList.get((currentWeek-1)*7+i);
                String date = getDateLabel(diaryInfo.getDate());
                chartLabels.add(date);
            }
        }else{
            if(this.diaryList.size()%7 == 0){
                for(int i=0;i<7;i++){
                    DiaryInfo diaryInfo = this.diaryList.get((currentWeek-1)*7+i);
                    String date = getDateLabel(diaryInfo.getDate());
                    chartLabels.add(date);
                }
            }else{
                for(int i=0;i<this.diaryList.size()%7;i++){
                    DiaryInfo diaryInfo = this.diaryList.get((currentWeek-1)*7+i);
                    String date = getDateLabel(diaryInfo.getDate());
                    chartLabels.add(date);
                }
                for(int i=this.diaryList.size()%7;i<7;i++){
                    chartLabels.add("");
                }
            }
        }
        return  chartLabels;
    }


}