package com.example.tuoxiaoxiao.campus.activity;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuoxiaoxiao.campus.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 系统主界面
 * @author  ywm
 */
public class MainActivity extends FragmentActivity {
    //flag 用于判断是否是第一次点击返回键
    private boolean flag = true;
    private static final int WHAT_RESET_BACK = 1;
    //主页面布局
    @Bind(R.id.fl_main_content)
    FrameLayout MainContent;
    //首页模块相关
    @Bind(R.id.iv_main_home)
    ImageView ivMainHome;
    @Bind(R.id.tv_main_home)
    TextView tvMainHome;
    @Bind(R.id.ll_main_home)
    LinearLayout llMainHome;
    //简历模块相关
    @Bind(R.id.iv_main_resume)
    ImageView ivMainJob;
    @Bind(R.id.tv_main_resume)
    TextView tvMainJob;
    @Bind(R.id.ll_main_resume)
    LinearLayout llMainJob;
    //消息模块相关
    @Bind(R.id.iv_main_chat)
    ImageView ivMainChat;
    @Bind(R.id.tv_main_chat)
    TextView tvMainChat;
    @Bind(R.id.ll_main_chat)
    LinearLayout llMainChat;
    //个人设置相关
    @Bind(R.id.iv_main_me)
    ImageView ivMainMe;
    @Bind(R.id.tv_main_me)
    TextView tvMainMe;
    @Bind(R.id.ll_main_me)
    LinearLayout llMainMe;

    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private ResumeFragment resumeFragment;
    private ChatFragment chatFragment;
    private MeFragment meFragment;
    private android.app.FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //初始化home页面,默认进入系统的首页面
        fragmentManager = getFragmentManager();
        transaction = new HomeFragment();
        transaction.add(R.id.fl_main_content, homeFragment);
        transaction.commit();
        
    }
    @OnClick({R.id.ll_main_home, R.id.ll_main_resume,R.id.ll_main_chat, R.id.ll_main_me})
    public void changeTab(View view) {
        transaction = fragmentManager.beginTransaction();
        //设置隐藏fragmen/图片/颜色
        hideFragment();
        switch (view.getId()) {
            case R.id.ll_main_home:
                if (homeFragment == null) {
                    //当homeFragment不存在时,进行初始化
                    homeFragment = new HomeFragment();
                    //添加homeFragment
                    transaction.add(R.id.fl_main_content, homeFragment);
                }
                transaction.show(homeFragment);
                ivMainHome.setImageResource(R.drawable.bottom02);
                tvMainHome.setTextColor(getResources().getColor(R.color.home_back_selected));
                break;
            case R.id.ll_main_resume:
                if (resumeFragment == null) {  
                    resumeFragment = new resumeFragment();
                    transaction.add(R.id.fl_main_content, resumeFragment);    
                }
                transaction.show(resumeFragment); 
                ivMainJob.setImageResource(R.drawable.bottom08);
                tvMainJob.setTextColor(getResources().getColor(R.color.home_back_selected));
                break;
            case R.id.ll_main_chat:
                if (chatFragment == null) {
                    chatFragment = new ChatFragment();
                    transaction.add(R.id.fl_main_content, chatFragment);
                }
                transaction.show(chatFragment);
                ivMainChat.setImageResource(R.drawable.bottom04);
                tvMainChat.setTextColor(getResources().getColor(R.color.home_back_selected));
                break;
            case R.id.ll_main_me:
                if (meFragment == null) {
                    meFragment = new MeFragment();
                    transaction.add(R.id.fl_main_content, meFragment);
                }
                transaction.show(meFragment);
                ivMainMe.setImageResource(R.drawable.bottom06);
                tvMainMe.setTextColor(getResources().getColor(R.color.home_back_selected));
                break;

        }
        transaction.commit();
    }
    private void hideFragment() {
        if (homeFragment != null) {
            ivMainHome.setImageResource(R.drawable.bottom01);
            tvMainHome.setTextColor(getResources().getColor(R.color.home_back_unselected));
            transaction.hide(homeFragment);
        }
        if (resumeFragment != null) {
            ivMainJob.setImageResource(R.drawable.bottom07);
            tvMainJob.setTextColor(getResources().getColor(R.color.home_back_unselected));
            transaction.hide(resumeFragment);
        }
        if (chatFragment != null) {
            ivMainChat.setImageResource(R.drawable.bottom03);
            tvMainChat.setTextColor(getResources().getColor(R.color.home_back_unselected));
            transaction.hide(chatFragment);
        }
        if (meFragment != null) {
            ivMainMe.setImageResource(R.drawable.bottom05);
            tvMainMe.setTextColor(getResources().getColor(R.color.home_back_unselected));
            transaction.hide(meFragment);
        }
    }
    
    /**
     * handler处理器,判断是否第一次点击返回键
     * */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_RESET_BACK:
                    flag = true;
                    break;
            }
        }
    };
    /**
     * 重写onKeyDown方法,当点击两次返回键退出程序
     * 
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && flag) {
            Toast.makeText(this, "再点击一次，退出当前应用", Toast.LENGTH_SHORT).show();
            flag = false;
            //设置2s的延迟，用于处理flag的值
            handler.sendEmptyMessageDelayed(WHAT_RESET_BACK, 2000);                    return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 为了避免内存泄漏，移除未被执行的消息
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
    
}
