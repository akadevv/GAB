package com.example.lcd.gab.RoomList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.lcd.gab.MainActivity;
import com.example.lcd.gab.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by LCD on 2016-01-18.
 */
public class RoomListMain extends Fragment{

    String log = "이창대";
    private String masterId = MainActivity.getMasterInfo().getUserId(); // 마스터 핸드폰 번호 받기
    private RelativeLayout recyclerLayout; // 방 목록 만들 recyclerview
    private StringBuilder sb = new StringBuilder();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerLayout = (RelativeLayout) inflater.inflate(R.layout.room_list_main, container,false);
        Log.d(log, "this is my phone number" + masterId);

        getRoomInfoFromDB(masterId, "http://jjunest.cafe24.com/getRoomInfo.php"); // 마스터 핸드폰 번호 php로 넘기기


        return recyclerLayout;
    }

    class backgroundTask extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            String return_str="";

            while (return_str.equalsIgnoreCase("")) {
                try{
                    URL data_url = new URL(urls[0]);
                    System.out.println(urls[0]);
                    HttpURLConnection conn = (HttpURLConnection)data_url.openConnection();
                    if(conn != null){
                        conn.setConnectTimeout(10000);
                        conn.setUseCaches(false);
                        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                            Log.d(log, "This is Connection success");
                            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                            for(;;){
                                String line = br.readLine();
                                if(line == null) break;
                                jsonHtml.append(line + "\n");
                            }
                            br.close();
                        }
                        conn.disconnect();
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                return_str = jsonHtml.toString();
            }

            return jsonHtml.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println("this is onPost()" + s);
            try {
                JSONObject root = new JSONObject(s);
                JSONArray partyInfoJa = root.getJSONArray("party_info_cur");
                JSONArray roomInfoJa = root.getJSONArray("room_info_cur");
                for(int i=0; i<partyInfoJa.length(); i++){
                    JSONObject jo = partyInfoJa.getJSONObject(i);

                }
            }catch(Exception e){

            }
            super.onPostExecute(s);
        }


    }

    private void getRoomInfoFromDB(String masterPhoneNum, String phpUrl){
        try{
            String data = URLEncoder.encode("masterId", "UTF-8")+ "=" + URLEncoder.encode(masterId, "UTF-8");
            URL urlC = new URL(phpUrl);
            URLConnection conn = urlC.openConnection();
            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = null;

            while ((line = reader.readLine())!=null){
                sb.append(line);
                break;
            }
            System.out.println("this is response :"+sb);


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
