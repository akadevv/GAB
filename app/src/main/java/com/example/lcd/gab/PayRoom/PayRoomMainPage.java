package com.example.lcd.gab.PayRoom;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lcd.gab.R;

/**
 * Created by LCD on 2016-01-26.
 */
public class PayRoomMainPage extends Activity {



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_room_main_page);

        int totalCost = 0;

        TextView title = (TextView)findViewById(R.id.pay_room_title);
        TextView masterName = (TextView)findViewById(R.id.master_name);

        TextView totalCostText = (TextView)findViewById(R.id.pay_room_total_cost);
        ImageView finished = (ImageView)findViewById(R.id.pay_room_result_finished);
        ImageView notFinished = (ImageView)findViewById(R.id.pay_room_result_not_finished);

        DRoom_FullInfo roomListData = (DRoom_FullInfo)getIntent().getSerializableExtra("selectedDRoomInfo");

        RecyclerView itemRecyclerView;
        RecyclerView memberRecyclerView;

        LinearLayout itemLinearLayout = (LinearLayout) findViewById(R.id.item_linear_layout);
        LinearLayout costLinearLayout = (LinearLayout) findViewById(R.id.cost_linear_layout);

        LinearLayoutManager itemLayoutManager = new LinearLayoutManager(itemLinearLayout.getContext());
        itemLayoutManager.setOrientation(RecyclerView.VERTICAL);
        LinearLayoutManager costLayoutManager = new LinearLayoutManager(costLinearLayout.getContext());
        costLayoutManager.setOrientation(RecyclerView.VERTICAL);

        itemRecyclerView = (RecyclerView) findViewById(R.id.item_recycler_view);
        memberRecyclerView = (RecyclerView) findViewById(R.id.cost_recycler_view);

        itemRecyclerView.setHasFixedSize(true);
        memberRecyclerView.setHasFixedSize(true);

        itemRecyclerView.setLayoutManager(itemLayoutManager);
        memberRecyclerView.setLayoutManager(costLayoutManager);

        PayRoomMainAdapter_ForItem itemAdapter = new PayRoomMainAdapter_ForItem(roomListData.getDRoomItemList(), getApplicationContext());
        PayRoomMainAdapter_ForCost costAdapter = new PayRoomMainAdapter_ForCost(roomListData.getDRoomPartyList(), getApplicationContext());

        itemRecyclerView.setAdapter(itemAdapter);
        memberRecyclerView.setAdapter(costAdapter);

        title.setText(roomListData.getDRoomName());
        masterName.setText(roomListData.getMasterID());

        for(int i = 0; i < roomListData.getDRoomPartyList().size(); i++)
            totalCost += roomListData.getDRoomPartyList().get(i).getPartyMoney();

        totalCostText.setText(Integer.toString(totalCost));

        if(roomListData.getDRoomFinished()==0){
            finished.setVisibility(View.GONE);
            notFinished.setVisibility(View.VISIBLE);
        }else{
            finished.setVisibility(View.VISIBLE);
            notFinished.setVisibility(View.GONE);
        }

    }
}
