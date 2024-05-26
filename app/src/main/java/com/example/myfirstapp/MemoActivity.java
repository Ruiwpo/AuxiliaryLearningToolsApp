package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MemoActivity extends AppCompatActivity {

    private static final String TAG = "MemoActivity";
    EditText memo_content;

    private String newMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        newMemo = getIntent().getStringExtra("memo_key");
        memo_content = findViewById(R.id.memo_content);
        memo_content.setText(newMemo);
    }

    public void save(View btn) {
        //保存新的备忘录数据
        newMemo = memo_content.getText().toString();
        Log.i(TAG, "save: " + newMemo);
    }

    public void back(View btn) {
        //带回备忘录数据到主页面
        Intent memoIntent = getIntent();
        Bundle bdl = new Bundle();
        bdl.putString("memo_key2", newMemo);
        memoIntent.putExtras(bdl);

        Log.i(TAG, "back: " + newMemo);

        setResult(2, memoIntent);

        finish();
    }
}