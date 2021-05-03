package com.example.fitit;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
    private TextView upperlimb_time,lowerlimb_time,softness_time,endurance_time;
    private TextView upperlimb_suggest,lowerlimb_suggest,softness_suggest,endurance_suggest;
    private LinearLayout upperlimb_background,lowerlimb_background,softness_background,endurance_background;
    private ImageView upperlimb_img,lowerlimb_img,softness_img,endurance_img;

    private BarChart barChart;

    private DBHelper myDBHelper;
    private ArrayList<DiaryInfo> diaryList = new ArrayList<>();
    private ArrayList<WeekInfo> weekList = new ArrayList<>();
    private ArrayList<String> reportDateList = new ArrayList<>();

    private int weekNum = 0;
    private int currentWeek = 0;
    private String upperlimb_info = "",lowerlimb_info = "",softness_info = "",endurance_info = "";

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
        //popupWindow();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setInit();
    }

    public void findObject(View view){
        left_btn = view.findViewById(R.id.left_btn);
        right_btn = view.findViewById(R.id.right_btn);
        choose_date_tv = view.findViewById(R.id.choose_date_tv);
        total_time_tv = view.findViewById(R.id.total_time_tv);
        upperlimb_img = view.findViewById(R.id.upperlimb_img);
        upperlimb_suggest = view.findViewById(R.id.upperlimb_suggest);
        upperlimb_time = view.findViewById(R.id.upperlimb_time);
        upperlimb_background = view.findViewById(R.id.upperlimb_background);
        lowerlimb_img = view.findViewById(R.id.lowerlimb_img);
        lowerlimb_suggest = view.findViewById(R.id.lowerlimb_suggest);
        lowerlimb_time = view.findViewById(R.id.lowerlimb_time);
        lowerlimb_background = view.findViewById(R.id.lowerlimb_background);
        softness_img = view.findViewById(R.id.softness_img);
        softness_suggest = view.findViewById(R.id.softness_suggest);
        softness_time = view.findViewById(R.id.softness_time);
        softness_background = view.findViewById(R.id.softness_background);
        endurance_img = view.findViewById(R.id.endurance_img);
        endurance_suggest = view.findViewById(R.id.endurance_suggest);
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
                        if(this.diaryList.size()%7 == 1){
                            dateEnd = diaryInfo.getDate();
                        }
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
            String reportDateInfo = getDateFormat(weekInfo.getDateStart()) + " ~ " + getDateFormat(weekInfo.getDateEnd());
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
            upperlimb_img.setImageDrawable(getResources().getDrawable(R.drawable.greenbadface));
            upperlimb_time.setText(upperlimb + " 分鐘");
            upperlimb_time.setTextColor(getResources().getColor(R.color.green_txt));
            //upperlimb_background.setBackground(getResources().getDrawable(R.drawable.failed_card_form));
            upperlimb_info = "上肢訓練\n上肢肌力訓練有些不足，可以多加強這項！";
            upperlimb_suggest.setText(upperlimb_info);
            upperlimb_suggest.setTextColor(getResources().getColor(R.color.green_txt));
        }else if(upperlimb >= 20 && upperlimb < 45){
            upperlimb_img.setImageDrawable(getResources().getDrawable(R.drawable.facial_smile));
            upperlimb_time.setText(upperlimb + " 分鐘");
            upperlimb_time.setTextColor(getResources().getColor(R.color.tablayout_dark));
            //upperlimb_background.setBackground(getResources().getDrawable(R.drawable.complete_card_form));
            upperlimb_info = "上肢訓練\n快達到上肢肌力訓練量，加油！";
            upperlimb_suggest.setText(upperlimb_info);
            upperlimb_suggest.setTextColor(getResources().getColor(R.color.tablayout_dark));
        }else{
            upperlimb_img.setImageDrawable(getResources().getDrawable(R.drawable.facial_laugh));
            upperlimb_time.setText(upperlimb + " 分鐘");
            upperlimb_time.setTextColor(getResources().getColor(R.color.tablayout_dark));
            //upperlimb_background.setBackground(getResources().getDrawable(R.drawable.complete_card_form));
            upperlimb_info = "上肢訓練\n已達到上肢肌力訓練量，讚！";
            upperlimb_suggest.setText(upperlimb_info);
            upperlimb_suggest.setTextColor(getResources().getColor(R.color.tablayout_dark));
        }

        // lowerlimb
        if(lowerlimb < 20){
            lowerlimb_img.setImageDrawable(getResources().getDrawable(R.drawable.greenbadface));
            lowerlimb_time.setText(lowerlimb + " 分鐘");
            lowerlimb_time.setTextColor(getResources().getColor(R.color.green_txt));
            //lowerlimb_background.setBackground(getResources().getDrawable(R.drawable.failed_card_form));
            lowerlimb_info = "下肢訓練\n下肢肌力訓練有些不足，可以多加強這項！";
            lowerlimb_suggest.setText(lowerlimb_info);
            lowerlimb_suggest.setTextColor(getResources().getColor(R.color.green_txt));
        }else if(lowerlimb >= 20 && lowerlimb < 45){
            lowerlimb_img.setImageDrawable(getResources().getDrawable(R.drawable.facial_smile));
            lowerlimb_time.setText(lowerlimb + " 分鐘");
            lowerlimb_time.setTextColor(getResources().getColor(R.color.tablayout_dark));
            //lowerlimb_background.setBackground(getResources().getDrawable(R.drawable.complete_card_form));
            lowerlimb_info = "下肢訓練\n快達到下肢肌力訓練量，加油！";
            lowerlimb_suggest.setText(lowerlimb_info);
            lowerlimb_suggest.setTextColor(getResources().getColor(R.color.tablayout_dark));
        }else{
            lowerlimb_img.setImageDrawable(getResources().getDrawable(R.drawable.facial_laugh));
            lowerlimb_time.setText(lowerlimb + " 分鐘");
            lowerlimb_time.setTextColor(getResources().getColor(R.color.tablayout_dark));
            //lowerlimb_background.setBackground(getResources().getDrawable(R.drawable.complete_card_form));
            lowerlimb_info = "下肢訓練\n已達到下肢肌力訓練量，讚！";
            lowerlimb_suggest.setText(lowerlimb_info);
            lowerlimb_suggest.setTextColor(getResources().getColor(R.color.tablayout_dark));
        }

        // softness
        if(softness < 20){
            softness_img.setImageDrawable(getResources().getDrawable(R.drawable.greenbadface));
            softness_time.setText(softness + " 分鐘");
            softness_time.setTextColor(getResources().getColor(R.color.green_txt));
            //softness_background.setBackground(getResources().getDrawable(R.drawable.failed_card_form));
            softness_info = "柔軟訓練\n柔軟訓練有些不足，可以多加強這項！";
            softness_suggest.setText(softness_info);
            softness_suggest.setTextColor(getResources().getColor(R.color.green_txt));
        }else if(softness >= 20 && softness < 45){
            softness_img.setImageDrawable(getResources().getDrawable(R.drawable.facial_smile));
            softness_time.setText(softness + " 分鐘");
            softness_time.setTextColor(getResources().getColor(R.color.tablayout_dark));
            //softness_background.setBackground(getResources().getDrawable(R.drawable.complete_card_form));
            softness_info = "柔軟訓練\n快達到柔軟訓練量，加油！";
            softness_suggest.setText(softness_info);
            softness_suggest.setTextColor(getResources().getColor(R.color.tablayout_dark));
        }else{
            softness_img.setImageDrawable(getResources().getDrawable(R.drawable.facial_laugh));
            softness_time.setText(softness + " 分鐘");
            softness_time.setTextColor(getResources().getColor(R.color.tablayout_dark));
            //softness_background.setBackground(getResources().getDrawable(R.drawable.complete_card_form));
            softness_info = "柔軟訓練\n已達到柔軟訓練量，讚！";
            softness_suggest.setText(softness_info);
            softness_suggest.setTextColor(getResources().getColor(R.color.tablayout_dark));
        }

        // endurance
        if(endurance < 20){
            endurance_img.setImageDrawable(getResources().getDrawable(R.drawable.greenbadface));
            endurance_time.setText(endurance + " 分鐘");
            endurance_time.setTextColor(getResources().getColor(R.color.green_txt));
            //endurance_background.setBackground(getResources().getDrawable(R.drawable.failed_card_form));
            endurance_info = "耐力訓練\n耐力訓練有些不足，可以多加強這項！";
            endurance_suggest.setText(endurance_info);
            endurance_suggest.setTextColor(getResources().getColor(R.color.green_txt));
        }else if(endurance >= 20 && endurance < 45){
            endurance_img.setImageDrawable(getResources().getDrawable(R.drawable.facial_smile));
            endurance_time.setText(endurance + " 分鐘");
            endurance_time.setTextColor(getResources().getColor(R.color.tablayout_dark));
            //endurance_background.setBackground(getResources().getDrawable(R.drawable.complete_card_form));
            endurance_info = "耐力訓練\n快達到耐力訓練量，加油！";
            endurance_suggest.setText(endurance_info);
            endurance_suggest.setTextColor(getResources().getColor(R.color.tablayout_dark));
        }else{
            endurance_img.setImageDrawable(getResources().getDrawable(R.drawable.facial_laugh));
            endurance_time.setText(endurance + " 分鐘");
            endurance_time.setTextColor(getResources().getColor(R.color.tablayout_dark));
            //endurance_background.setBackground(getResources().getDrawable(R.drawable.complete_card_form));
            endurance_info = "耐力訓練\n已達到耐力訓練量，讚！";
            endurance_suggest.setText(endurance_info);
            endurance_suggest.setTextColor(getResources().getColor(R.color.tablayout_dark));
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
        barChart.setGridBackgroundColor(getResources().getColor(R.color.dark_tan));

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(12);
        xAxis.setTextColor(getResources().getColor(R.color.dark_tan));
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getLabels()));

        leftAxis.setGranularity(30);
        leftAxis.setAxisMinimum(0);
        leftAxis.setAxisMaximum(100);
        leftAxis.setTextSize(14);
        leftAxis.setTextColor(getResources().getColor(R.color.dark_tan));
        leftAxis.setLabelCount(4, false);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setValueFormatter(new YLabelFormat());

        rightAxis.setEnabled(false);
        description.setEnabled(false);
        legend.setEnabled(false);

    }

    public BarData getBarData(){
        BarDataSet dataSet = new BarDataSet(getChartData(),"時數");
        dataSet.setColor(getResources().getColor(R.color.green_txt));
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.3f);
        barData.setDrawValues(false);
