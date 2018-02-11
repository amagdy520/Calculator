package com.calculator;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class Calculator extends AppCompatActivity {
    private TextView _screen;
    private String display = "";
    private String currentOperator = "";
    private String result = "";
    private ViewPager mViewPager;
    private Adapter mAdapter;
    double val1 , val2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        _screen = (TextView)findViewById(R.id.textView);
        _screen.setText(display);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mAdapter = new Adapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
    }

    private void updateScreen(){
        _screen.setText(display);
    }

    public void onClickNumber(View v){
        if(result != ""){
            clear();
            updateScreen();
        }
        Button b = (Button) v;
        display += b.getText();
        updateScreen();
    }

    private boolean isOperator(char op){
        switch (op){
            case '+':
            case '-':
            case 'x':
            case '÷':return true;
            default: return false;
        }
    }

    public void onClickOperator(View v){
        if(display == "") return;

        Button b = (Button)v;

        if(result != ""){
            String _display = result;
            clear();
            display = _display;
        }

        if(currentOperator != ""){
            Log.d("CalcX", ""+display.charAt(display.length()-1));
            if(isOperator(display.charAt(display.length()-1))){
                display = display.replace(display.charAt(display.length()-1), b.getText().charAt(0));
                updateScreen();
                return;
            }else{
                getResult();
                display = result;
                result = "";
            }
            currentOperator = b.getText().toString();
        }
        display += b.getText();
        currentOperator = b.getText().toString();
        updateScreen();
    }

    private void clear(){
        display = "";
        currentOperator = "";
        result = "";
    }

    public void onClickClear(View v){
        clear();
        updateScreen();
    }

    private double operate(String a, String b, String op){
        switch (op){
            case "+": return Double.valueOf(a) + Double.valueOf(b);
            case "-": return Double.valueOf(a) - Double.valueOf(b);
            case "x": return Double.valueOf(a) * Double.valueOf(b);
            case "÷": try{
                return Double.valueOf(a) / Double.valueOf(b);
            }catch (Exception e){
                Log.d("Calc", e.getMessage());
            }
            default: return -1;
        }
    }

    private boolean getResult(){
        if(currentOperator == "") return false;
        String[] operation = display.split(Pattern.quote(currentOperator));
        if(operation.length < 2) return false;
        result = String.valueOf(operate(operation[0], operation[1], currentOperator));
        return true;
    }

    public void onClickEqual(View v){
        if(display == "") return;
        if(display.contains("sin")){
            int i = display.length();
            String s = display.substring(3,i);
            DecimalFormat twoDForm = new DecimalFormat("#.#");
            double d = Double.valueOf(twoDForm.format(Math.sin(Math.toRadians(Double.valueOf(s)))));
            _screen.setText(display + "\n" + String.valueOf(d));
        }else if(display.contains("cos")){
            int i = display.length();
            String s = display.substring(3,i);
            DecimalFormat twoDForm = new DecimalFormat("#.#");
            double d = Double.valueOf(twoDForm.format(Math.cos(Math.toRadians(Double.valueOf(s)))));
            _screen.setText(display + "\n" + String.valueOf(d));
        }else if(display.contains("tan")){
            int i = display.length();
            String s = display.substring(3,i);
            DecimalFormat twoDForm = new DecimalFormat("#.#");
            double d = Double.valueOf(twoDForm.format(Math.tan(Math.toRadians(Double.valueOf(s)))));
            _screen.setText(display + "\n" + String.valueOf(d));
        }else if(display.contains("\u221a")){
            int i = display.length();
            String s = display.substring(1,i);
            DecimalFormat twoDForm = new DecimalFormat("#.#");
            double d = Double.valueOf(twoDForm.format(Math.sqrt(Double.valueOf(s))));
            _screen.setText(display + "\n" + String.valueOf(d));
        }else if(display.contains("^")){
            int i = display.indexOf("^");
            int len = display.length();
            String s2 = display.substring(i+1,len);
            val2 = Double.valueOf(s2);
            DecimalFormat twoDForm = new DecimalFormat("#.#");
            double d = Double.valueOf(twoDForm.format(Math.pow(val1,val2)));
            _screen.setText(display + "\n" + String.valueOf(d));
        }else if(!getResult()){
             return;
        }else{
            _screen.setText(display + "\n" + String.valueOf(result));
        }
    }


    public void onTrigonometricFunctions(View v){
        if(display != "") return;
        Button mButton = (Button) v;
        String mCheck = mButton.getText().toString();
        switch(mCheck){
            case "sin":
                display = "sin";
                updateScreen();
                break;
            case "cos":
                display = "cos";
                updateScreen();
                break;
            case "tan":
                display = "tan";
                updateScreen();
                break;
        }
    }

    public void onSquare(View v){
        if(display != "") return;
        Button mButton = (Button) v;
        display = mButton.getText().toString();
        updateScreen();
    }

    public void onMultiplicand(View v){
        int i = Integer.parseInt(display);
        int value = 1;
        for (int j = i ; j >= 1 ; j-- ){
            value *= j;
        }
        _screen.setText(display+"!"+"\n"+ String.valueOf(value));
    }

    public void onPower(View v){
        if(display == "") return;
        Button mButton = (Button) v;
        val1 = Double.valueOf(display);
        display += mButton.getText().toString();
        updateScreen();
    }

}
