package com.example.lcd.gab.CommonListener;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016-02-02.
 */
//item Money 의 가격을 3자리씩 끝어서 보여준다. (EditText일 시에)
public class MoneyUnitListener_Text implements TextWatcher {
    private String log= "jjunest";
    private TextView moneyView;
    DecimalFormat df = new DecimalFormat("###,###.####");
    String result = "";

    public MoneyUnitListener_Text(TextView moneyView) {
        this.moneyView = moneyView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals(result)) {     // StackOverflow를 막기위해,
            result = df.format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
            Log.d(log, "MoneyUnitListener : " + result);
            moneyView.setText(result.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}