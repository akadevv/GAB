package com.example.lcd.gab.ACCESS_TO_DB;

import android.util.Log;

import com.example.lcd.gab.PayRoom.DRoom_FullInfo;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016-01-26.
 */
public class Revise_DRoom_FullInfo_DB {
    String log = "jjunest";
    private final String USER_AGENT = "Mozilla/5.0";

    public void revise_DRoom_FullInfo_DB(DRoom_FullInfo revisedDroominfo, String phpurl){

        //making newDroominfo OBJECT TO JSON TYPE;
        Log.d(log, "revisedRoomData is : " + revisedDroominfo);
        Gson gson = new Gson();
        String jsonTransfered = gson.toJson(revisedDroominfo);
        System.out.println("this is transerfed String :  " + jsonTransfered);

        //url 을 통해 send해준다
        try {
            Log.d(log, "testing3 started-----");
            String data = URLEncoder.encode("revisedRoomInfo", "UTF-8") + "=" + URLEncoder.encode(jsonTransfered, "UTF-8");
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
    }
}
