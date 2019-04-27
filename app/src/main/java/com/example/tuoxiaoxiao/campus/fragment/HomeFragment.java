package com.example.tuoxiaoxiao.campus.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.tuoxiaoxiao.campus.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页面主要内容
 * @author ywm
 */

public class HomeFragment extends Fragment {
    @Bind(R.id.vp_home)
    ViewPager vpHome;
    @Bind(R.id.group)
    RadioGroup group;
    @Bind(R.id.iv_home_icon1)
    ImageView ivHomeIcon1;
    @Bind(R.id.tv_home_icon1)
    TextView tvHomeIcon1;
    @Bind(R.id.iv_home_icon2)
    ImageView ivHomeIcon2;
    @Bind(R.id.tv_home_icon2)
    TextView tvHomeIcon2;
    @Bind(R.id.iv_home_icon3)
    ImageView ivHomeIcon3;
    @Bind(R.id.tv_home_icon3)
    TextView tvHomeIcon3;
    @Bind(R.id.iv_home_icon4)
    ImageView ivHomeIcon4;
    @Bind(R.id.tv_home_icon4)
    TextView tvHomeIcon4;
    @Bind(R.id.iv_home_bigshow)
    ImageView ivHomeBigshow;
    @Bind(R.id.tv_home_hot)
    TextView tvHomeHot;
    @Bind(R.id.iv_home_hot1)
    ImageView ivHomeHot1;
    @Bind(R.id.iv_home_hot2)
    ImageView ivHomeHot2;
    @Bind(R.id.iv_home_hot3)
    ImageView ivHomeHot3;

    //图片资源集合
    private int[] imageIds = {R.drawable.pic_home_longterm, R.drawable.pic_home_quickly, R.drawable.pic_home_shortterm};
    //存放图片的数组
    private List<ImageView> mList;
    //当前索引位置以及上一个索引位置
    private int index = 0, preIndex = 0;
    //是否需要轮播标志
    private boolean isContinue = true;
    //定时器，用于实现轮播
    private Timer timer;

    @SuppressLint("HandlerLeak")
    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (getActivity() != null) {
                switch (msg.what) {
                    case 1:
                        index++;
                        System.out.println("==========index: " + index);
                        vpHome.setCurrentItem(index);
                }
            }
        }
    };

    /**
     * 为了避免内存泄漏，移除未被执行的消息
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        mList = new ArrayList<>();
        vpHome.setAdapter(pagerAdapter);
        vpHome.addOnPageChangeListener(onPageChangeListener);
        vpHome.setOnTouchListener(onTouchListener);
        initRadioButton(imageIds.length);
        intiTimer();
        return view;
    }

    /**
     *  初始化timer 定时器
     * */
    private void intiTimer() {
        //创建Timer对象
        timer = new Timer();
        if (getActivity() == null) {
            timer.cancel();
        } else {
            //执行定时任务
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //首先判断是否需要轮播，是的话才发消息
                    if (isContinue) {
                        mHandler.sendEmptyMessage(1);
                    }
                }
            }, 3000, 3000);//延迟2秒，每隔2秒发一次消息
        }
    }

    /**
     *   销毁视图
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 根据图片个数初始化按钮
     *
     * @param length
     */
    private void initRadioButton(int length) {
        for (int i = 0; i < length; i++) {
            ImageView imageview = new ImageView(getActivity());
            imageview.setImageResource(R.drawable.rg_selector);//设置背景选择器
            imageview.setPadding(20, 0, 0, 0);//设置每个按钮之间的间距
            //将按钮依次添加到RadioGroup中
            group.addView(imageview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //默认选中第一个按钮，因为默认显示第一张图片
            group.getChildAt(0).setEnabled(false);
        }
    }


    /**
     * 根据当前触摸事件判断是否要轮播
     */
    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                //手指按下和划动的时候停止图片的轮播
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    isContinue = false;
                    break;
                default:
                    isContinue = true;
            }
            return false;
        }
    };

    /**
     * 根据当前选中的页面设置按钮的选中
     */
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {
            index = position;//当前位置赋值给索引
            setCurrentDot(index % imageIds.length);
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * 设置对应位置按钮的状态
     * @param i 当前位置
     */
    private void setCurrentDot(int i) {
        if (group.getChildAt(i) != null) {
            group.getChildAt(i).setEnabled(false);//当前按钮选中
        }
        if (group.getChildAt(preIndex) != null) {
            group.getChildAt(preIndex).setEnabled(true);//上一个取消选中
            preIndex = i;//当前位置变为上一个，继续下次轮播
        }
    }

   /**
    *  实现图片轮播的adapter
    */
    PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            //返回一个比较大的值，目的是为了实现无限轮播
            return Integer.MAX_VALUE;
        }
        //判断显示的是否为同一张图片
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        //显示图片的初始化
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % imageIds.length;
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(imageIds[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            mList.add(imageView);
            return imageView;
        }
        //PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }
    };
}
