package com.example.thinkpad.icompetition.view.activity.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.thinkpad.icompetition.IcompetitionApplication;
import com.example.thinkpad.icompetition.R;
import com.example.thinkpad.icompetition.presenter.impl.BasePresenter;

import java.util.Timer;
import java.util.TimerTask;

/**
 * created by a’su's
 */
public class SplashActivity extends BaseActivity{
    private Timer timer;
    private TimerTask timerTask;
    private boolean isStartMainActivity=false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        timerTask=new TimerTask() {
            @Override
            public void run() {
                if(!isStartMainActivity)
                {
                    isStartMainActivity=true;
                    if(judgeToken()){
                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    }
                    else {
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    }
                    finish();
                }
            }
        };
        timer=new Timer();
        timer.schedule(timerTask,2500);
    }

    private boolean judgeToken() {
        String nowToken = ((IcompetitionApplication)getApplication()).getToken();
        return !TextUtils.isEmpty(nowToken);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

}
