package com.example.fitit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button start_btn,register_btn;

    private ArrayList<AccountInfo> accountInfo = new ArrayList<>();
    private String accountName="";
    private String accountPassword="";
    private DBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findObject();
        buttonClickEvent();
        invisibleRegister();
    }

    public void findObject(){
        register_btn = findViewById(R.id.register_btn);
        start_btn = findViewById(R.id.start_btn);
    }

    public void getAccount(){
        myDBHelper = new DBHelper(this);
        accountInfo = myDBHelper.getAccountInfo();
        if(accountInfo.size()!=0){
            accountName = accountInfo.get(0).getUserName();
            accountPassword = accountInfo.get(0).getUserPassword();
        }
    }

    public void login(){
        if(accountName != "" && accountPassword != ""){
            Intent intent = new Intent();
            intent.putExtra("user_name",accountName);
            intent.setClass(MainActivity.this,Home.class);
            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this,"尚未建立帳號",Toast.LENGTH_SHORT).show();
        }
    }

    public void invisibleRegister(){
        getAccount();
        if(accountName != "" && accountPassword != ""){
            register_btn.setVisibility(View.INVISIBLE);
        }
    }

    public void buttonClickEvent(){
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAccount();
                login();
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,Register.class);
                startActivity(intent);
            }
        });

    }

}