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
    Button btn_baidu, btn_bing, btn_csdn, btn_cnki, btn_new, btn_set, btn_back;
    Uri uri;
    String websiteString, nameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();

        SharedPreferences sharedPreferences = getSharedPreferences("website", Activity.MODE_PRIVATE);
        websiteString = sharedPreferences.getString("website_key", "");
        nameString = sharedPreferences.getString("name_key", "");

        btn_new.setText(nameString);
    }

    private void initView(){
        btn_baidu = findViewById(R.id.btn_baidu);
        btn_bing = findViewById(R.id.btn_bing);
        btn_csdn = findViewById(R.id.btn_csdn);
        btn_cnki = findViewById(R.id.btn_cnki);
        btn_new = findViewById(R.id.btn_new);
        btn_set = findViewById(R.id.btn_set_web);
        btn_back = findViewById(R.id.btn_web_back);

        btn_baidu.setOnClickListener(this);
        btn_bing.setOnClickListener(this);
        btn_csdn.setOnClickListener(this);
        btn_cnki.setOnClickListener(this);
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
        } else if (v.getId()==R.id.btn_cnki) {
            uri = Uri.parse("https://www.cnki.net/");
            Intent cnki = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(cnki);
        } else if (v.getId()==R.id.btn_new) {
            uri = Uri.parse(nameString);
            Intent newWeb = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(newWeb);
        } else if (v.getId()==R.id.btn_set_web) {
            Intent set = new Intent(this, SetWebsiteActivity.class);
            set.putExtra("website_key3", websiteString);
            set.putExtra("name_key3", nameString);
            startActivityForResult(set, 12);
        } else if (v.getId()==R.id.btn_web_back) {
            Intent back = getIntent();
            Log.i(TAG, "Web back: back to main");
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

            //保存备忘录内容数据到SharedPreferences
            SharedPreferences sp = getSharedPreferences("website", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("website_content", websiteString);
            editor.putString("name_content", nameString);
            editor.apply();
            Log.i(TAG, "save to sp:" + websiteString);
            Log.i(TAG, "save to sp:" + nameString);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}