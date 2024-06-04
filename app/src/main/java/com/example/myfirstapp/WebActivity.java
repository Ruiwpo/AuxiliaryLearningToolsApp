package com.example.myfirstapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "WebActivity";
    Button btn_baidu, btn_bing, btn_csdn, btn_youdao, btn_new, btn_set, btn_back;
    Uri uri;
    String websiteString, nameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();

        SharedPreferences sharedPreferences = getSharedPreferences("website", Activity.MODE_PRIVATE);
        websiteString = sharedPreferences.getString("website_content", "");
        nameString = sharedPreferences.getString("name_content", "");

        websiteString = getIntent().getStringExtra("website_key");
        nameString = getIntent().getStringExtra("name_key");

        Log.i(TAG, "onCreate: get from Main: " + websiteString);
        Log.i(TAG, "onCreate: get from Main: " + nameString);

        btn_new.setText(nameString);
    }

    private void initView(){
        btn_baidu = findViewById(R.id.btn_baidu);
        btn_bing = findViewById(R.id.btn_bing);
        btn_csdn = findViewById(R.id.btn_csdn);
        btn_youdao = findViewById(R.id.btn_youdao);
        btn_new = findViewById(R.id.btn_new);
        btn_set = findViewById(R.id.btn_set_web);
        btn_back = findViewById(R.id.btn_web_back);

        btn_baidu.setOnClickListener(this);
        btn_bing.setOnClickListener(this);
        btn_csdn.setOnClickListener(this);
        btn_youdao.setOnClickListener(this);
        btn_new.setOnClickListener(this);
        btn_set.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_baidu) {
            uri = Uri.parse("https://www.baidu.com/");
            Intent baidu = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(baidu);
        } else if (v.getId()==R.id.btn_bing) {
            uri = Uri.parse("https://cn.bing.com/");
            Intent bing = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(bing);
        } else if (v.getId()==R.id.btn_csdn) {
            uri = Uri.parse("https://www.csdn.net/");
            Intent csdn = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(csdn);
        } else if (v.getId()==R.id.btn_youdao) {
            uri = Uri.parse("https://dict.youdao.com/");
            Intent youdao = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(youdao);
        } else if (v.getId()==R.id.btn_new) {
            uri = Uri.parse(websiteString);
            Intent newWeb = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(newWeb);
        } else if (v.getId()==R.id.btn_set_web) {
            Intent set = new Intent(this, SetWebsiteActivity.class);
            set.putExtra("website_key3", websiteString);
            set.putExtra("name_key3", nameString);
            startActivityForResult(set, 12);
        } else if (v.getId()==R.id.btn_web_back) {
            Intent back = getIntent();
            Bundle bdl = new Bundle();
            bdl.putString("website_content", websiteString);
            bdl.putString("name_content", nameString);
            back.putExtras(bdl);
            Log.i(TAG, "Web back: back to main with " + websiteString);
            Log.i(TAG, "Web back: back to main with " + nameString);
            setResult(11, back);
            finish();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if(requestCode==12 && resultCode==13) {
            Bundle bdl2 = data.getExtras();
            websiteString = bdl2.getString("website_key2", "");
            nameString = bdl2.getString("name_key2", "");

            Log.i(TAG, "onActivityResult: " + websiteString);
            Log.i(TAG, "onActivityResult: " + nameString);

            //保存新增网址数据到SharedPreferences
            SharedPreferences sp = getSharedPreferences("website", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("website_content", websiteString);
            editor.putString("name_content", nameString);
            editor.apply();
            Log.i(TAG, "Web save to sp:" + websiteString);
            Log.i(TAG, "Web save to sp:" + nameString);

            btn_new.setText(nameString);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}