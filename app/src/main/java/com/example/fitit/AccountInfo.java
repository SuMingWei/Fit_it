package com.example.fitit;

public class AccountInfo {
    private String name;
    private String password;

    public void init(String name,String password){
        this.name = name;
        this.password =password;
    }

    public String getUserName(){
        return this.name;
    }

    public String getUserPassword(){
        return this.password;
    }
}
