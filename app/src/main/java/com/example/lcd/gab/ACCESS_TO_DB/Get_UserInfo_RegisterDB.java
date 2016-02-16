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
 * Created by Administrator on 2016-02-16.
 */
public class Get_UserInfo_RegisterDB {

    String log = "jjunest";
    private final String USER_AGENT = "Mozilla/5.0";

    public String getUserInfoFromRegisterDB(String UserId, String phpurl) {
            String response = null;
        //url 을 통해 send해준다
        try {
            Log.d(log, "testing3 started-----");
            String data = URLEncoder.encode("UserId", "UTF-8") + "=" + URLEncoder.encode(UserId, "UTF-8");
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
            response =  sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //정보 받아오기 실패시에는 null 을 return 한다
            return response;
    }
}
