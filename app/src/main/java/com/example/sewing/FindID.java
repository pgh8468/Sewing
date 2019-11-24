package com.example.sewing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class FindID extends AppCompatActivity {

    ImageView imageview_findid;
    //차례대로 ID를 찾기위한 email, PW를 찾기위한 ID, Email
    TextInputLayout textInputLayout_email_findid, textInputLayout_id_findpw, textInputLayout_email_findpw;
    TextInputEditText textInputEditText_email_findid, textInputEditText_id_findpw, textInputEditText_email_findpw;
    Button button_find_id, button_find_pw;

    String find_user_ID = "";
    String find_user_PW = "";
    String inputURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        imageview_findid = findViewById(R.id.imageview_findid);

        textInputLayout_email_findid = findViewById(R.id.textInputLayout_email_findid);
        textInputLayout_id_findpw = findViewById(R.id.textInputLayout_id_findpw);
        textInputLayout_email_findpw = findViewById(R.id.textInputLayout_email_findpw);

        textInputEditText_email_findid = (TextInputEditText) findViewById(R.id.textInputEditText_email_findid);
        textInputEditText_id_findpw = (TextInputEditText) findViewById(R.id.textInputEditText_id_findpw);
        textInputEditText_email_findpw = (TextInputEditText) findViewById(R.id.textInputEditText_email_findpw);

        button_find_id = findViewById(R.id.button_find_id);
        button_find_pw = findViewById(R.id.button_find_pw);

        textInputLayout_email_findid.setCounterEnabled(true);
        textInputLayout_id_findpw.setCounterEnabled(true);
        textInputLayout_email_findpw.setCounterEnabled(true);

        textInputLayout_email_findid.setCounterMaxLength(100);
        textInputLayout_id_findpw.setCounterMaxLength(30);
        textInputLayout_email_findpw.setCounterMaxLength(100);

        button_find_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                URL_make url_make = new URL_make("find_id");
                inputURL = url_make.make_url();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            find_user_ID = new Find_info().execute(inputURL,"1",textInputEditText_email_findid.getText().toString()).get();
                            if(find_user_ID.equals("0")){
                                Toast.makeText(getApplicationContext(),"등록된 정보의 아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"등록된 정보의 아이디는 "+ find_user_ID+" 입니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }, 500);

            }
        });

        button_find_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                URL_make url_make = new URL_make("find_pw");
                inputURL = url_make.make_url();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            find_user_PW = new Find_info().execute(inputURL, "2", textInputEditText_id_findpw.getText().toString(), textInputEditText_email_findpw.getText().toString()).get();
                            if(find_user_PW.equals("0")){
                                Toast.makeText(getApplicationContext(),"입력하신 정보를 다시 한번 확인해주세요.", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"등록된 정보의 아이디는 "+ find_user_PW+" 입니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }, 500);
            }
        });


    }

    public class Find_info extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {

            StringBuilder output = new StringBuilder();

            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();

                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);
                DataOutputStream dos = new DataOutputStream(con.getOutputStream());

                if(params[1].equals("1")){
                    dos.writeBytes("find_id_email="+params[2]);
                }
                else if(params[1].equals("2")){
                    dos.writeBytes("find_pw_user_id="+params[2]+"&find_pw_user_email="+params[3]);
                }

                dos.flush();
                dos.close();

                InputStreamReader is = new InputStreamReader(con.getInputStream());
                BufferedReader reader = new BufferedReader(is);
                String results = "";

                while(true){
                    results = reader.readLine();
                    if(results == null){
                        break;
                    }output.append(results);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output.toString();
        }
    }
}
