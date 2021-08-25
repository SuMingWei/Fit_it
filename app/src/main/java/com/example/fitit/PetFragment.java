package com.example.fitit;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Intent.getIntent;

public class PetFragment extends Fragment {
    private TextView username,level,level_value,dialog_tv,closeness_tv;
    private ImageView pet,pet_front;
    private ProgressBar level_bar;

    private DBHelper myDBHelper;
    private ArrayList<PetInfo> petInfo = new ArrayList<>();
    private ArrayList<DiaryInfo> diaryList = new ArrayList<>();

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pet, container, false);

        myDBHelper = new DBHelper(container.getContext());

        findObject(view);
        setUsername();
        updatePetInfo();
        newDiaryInfo();
        setCloseness();
        buttonClickEvent();

        // guide
        sharedPreferences = this.getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("guide",false) == true){
            //Toast.makeText(this.getActivity(),"hahhahaahah",Toast.LENGTH_SHORT).show();
            showDialog(view);
            sharedPreferences.edit().putBoolean("guide",false).commit();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePetInfo();
        newDiaryInfo();
        setCloseness();
    }

    public void findObject(View view){
        username = view.findViewById(R.id.username);
        level = view.findViewById(R.id.level);
        level_value = view.findViewById(R.id.value);
        level_bar = view.findViewById(R.id.level_bar);
        closeness_tv = view.findViewById(R.id.closeness);
        pet_front = view.findViewById(R.id.pet_front);
        pet = view.findViewById(R.id.pet);
        dialog_tv = view.findViewById(R.id.dialog);
    }

    public void setUsername(){
        Bundle bundle = this.getArguments();
        String name = bundle.getString("account_name");
        username.setText(name + "的小狗");
    }

    public void changeDialog(){
//        String dialog[] = {
//                "\n肌力訓練：\n可以強化骨頭和肌肉，\n防止因跌倒而骨折。\n",
//                "\n柔軟訓練：\n可以訓練平衡，預防跌倒，\n也可以防止肌肉僵化。\n",
//                "\n心肺訓練：\n可以提升心血管健康，\n增加全身代謝量\n",
//                "\n每日運動可增加寵物好感度\n"};
//
//        int num = (int)(Math.random()*4);
//        dialog_tv.setText(dialog[num]);
        dialog_tv.setText("\n點我可以查看\n更多寵物資訊！\n");
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

    public void setCloseness(){
        if(diaryList.size() < 2){
            closeness_tv.setText("好感度： 普通");
            return;
        }
        DiaryInfo yesInfo = diaryList.get(diaryList.size()-2);
        DiaryInfo todayInfo = diaryList.get(diaryList.size()-1);
        int yesExe = yesInfo.getUpperlimb()+yesInfo.getLowerlimb()+yesInfo.getSoftness()+yesInfo.getEndurance()+yesInfo.getUpperrope()+yesInfo.getLowerrope();
        int todayExe = todayInfo.getUpperlimb()+todayInfo.getLowerlimb()+todayInfo.getSoftness()+todayInfo.getEndurance()+todayInfo.getUpperrope()+todayInfo.getLowerrope();

        if(yesExe == 0 && todayExe == 0){
            closeness_tv.setText("好感度： 陌生");
        }else if((yesExe > 0 && todayExe == 0) || (yesExe == 0 && todayExe > 0)){
            closeness_tv.setText("好感度： 普通");
        }else {
            closeness_tv.setText("好感度： 親密");
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
            //insert();
            myDBHelper.insertToDiary(getCurrentDate(),0,0,0,0,0,0);
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
        //Toast.makeText(this,String.valueOf(days),Toast.LENGTH_SHORT).show();

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
            myDBHelper.insertToDiary(new_date,0,0,0,0,0,0);
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

        pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),Attribute.class);
                startActivity(intent);
            }
        });
        dialog_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),Attribute.class);
                startActivity(intent);
            }
        });

        pet_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v);
            }
        });
    }

    public void showDialog(View view){
        Dialog dialog = new Dialog(this.getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(R.layout.pager_layout);

        List<PagerModel> pagerArr = new ArrayList<>();
        pagerArr.add(new PagerModel("1",R.drawable.guide_pic1));
        pagerArr.add(new PagerModel("2",R.drawable.guide_pic2));
        pagerArr.add(new PagerModel("3",R.drawable.guide_pic3));
        pagerArr.add(new PagerModel("4",R.drawable.guide_pic4));
        pagerArr.add(new PagerModel("5",R.drawable.guide_pic5));
        pagerArr.add(new PagerModel("6",R.drawable.guide_pic6));
        pagerArr.add(new PagerModel("7",R.drawable.guide_pic7));
        pagerArr.add(new PagerModel("8",R.drawable.guide_pic8));
        pagerArr.add(new PagerModel("9",R.drawable.guide_pic9));

        GuidePagerAdapter adapter = new GuidePagerAdapter(this.getActivity(),pagerArr);
        ViewPager pager = dialog.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        DotsIndicator indicator = (DotsIndicator) dialog.findViewById(R.id.dots_indicator);
        indicator.setViewPager(pager);

        dialog.show();

    }

    // test data
    public void insert(){
        myDBHelper.insertToDiary("20201205",1,3,2,1,0,0);
        myDBHelper.insertToDiary("20201206",1,1,1,1,0,0);
        myDBHelper.insertToDiary("20201207",2,0,1,1,0,0);
        myDBHelper.insertToDiary("20201208",3,1,2,1,0,0);
        myDBHelper.insertToDiary("20201209",1,3,2,1,0,0);
        myDBHelper.insertToDiary("20201210",1,1,1,1,0,0);
        myDBHelper.insertToDiary("20201211",2,0,1,1,0,0);
        myDBHelper.insertToDiary("20201212",3,1,2,1,0,0);
        myDBHelper.insertToDiary("20201213",1,3,2,1,0,0);
        myDBHelper.insertToDiary("20201214",1,1,1,1,0,0);
        myDBHelper.insertToDiary("20201215",2,0,1,1,0,0);
        myDBHelper.insertToDiary("20201216",3,1,2,1,0,0);
        myDBHelper.insertToDiary("20201217",1,3,2,1,0,0);
        myDBHelper.insertToDiary("20201218",1,1,1,1,0,0);
        myDBHelper.insertToDiary("20201219",2,0,1,1,0,0);
        myDBHelper.insertToDiary("20201220",3,1,2,1,0,0);
        myDBHelper.insertToDiary("20201221",1,3,2,1,0,0);
        myDBHelper.insertToDiary("20201222",1,1,1,1,0,0);
        myDBHelper.insertToDiary("20201223",2,0,1,1,0,0);
        myDBHelper.insertToDiary("20201224",3,1,2,1,0,0);
        myDBHelper.insertToDiary("20201225",3,3,2,3,0,0);
        myDBHelper.insertToDiary("20201226",3,3,3,3,0,0);
        myDBHelper.insertToDiary("20201227",12,13,11,13,0,0);
        myDBHelper.insertToDiary("20201228",13,13,12,13,0,0);
        myDBHelper.updateToPet(1,66,57,58,52);
    }


}