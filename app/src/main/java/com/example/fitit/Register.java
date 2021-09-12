package com.example.fitit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Register extends AppCompatActivity {
    private Button confirm_btn,back_btn;
    private EditText name_ed,password_ed;

    private ArrayList<AccountInfo> accountInfo = new ArrayList<>();
    private DBHelper myDBHelper = new DBHelper(Register.this);

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findObject();
        buttonClickEvent();
        checkRegister();
    }

    public void findObject(){
        name_ed = findViewById(R.id.name);
        password_ed = findViewById(R.id.password);
        confirm_btn = findViewById(R.id.register_btn);
        back_btn = findViewById(R.id.login_btn);
    }

    public void register(){
        String user_name = name_ed.getText().toString();
        String user_password = password_ed.getText().toString();

        if(user_name.length() != 0 && user_password.length() != 0){
            // write into DB
            this.myDBHelper.insertToAccount(user_name,user_password);
            // set global variable
            sharedPreferences = getSharedPreferences("User",MODE_PRIVATE);
            sharedPreferences.edit().putBoolean("guide",true).apply();

            finish();
        }else {
            Toast.makeText(Register.this,"您的資料不完整，請重新再試",Toast.LENGTH_SHORT).show();
        }

    }

    public void checkRegister(){
        ArrayList<AccountInfo> accountInfo = new ArrayList<>();
        accountInfo = myDBHelper.getAccountInfo();
        if(accountInfo.size() != 0){
            Toast.makeText(Register.this,"請勿重複註冊",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void buttonClickEvent(){
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}