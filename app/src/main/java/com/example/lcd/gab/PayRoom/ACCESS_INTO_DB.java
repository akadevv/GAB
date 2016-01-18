package com.example.lcd.gab.PayRoom;

import android.util.Log;

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
public class ACCESS_INTO_DB {
    // String DRoomName;
    // int DRoomDate;
    // List<DRoomItemInfo> DRoomItemList = new ArrayList<DRoomItemInfo>();
    // int totalPrice;
    // List<DRoomPartyInfo> DRoomPartyList = new ArrayList<DRoomPartyInfo>();
    String log = "jjunest";
    private final String USER_AGENT = "Mozilla/5.0";

    public void insertIntoDB_DroomFullInfo(DRoom_FullInfo newDroominfo, String phpurl) {
        URLConnection urlCon = null;
        Object reply = null;
        ObjectOutputStream out;
        try {
            Log.d(log, "this is in post insertDB() 1");
            URL url = new URL(phpurl);

            urlCon = (HttpURLConnection) url.openConnection();

            urlCon.setDoOutput(true); // to be able to write.
            urlCon.setDoInput(true); // to be able to read.

            out = new ObjectOutputStream(urlCon.getOutputStream());
            out.writeObject(newDroominfo);
            out.close();
            Log.d(log, "this is in post insertDB() 1.1 reply");
            ObjectInputStream ois = new ObjectInputStream(urlCon.getInputStream());
            reply = ois.readObject();
            ois.close();
            Log.d(log, "this is in post insertDB() 2 reply + :" + reply);
        } catch (Exception e) {

        }

        // recieve reply
        try {
            Log.d(log, "this is in receive reply DB() start ");
            ObjectInputStream objIn = new ObjectInputStream(urlCon.getInputStream());
            Log.d(log, "this is in receive reply DB() start1 ");
            reply = objIn.readObject();
            Log.d(log, "this is in receive reply DB() start2 ");
            objIn.close();
            Log.d(log, "this is in receive reply DB() after =" + reply);
        } catch (Exception ex) {
            // it is ok if we get an exception here
            // that means that there is no object being returned
            System.out.println("No Object Returned");
            if (!(ex instanceof EOFException))
                ex.printStackTrace();
            System.err.println("*");
        }

        Log.d(log, "InsertDRoomFullToDB() IN ACCESS INTO DB Class / DRoomname =" + newDroominfo.getDRoomName());
        Log.d(log, "InsertDRoomFullToDB() IN ACCESS INTO DB Class / DATE =" + newDroominfo.getDRoomDate());
        List<DRoomItemInfo> newList = newDroominfo.getDRoomItemList();
        for (int i = 0; i < newList.size(); i++) {
            Log.d(log, "this is item 1 's name " + newList.get(i).getDRoomitem_name());
            Log.d(log, "this is item 1 's name " + newList.get(i).getDRoomitem_price());
        }

        Log.d(log, "test=========================================================");

        try {
            URL obj = new URL(phpurl);
            Log.d(log, "testing1-----");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");
            Log.d(log, "testing1.1-----");
            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);
            Log.d(log, "testing1.2-----");
            int responseCode = con.getResponseCode();
            Log.d(log, "testing1.3-----responseCode :" + responseCode);

            Log.d(log, "testing1.4-----");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            Log.d(log, "testing1.5-----");
            StringBuffer response = new StringBuffer();
            Log.d(log, "testing1.6-----");
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            Log.d(log, "testing1.7-----response : " + response);
            //print result
            System.out.println(response.toString());

        } catch (Exception e) {

        }
        Log.d(log, "testing1 end-----");
        Log.d(log, "testing2 started-----");

        //making newDroominfo OBJECT TO JSON TYPE;
        Log.d(log, "newDroominfo is : " + newDroominfo);
        Gson gson = new Gson();
        String jsonTransfered = gson.toJson(newDroominfo);
        System.out.println("this is transerfed String :  " + jsonTransfered);

        //url 을 통해 send해준다
        try {
            Log.d(log, "testing3 started-----");
            String data = URLEncoder.encode("newRoomInfo", "UTF-8") + "=" + URLEncoder.encode(jsonTransfered, "UTF-8");
            URL urlc = new URL("http://jjunest.cafe24.com/DB/insert_into_DRoomInfo.php");
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
