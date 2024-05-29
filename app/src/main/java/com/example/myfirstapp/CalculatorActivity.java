package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CalculatorActivity";
    TextView result_show;  //显示算式和结果
    Button btn_ac, btn_c, btn_left, btn_right, btn_plus, btn_minus, btn_mul, btn_div, btn_point, btn_result,
            btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_0;
    Stack<Character> st1 = new Stack<Character>();
    Stack<Double> st2 = new Stack<Double>();
    String s = "";  //输入的表达式（中缀表达式）
    String trans_s = "";  //后缀表达式
    int flag = 0;  //flag=1表示发生了除0错误

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        initView();
    }

    private void initView() {
        result_show = findViewById(R.id.result_show);
        btn_ac = findViewById(R.id.btn_ac);
        btn_c = findViewById(R.id.btn_c);
        btn_left = findViewById(R.id.btn_left);
        btn_right = findViewById(R.id.btn_right);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        btn_mul = findViewById(R.id.btn_multiply);
        btn_div = findViewById(R.id.btn_divide);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        btn_0 = findViewById(R.id.btn_0);
        btn_point = findViewById(R.id.btn_point);
        btn_result = findViewById(R.id.btn_get_result);

        btn_ac.setOnClickListener(this);
        btn_c.setOnClickListener(this);
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        btn_plus.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_mul.setOnClickListener(this);
        btn_div.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_0.setOnClickListener(this);
        btn_point.setOnClickListener(this);
        btn_result.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //点击计算器相应按钮，得到输入的中缀表达式
        if (v.getId()!=R.id.btn_get_result) {
            if (v.getId()==R.id.btn_ac){
                s = "";
            } else if (v.getId()==R.id.btn_c){
                if(s.length()!=0){
                    s = s.substring(0, s.length()-1);
                }
            } else if (v.getId()==R.id.btn_left){
                s += '(';
            } else if (v.getId()==R.id.btn_right){
                s += ')';
            } else if (v.getId()==R.id.btn_1){
                s += '1';
            } else if (v.getId()==R.id.btn_2){
                s += '2';
            } else if (v.getId()==R.id.btn_3){
                s += '3';
            } else if (v.getId()==R.id.btn_4){
                s += '4';
            } else if (v.getId()==R.id.btn_5){
                s += '5';
            } else if (v.getId()==R.id.btn_6){
                s += '6';
            } else if (v.getId()==R.id.btn_7){
                s += '7';
            } else if (v.getId()==R.id.btn_8){
                s += '8';
            } else if (v.getId()==R.id.btn_9){
                s += '9';
            } else if (v.getId()==R.id.btn_0){
                s += '0';
            } else if (v.getId()==R.id.btn_plus){
                s += '+';
            } else if (v.getId()==R.id.btn_minus){
                s += '-';
            } else if (v.getId()==R.id.btn_multiply){
                s += '*';
            } else if (v.getId()==R.id.btn_divide){
                s += '/';
            } else if (v.getId()==R.id.btn_point){
                s += '.';
            }
            result_show.setText(s);
        }
        //当点击“=”按钮时，得到计算结果
        else {
            s += '=';
            transString(s);
            getResult(trans_s);
            s = "";
            trans_s = "";
            flag = 0;
        }
    }

    //将输入的中缀表达式转为后缀表达式
    private void transString(String s) {
        char[] sArr = s.toCharArray();
        int i = 0;
        while (sArr[i]!='=') {
            if(sArr[i]=='('){
                st1.push('(');
                i++;
            } else if (sArr[i]==')') {
                Character e = st1.pop();
                while(e!='('){
                    trans_s += e;
                    e = st1.pop();
                }
                i++;
            } else if (sArr[i]=='+'){
                while(!st1.isEmpty()){
                    Character e = st1.peek();
                    if (e!='('){
                        trans_s += e;
                        st1.pop();
                    } else break;
                }
                st1.push('+');
                i++;
            } else if (sArr[i]=='-') {
                while (!st1.isEmpty()) {
                    Character e = st1.peek();
                    if (e != '(') {
                        trans_s += e;
                        st1.pop();
                    } else break;
                }
                st1.push('-');
                i++;
            } else if (sArr[i]=='*') {
                while (!st1.isEmpty()) {
                    Character e = st1.peek();
                    if (e=='*' || e=='/') {
                        trans_s += 'e';
                        st1.pop();
                    } else break;
                }
                st1.push('*');
                i++;
            } else if (sArr[i]=='/') {
                while (!st1.isEmpty()) {
                    Character e = st1.peek();
                    if (e=='*' || e=='/') {
                        trans_s += 'e';
                        st1.pop();
                    } else break;
                }
                st1.push('/');
                i++;
            } else {
                while (sArr[i]>='0' && sArr[i]<='9' || sArr[i]=='.'){
                    trans_s += sArr[i];
                    i++;
                }
                trans_s += '#';
            }
        }
        while (!st1.isEmpty()){
            Character e = st1.pop();
            trans_s += e;
        }
        trans_s += '=';
    }

    //通过后缀表达式获得计算结果
    private void getResult(String trans_s) {
        double a, b, c, result = 0;
        int i=0;
        char[] sArr = trans_s.toCharArray();
        while (sArr[i]!='=') {
            if (sArr[i]=='+') {
                a = st2.pop();
                b = st2.pop();
                c = b + a;
                st2.push(c);
            } else if (sArr[i]=='-') {
                a = st2.pop();
                b = st2.pop();
                c = b - a;
                st2.push(c);
            } else if (sArr[i]=='*') {
                a = st2.pop();
                b = st2.pop();
                c = b * a;
                st2.push(c);
            } else if (sArr[i]=='/') {
                a = st2.pop();
                b = st2.pop();
                if (a!=0) {
                    c = b / a;
                    st2.push(c);
                }
                //当除数为0时，发生除0错误，计算器不显示结果，退出本次计算
                else {
                    Log.i(TAG, "getResult: error_0");
                    result_show.setText("--");
                    flag = 1;
                    break;
                }
            } else if (sArr[i]>='0' && sArr[i]<='9' || sArr[i]=='.') {
                String num = "";
                while (sArr[i]>='0' && sArr[i]<='9' || sArr[i]=='.') {
                    num += sArr[i];
                    i++;
                }
                i--;
                st2.push(Double.parseDouble(num));
            }
            i++;
        }

        //未发生除0错误时，显示计算结果
        if(flag==0) {
            result = st2.peek();
            if (result == (int) result){
                result_show.setText('=' + String.valueOf((int)result));  //结果为整数
            } else if (((result - (int) result)*10)%2==0 || ((result - (int) result)*10)%5==0) {
                result_show.setText('=' + String.format("%.1f", result));  //结果是小数，并且为0.2或0.5的倍数
            } else if (((result - (int) result)*100)%25==0) {
                result_show.setText('=' + String.format("%.2f", result));  //结果为小数，并且为0.25的倍数
            } else if (((result - (int) result)*1000)%125==0) {
                result_show.setText('=' + String.format("%.3f", result));  //结果为小数，并且为0.125的倍数
            } else if (((result - (int) result)*10000)%625==0) {
                result_show.setText('=' + String.format("%.4f", result));  //结果为小数，并且为0.0625的倍数
            } else if (((result - (int) result)*100000)%3125==0) {
                result_show.setText('=' + String.format("%.5f", result));  //结果为小数，并且为0.03125的倍数
            } else {
                result_show.setText('=' + String.format("%.6f", result));  //结果为小数，且小数部分大于等于6位
            }
        }
    }

    //返回主界面
    public void back2 (View v) {
        Intent calculatorIntent = getIntent();
        Log.i(TAG, "back2: back to main");
        setResult(6, calculatorIntent);
        finish();
    }
}