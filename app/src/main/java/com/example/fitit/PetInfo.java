package com.example.fitit;

public class PetInfo {
    private int upperlimb;
    private int lowerlimb;
    private int softness;
    private int endurance;

    public void init(int upperlimb,int lowerlimb,int softness,int endurance){
        this.upperlimb = upperlimb;
        this.lowerlimb = lowerlimb;
        this.softness = softness;
        this.endurance = endurance;
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
