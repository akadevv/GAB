package com.example.lcd.gab.ACCESS_TO_DB;

import android.util.Log;

import com.example.lcd.gab.PayRoom.DRoomItemInfo;
import com.example.lcd.gab.PayRoom.DRoom_FullInfo;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Administrator on 2016-01-15.
 */
public class Insert_DRoom_FullInfo_DB {
    // String DRoomName;
    // int DRoomDate;
    // List<DRoomItemInfo> DRoomItemList = new ArrayList<DRoomItemInfo>();
    // int totalPrice;
    // List<DRoomPartyInfo> DRoomPartyList = new ArrayList<DRoomPartyInfo>();
    String log = "jjunest";
    private final String USER_AGENT = "Mozilla/5.0";

    //DBRoom_FullInfo 객체와 phpURL을 넘겨주면, DB의 history table 에 넘겨준다.
    public void insertIntoDB_DroomFullInfo(DRoom_FullInfo newDroominfo, String phpurl) {

        //making newDroominfo OBJECT TO JSON TYPE;
        Log.d(log, "newDroominfo is : " + newDroominfo);
        Gson gson = new Gson();
        String jsonTransfered = gson.toJson(newDroominfo);
        System.out.println("this is transerfed String :  " + jsonTransfered);

        //url 을 통해 send해준다
        try {
            Log.d(log, "testing3 started-----");
            String data = URLEncoder.encode("newRoomInfo", "UTF-8") + "=" + URLEncoder.encode(jsonTransfered, "UTF-8");
            URL urlc = new URL(phpurl);
            URLConnection conn = urlc.openConnection();
            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;

            }
            System.out.println("this is response : " + sb);
            Log.d(log, "testing3 ended-----");
        } catch (Exception e) {
            e.printStackTrace();
        }

//--------------------------------------------------------------------


    }

}
