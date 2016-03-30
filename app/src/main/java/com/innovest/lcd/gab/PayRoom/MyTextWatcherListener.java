package com.innovest.lcd.gab.PayRoom;

import android.content.Context;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;

/**
 * Created by Administrator on 2016-01-14.
 */
import android.app.Activity;

public class MyTextWatcherListener implements TextWatcher{
    Context myContext;
    Activity payRoomActivity;
    Layout mylayout;
    private String log = "jjunest";

    public MyTextWatcherListener(Context myContext, Activity payRoomActivity) {
        this.myContext = myContext;
        this.payRoomActivity = payRoomActivity;
    }



    @Override

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        Log.d(log, "this is after onTextChanged");
//        Log.d(log, "this is after payRommActivity :"+payRoomActivity);
//        Log.d(log, "this is after context :"+myContext);
//        LinearLayout ItemLayout = (LinearLayout)payRoomActivity.findViewById(R.id.dutchPayItemLayout);
//        Log.d(log, "this is after ItemLayout : "+ItemLayout);
//        int coun=ItemLayout.getChildCount();
//        Log.d(log, "this is after ItemLayout : "+coun);
//        for(int i =0; i<coun; i++){
//                Log.d(log, "this is loop i  :"+i +ItemLayout.getChildAt(i));
//               String getTag = (String)ItemLayout.getChildAt(i).getTag();
//              Log.d(log, "this is loop TAG  :"+i +getTag);
//
//                if(getTag==null){
//                    Log.d(log, "this is getTag null"+i +getTag);
//                }else{
//                    Log.d(log, "this is not getTag null"+i +getTag);
//                    if(getTag.equals("itemTag")){
//                        Log.d(log, "this is getTag ==ItemTag"+i +getTag);
//                    }
//                }
//        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
