package com.innovest.lcd.gab.ACCESS_TO_DB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016-02-14.
 */
public class Insert_UserInfo_DB {

    String log = "jjunest";
    private final String USER_AGENT = "Mozilla/5.0";

    //DBRoom_FullInfo 객체와 phpURL을 넘겨주면, DB의 history table 에 넘겨준다.
    public void insert_userInfo_To_DB (String newUserInfo, String phpurl) {

        //url 을 통해 send해준다
        try {
            System.out.println("this is sendUserInfo : " + newUserInfo);

            URL urlc = new URL(phpurl);
            URLConnection conn = urlc.openConnection();
            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(newUserInfo);
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    //DBRoom_FullInfo 객체와 phpURL을 넘겨주면, DB의 history table 에 넘겨준다.
    public void insert_userid_To_DB (String newUserId, String phpurl) {

        //url 을 통해 send해준다
        try {
            String data = URLEncoder.encode("newUserId", "UTF-8") + "=" + URLEncoder.encode(newUserId, "UTF-8");
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
