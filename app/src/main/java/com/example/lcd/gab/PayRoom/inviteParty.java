package com.example.lcd.gab.PayRoom;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.lcd.gab.R;

/**
 * Created by Administrator on 2016-01-14.
 */
public class inviteParty extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_partylist);
        Button selected_completeBT = (Button)findViewById(R.id.selectPartyBT);
        finish();

    }
}
