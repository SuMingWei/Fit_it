package com.example.fitit;

public class DiaryInfo {
    private String date;
    private int upperlimb;
    private int lowerlimb;
    private int softness;
    private int endurance;
    private int upperrope;
    private int lowerrope;

    public void init(String date,int upperlimb,int lowerlimb,int softness,int endurance,int upperrope,int lowerrope){
        this.date = date;
        this.upperlimb = upperlimb;
        this.lowerlimb = lowerlimb;
        this.softness = softness;
        this.endurance = endurance;
        this.upperrope = upperrope;
        this.lowerrope = lowerrope;
    }

    public String getDate(){
        return this.date;
    }

    public int getUpperlimb(){
        return this.upperlimb;
    }

    public int getLowerlimb(){
        return this.lowerlimb;
    }

    public int getSoftness(){
        return this.softness;
    }

    public int getEndurance(){
        return this.endurance;
    }

    public int getUpperrope() {
        return this.upperrope;
    }

    public int getLowerrope() {
        return this.lowerrope;
    }


}
