package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, Chronometer.OnChronometerTickListener{

    private Chronometer chronometer;  //计时器
    private Button start;  //开始计时按钮
    private Button pause;  //暂停计时按钮
    private Button reset;  //重置计时器按钮
    private long record_time = 0;  //当暂停时，记录已经经过的的时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        chronometer = findViewById(R.id.chronometer);
        start = findViewById(R.id.start);
        pause = findViewById(R.id.pause);
        reset = findViewById(R.id.reset);

        chronometer.setOnChronometerTickListener(this);
        start.setOnClickListener(this);
        pause.setOnClickListener(this);
        reset.setOnClickListener(this);

    }

    public void onClick(View v) {
        //设置计时器格式为 hh:mm:ss
        int hour = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000 / 60 / 60);
        if(hour<10){
            chronometer.setFormat("0"+hour+":%s");
        }
        else{
            chronometer.setFormat(hour+":%s");
        }

        //设置计时功能
        if(v.getId()==R.id.start) {
            //设置计时基准时间，防止暂停计时期间后台仍继续计时，导致继续计时后计时器时间出现跳跃
            chronometer.setBase(SystemClock.elapsedRealtime() - record_time);

            chronometer.start();  //开始计时
        } else if (v.getId() == R.id.pause) {
            record_time = SystemClock.elapsedRealtime() - chronometer.getBase();
            chronometer.stop();  //暂停计时
        } else if (v.getId() == R.id.reset) {
            chronometer.setBase(SystemClock.elapsedRealtime());  //重置计时器为00:00:00
            record_time = 0;  //重置已经经过的时间为0
        }
    }

    public void onChronometerTick(Chronometer chronometer) {

    }
}