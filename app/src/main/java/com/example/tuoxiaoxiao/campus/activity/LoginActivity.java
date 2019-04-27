package com.example.tuoxiaoxiao.campus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.uitest.administrator.shoulderby.R;

/*
    登陆界面
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_login_login;
    private Button btn_login_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login_login = findViewById(R.id.btn_login_login);
        btn_login_register = findViewById(R.id.btn_login_register);

        btn_login_login.setOnClickListener(this);
        btn_login_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_login:
                Intent login = new Intent(this,MainActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(login);
                break;
            case R.id.btn_login_register:
                Intent register = new Intent(this,MainActivity.class);
                startActivity(register);
                break;
        }
    }
}

