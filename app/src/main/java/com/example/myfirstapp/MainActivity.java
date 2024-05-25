package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, Chronometer.OnChronometerTickListener{

    private static final String TAG = "MainActivity";
    private Chronometer chronometer;  //计时器
    private Button start;  //开始计时按钮
    private Button pause;  //暂停计时按钮
    private Button reset;  //重置计时器按钮
    private long record_time = 0;  //当暂停时，记录已经经过的的时间
    private String memoString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        SharedPreferences sharedPreferences = getSharedPreferences("memo", Activity.MODE_PRIVATE);
        memoString = sharedPreferences.getString("memo_context", "");

        Log.i(TAG, "onCreate: get from sp:" + memoString);
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

    public void open(View btn){
        Intent memo = new Intent(this, MemoActivity.class);
        memo.putExtra("memo_key", memoString);

        Log.i(TAG, "open: memoString="+memoString);

        //startActivity(config);
        startActivityForResult(memo,1);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if(requestCode==1 && resultCode==2) {
            Bundle bdl2 = data.getExtras();
            memoString = bdl2.getString("memo_key2", "");

            Log.i(TAG, "onActivityResult: " + memoString);

            //保存新的汇率数据到->SharedPreferences
            SharedPreferences sp = getSharedPreferences("memo", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("memo_context", memoString);
            editor.apply();
            Log.i(TAG, "save to sp:" + memoString);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}