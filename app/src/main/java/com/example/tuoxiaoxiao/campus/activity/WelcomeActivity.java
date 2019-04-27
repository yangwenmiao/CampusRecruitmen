package com.example.tuoxiaoxiao.campus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.tuoxiaoxiao.campus.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 系统欢迎界面
 * @author ywm
 */
public class WelcomeActivity extends AppCompatActivity {
    @Bind(R.id.tv_welcome_name)
    TextView tvWelcomeName;
    @Bind(R.id.tv_welcome_version)
    TextView tvWelcomeVersion;
    @Bind(R.id.rl_welcome)
    RelativeLayout rlWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        getSupportActionBar().hide();
        // 隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        //设置等待时间，单位为毫秒
        Integer time = 8000;
        Handler handler = new Handler();
        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(com.example.tuoxiaoxiao.campus.activity.WelcomeActivity.this, LoginActivity.class));
                com.example.tuoxiaoxiao.campus.activity.WelcomeActivity.this.finish();
            }
        }, time);
    }
}

