package com.example.myfirstapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TimeUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_up);

        //到时间后弹出“Time Up!”的提示对话框，点击“Close"即可关闭
        new AlertDialog.Builder(TimeUpActivity.this).setTitle("Alarm Clock").setMessage("Time Up!").setPositiveButton("Close", (dialog, which) -> TimeUpActivity.this.finish()).show();
    }
}