package com.innovest.lcd.gab.ACCESS_TO_DB;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016-02-15.
 */
public class Check_FirstLogin {

    String log = "jjunest";
    private final String USER_AGENT = "Mozilla/5.0";

    //DBRoom_FullInfo 객체와 phpURL을 넘겨주면, DB의 history table 에 넘겨준다.
    public boolean check_FirstLogin(String userId, String phpurl) {
        boolean firstLogin;
        firstLogin = false;

        try {
            String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8");
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
            System.out.println("this is response in Check_FirstLogin.java: " + sb);

            if(sb.substring(0,1).equals("0")){ //처음 로그인일 경우 //registerDB에서 row값이 0으로 넘어옴
                firstLogin = true;
                Log.d(log,"firstLogIn is true");
            }else{
                firstLogin = false;
                Log.d(log,"firstLogIn is NOT true");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return firstLogin;
    }

}
