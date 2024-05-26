package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmClockActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "AlarmClockActivity";
    private TextView alarm_clock_show;
    private Button set_alarm_clock;
    private Button cancel_alarm_clock;
    private AlarmManager alarmManager;
    private PendingIntent pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clock);
        initView();
    }

    private void initView() {
        alarm_clock_show = findViewById(R.id.alarm_clock_show);
        set_alarm_clock = findViewById(R.id.setClock);
        cancel_alarm_clock = findViewById(R.id.cancelClock);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //未设置闹钟时，隐藏取消闹钟按钮
        cancel_alarm_clock.setVisibility(View.GONE);

        Intent intent = new Intent(AlarmClockActivity.this, TimeUpActivity.class);
        pi = PendingIntent.getActivity(AlarmClockActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        set_alarm_clock.setOnClickListener(this);
        cancel_alarm_clock.setOnClickListener(this);
    }

    //设置/取消闹钟
    @Override
    public void onClick(View v) {
        //设置闹钟
        if(v.getId()==R.id.setClock) {
            Calendar currentTime = Calendar.getInstance();
            new TimePickerDialog(AlarmClockActivity.this, 0, (view, hourOfDay, minute) -> {
                  //设置当前时间
                  Calendar c = Calendar.getInstance();
                  c.setTimeInMillis(System.currentTimeMillis());
                  //用户设置闹钟
                  c.set(Calendar.HOUR, hourOfDay);
                  c.set(Calendar.MINUTE, minute);
                  //AlarmManager在设置的时间启动闹钟
                  alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
                  //设好闹钟后在页面中显示设定的闹钟时间
                  alarm_clock_show.setText(String.valueOf(c.getTime()));

            }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), false).show();

            //闹钟设好后设置取消闹钟按钮为可见
            cancel_alarm_clock.setVisibility(View.VISIBLE);
        }
        //取消闹钟
        else if (v.getId() == R.id.cancelClock) {
            //取消AlarmManager在设定时间的响应
            alarmManager.cancel(pi);
            //隐藏取消闹钟按钮
            cancel_alarm_clock.setVisibility(View.GONE);
            //在界面显示没有闹钟
            alarm_clock_show.setText("no alarm clock");
        }
    }

    //返回主界面
    public void back1(View btn) {
        Intent retIntent = getIntent();
        Log.i(TAG, "back1: back to main");
        setResult(4, retIntent);
        finish();
    }
}