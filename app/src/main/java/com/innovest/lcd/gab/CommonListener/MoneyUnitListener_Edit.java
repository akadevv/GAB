package com.innovest.lcd.gab.CommonListener;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016-02-02.
 */
//item Money 의 가격을 3자리씩 끝어서 보여준다. (EditText일 시에)
public class MoneyUnitListener_Edit implements TextWatcher {
    private String log = "jjunest";
    private EditText moneyView;
    DecimalFormat df = new DecimalFormat("###,###.####");
    String result = "";

    public MoneyUnitListener_Edit(EditText moneyView) {
        this.moneyView = moneyView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals(result)) {     // StackOverflow를 막기위해,
            if (s.toString().equals("")) {
                result = df.format(Long.parseLong("0"));
            } else {
                result = df.format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
            }
            Log.d(log, "MoneyUnitListener : " + result);
            moneyView.setText(result.toString());
            moneyView.setSelection(result.length());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}