//        barData.setValueFormatter(new DataValueFormat());
//        barData.setValueTextSize(12);
//        barData.setValueTextColor(getResources().getColor(R.color.green_txt));

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

    class YLabelFormat extends IndexAxisValueFormatter{
        @Override
        public String getFormattedValue(float value) {
            return (int)value + "分鐘";
        }
    }

    class DataValueFormat extends IndexAxisValueFormatter{
        @Override
        public String getFormattedValue(float value) {
            return (int)value + "";
        }
    }

    public void popupWindow(){
        upperlimb_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popInfo(v,1);
                backgroundAlpha(0.3f);
            }
        });

        lowerlimb_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popInfo(v,2);
                backgroundAlpha(0.3f);
            }
        });

        softness_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popInfo(v,3);
                backgroundAlpha(0.3f);
            }
        });

        endurance_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popInfo(v,4);
                backgroundAlpha(0.3f);
            }
        });
    }

    public void popInfo(View v,int index){
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.report_popup, null, false);
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL,0,0);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
        LinearLayout report_info_background = (LinearLayout)view.findViewById(R.id.report_info_background);
        ImageView report_info_img = (ImageView)view.findViewById(R.id.report_info_img);
        TextView report_info_time = (TextView)view.findViewById(R.id.report_info_time);
        TextView report_info_tv = (TextView)view.findViewById(R.id.report_info_tv);
        Button close_btn = (Button)view.findViewById(R.id.close_btn);

        if(index == 1){
            report_info_background.setBackground(upperlimb_background.getBackground());
            report_info_img.setImageDrawable(upperlimb_img.getDrawable());
            report_info_time.setText(upperlimb_time.getText());
            report_info_tv.setText(upperlimb_info);
        }else if(index == 2){
            report_info_background.setBackground(lowerlimb_background.getBackground());
            report_info_img.setImageDrawable(lowerlimb_img.getDrawable());
            report_info_time.setText(lowerlimb_time.getText());
            report_info_tv.setText(lowerlimb_info);
        }else if(index == 3){
            report_info_background.setBackground(softness_background.getBackground());
            report_info_img.setImageDrawable(softness_img.getDrawable());
            report_info_time.setText(softness_time.getText());
            report_info_tv.setText(softness_info);
        }else if(index == 4){
            report_info_background.setBackground(endurance_background.getBackground());
            report_info_img.setImageDrawable(endurance_img.getDrawable());
            report_info_time.setText(endurance_time.getText());
            report_info_tv.setText(endurance_info);
        }

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });

    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        this.getActivity().getWindow().setAttributes(lp); //act 是上下文context
    }

}