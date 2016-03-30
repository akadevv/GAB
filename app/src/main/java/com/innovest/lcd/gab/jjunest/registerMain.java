package com.innovest.lcd.gab.jjunest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.innovest.lcd.gab.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016-01-08.
 */
public class registerMain extends Activity {
    EditText id_edittext;
    EditText password_edittext;
    EditText passwordcheck_edittext;
    EditText phonenum_edittext_1;
    EditText phonenum_edittext_2;
    EditText phonenum_edittext_3;
    Button saveButton;
    String phonenum_full;
    String DBlink;
    Context mycontext;
    String phoneNumber;
    TelephonyManager telManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_register);
         mycontext= getApplicationContext();
         id_edittext = (EditText) findViewById(R.id.id_edittext);
         password_edittext = (EditText) findViewById(R.id.password_edittext);
         passwordcheck_edittext = (EditText) findViewById(R.id.passwordcheck_edittext);
         phonenum_edittext_1 = (EditText) findViewById(R.id.phone_number_editview_1);
         phonenum_edittext_2 = (EditText) findViewById(R.id.phone_number_editview_2);
         phonenum_edittext_3 = (EditText) findViewById(R.id.phone_number_editview_3);

        telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        phoneNumber = telManager.getLine1Number();

        if(phoneNumber.length()==11){
            System.out.println("this is phonenumber.length ==11 :"+phoneNumber.length());
            phonenum_edittext_1.setText(phoneNumber.substring(0,3));
            phonenum_edittext_2.setText(phoneNumber.substring(3,7));
            phonenum_edittext_3.setText(phoneNumber.substring(7,11));
        }else if(phoneNumber.length() == 10){
            System.out.println("this is phonenumber.length ==10 :"+phoneNumber.length());
            phonenum_edittext_1.setText(phoneNumber.substring(0,3));
            phonenum_edittext_2.setText(phoneNumber.substring(3,6));
            phonenum_edittext_3.setText(phoneNumber.substring(6,10));
        }else{


        }

//        System.out.println("byjjnest_ id_edittext.gettext : "+id_edittext.getText());
//        System.out.println("byjjnest_ id_edittext.gettext.tostring : "+id_edittext.getText().toString());
//        System.out.println("byjjunest_ phonenum_edittext_3.gettext.tostring : "+phonenum_edittext_3.getText().toString());
//        System.out.println("byjjunest_ phonenum_edittext_3.gettext : "+phonenum_edittext_3.getText());
//        System.out.println("byjjunest_ phonenum_full is : "+phonenum_full);
         saveButton = (Button) findViewById(R.id.registerButton);
         DBlink = "http://jjunest.cafe24.com/DB/insert_into_registerDB.php";

        //password check need ---

        // password check need ---
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("byjjunest_ onclicklistener");
                phonenum_full = phonenum_edittext_1.getText().toString()+phonenum_edittext_2.getText().toString()+phonenum_edittext_3.getText().toString();
                System.out.println("byjjunest_ this is phonenum_full" +phonenum_full);
                System.out.println("byjjunest_ this is id_edittext_string" +id_edittext.getText().toString());
              registerIntoDB(id_edittext.getText().toString(), password_edittext.getText().toString(), phonenum_full);

                System.out.println("byjjunest_phonenum_full after :" + phonenum_full);
            }
        });
    }

   public  void registerIntoDB(String input_id, String input_password, String input_phonenum) {

       System.out.println("byjjunest_ registerIntoDB() start");
       class InsertData extends AsyncTask<String, Void, String> {
          ProgressDialog loadingbar;
           @Override
           protected void onPreExecute() {
               System.out.println("byjjunest_ onPreExecute() ");
               super.onPreExecute();
              // loadingbar = ProgressDialog.show(mycontext, "Please wait", null, true, true);
           }
           @Override
           protected String doInBackground(String... params) {
               System.out.println("byjjunest_ doInBackground() ");
               try{
                   String userid = (String)params[0];
                   String userpassword = (String)params[1];
                   String userphonenum = (String)params[2];


                   String data  = URLEncoder.encode("userid_in", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8");
                   data += "&" + URLEncoder.encode("userpassword_in", "UTF-8") + "=" + URLEncoder.encode(userpassword, "UTF-8");
                   data += "&" + URLEncoder.encode("userphonenum_in", "UTF-8") + "=" + URLEncoder.encode(userphonenum, "UTF-8");
                   URL url = new URL(DBlink);
                   URLConnection conn = url.openConnection();

                   conn.setDoOutput(true);
                   OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                   System.out.println("byjjunest_ this is data to DB php :"  + data);
                   wr.write(data);
                   wr.flush();

                   BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                   StringBuilder sb = new StringBuilder();
                   String line = null;

                   // Read Server Response
                   while((line = reader.readLine()) != null)
                   {
                       sb.append(line);
                       break;
                   }
                   return sb.toString();
               }
               catch(Exception e){
                   return new String("Exception: " + e.getMessage());
               }
           }
            @Override
           protected void onPostExecute(String s) {
                System.out.println("byjjunest_ onPostExecute() ");
               super.onPostExecute(s);
               // loadingbar.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }



       }

       InsertData insertTask = new InsertData();
       System.out.println("byjjunest_ after making new insertTask() ");
       insertTask.execute(input_id,input_password,input_phonenum);
    }




}
