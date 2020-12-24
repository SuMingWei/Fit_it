package com.example.fitit;

public class WeekInfo {
    private String dateStart;
    private String dateEnd;
    private int upperlimb;
    private int lowerlimb;
    private int softness;
    private int endurance;

    public void init(String dateStart,String dateEnd,int upperlimb,int lowerlimb,int softness,int endurance){
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.upperlimb = upperlimb;
        this.lowerlimb = lowerlimb;
        this.softness = softness;
        this.endurance = endurance;
    }

    public String getDateStart(){
        return this.dateStart;
    }

    public String getDateEnd(){
        return  this.dateEnd;
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
}
