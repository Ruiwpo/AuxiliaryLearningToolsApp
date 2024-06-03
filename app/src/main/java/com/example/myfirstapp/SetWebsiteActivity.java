package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class SetWebsiteActivity extends AppCompatActivity {
    private static final String TAG = "SetWebsiteActivity";
    EditText newWeb, newName;
    String webString, nameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_website);

        webString = getIntent().getStringExtra("website_key3");
        newWeb = findViewById(R.id.new_website);
        newWeb.setText(webString);

        nameString = getIntent().getStringExtra("name_key3");
        newName = findViewById(R.id.new_name_text);
        newName.setText(nameString);
    }

    public void finish(View btn) {
        //保存新的备忘录数据
        webString = newWeb.getText().toString();
        Log.i(TAG, "save new website: " + webString);
        nameString = newName.getText().toString();
        Log.i(TAG, "save new website name: " + nameString);

        //带回备忘录数据到主页面
        Intent newWebIntent = getIntent();
        Bundle bdl = new Bundle();
        bdl.putString("website_key2", webString);
        bdl.putString("name_key2", nameString);
        newWebIntent.putExtras(bdl);

        Log.i(TAG, "back to quick browsing with: " + webString);
        Log.i(TAG, "back to quick browsing with: " + nameString);

        setResult(13, newWebIntent);

        finish();
    }
}