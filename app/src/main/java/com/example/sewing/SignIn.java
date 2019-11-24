package com.example.sewing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class SignIn extends AppCompatActivity {

    ImageView imageview_signin; //회원가입 최상단의 이미지 뷰
    //차례대로 id, pw, pw 확인, email 입력
    TextInputLayout textInputLayout_id, textInputLayout_pw, textInputLayout_pw_check, textInputLayout_email;
    TextInputEditText textInputEditText_id,textInputEditText_pw,textInputEditText_pw_check,textInputEditText_email;
    Button btn_finish;
    User_info user_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        textInputLayout_id = (TextInputLayout) findViewById(R.id.textInputLayout_id);
        textInputLayout_pw = (TextInputLayout) findViewById(R.id.textInputLayout_pw);
        textInputLayout_pw_check = (TextInputLayout) findViewById(R.id.textInputLayout_pw_check);
        textInputLayout_email = (TextInputLayout) findViewById(R.id.textInputLayout_email);

        textInputEditText_id = (TextInputEditText) findViewById(R.id.textInputEditText_id);
        textInputEditText_pw = (TextInputEditText) findViewById(R.id.textInputEditText_pw);
        textInputEditText_pw_check = (TextInputEditText) findViewById(R.id.textInputEditText_pw_check);
        textInputEditText_email = (TextInputEditText) findViewById(R.id.textInputEditText_email);

        textInputLayout_id.setCounterEnabled(true);
        textInputLayout_pw.setCounterEnabled(true);
        textInputLayout_pw_check.setCounterEnabled(true);
        textInputLayout_email.setCounterEnabled(true);

        textInputLayout_id.setCounterMaxLength(30);
        textInputLayout_pw.setCounterMaxLength(50);
        textInputLayout_pw_check.setCounterMaxLength(50);
        textInputLayout_email.setCounterMaxLength(100);

        btn_finish = (Button) findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String temp_id = textInputEditText_id.getText().toString();
                String temp_pw = textInputEditText_pw.getText().toString();
                String temp_pw_check = textInputEditText_pw_check.getText().toString();
                String temp_email = textInputEditText_email.getText().toString();
                String idcheck;
                String emailcheck;
                String inputURL;
                String inputURL2;


                try {

                    URL_make url_make = new URL_make("check_id");
                    inputURL = url_make.make_url();
                    idcheck = new duplicateCheck().execute(inputURL, temp_id,"0").get();
                    //Toast.makeText(getApplicationContext(),idcheck,Toast.LENGTH_SHORT).show();

                    URL_make url_make1 = new URL_make("check_email");
                    inputURL2 = url_make1.make_url();
                    emailcheck = new duplicateCheck().execute(inputURL2, temp_email,"1").get();
                    //Toast.makeText(getApplicationContext(),emailcheck,Toast.LENGTH_SHORT).show();

                    if(temp_id.length()==0 || temp_pw.length()==0 || temp_pw_check.length()==0 || temp_email.length() ==0){
                        Toast.makeText(getApplicationContext(), "값을 입력해주세요.",Toast.LENGTH_SHORT).show();
                    }
                    else if(idcheck.equals("0")){
                        Toast.makeText(getApplicationContext(), "입력한 아이디가 이미 존재합니다.",Toast.LENGTH_SHORT).show();
                        textInputEditText_id.setText("");
                    }
                    else if(!(temp_pw.equals(temp_pw_check))){
                        Toast.makeText(getApplicationContext(), "비밀번호가 서로 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                        textInputEditText_pw.setText(""); textInputEditText_pw_check.setText("");
                    }
                    else if(!(temp_email.contains("@")) || !(temp_email.contains(".com"))){
                        Toast.makeText(getApplicationContext(), "이메일 형식을 맞춰주세요.",Toast.LENGTH_SHORT).show();
                    }
                    else if(emailcheck.equals("0")){
                        Toast.makeText(getApplicationContext(), "입력한 이메일이 이미 존재합니다.",Toast.LENGTH_SHORT).show();
                        textInputEditText_email.setText("");
                    }
                    else{

                        URL_make url_make2 = new URL_make("add_user");
                        inputURL2 = url_make2.make_url();
                        String result = new SignUPMember().execute(inputURL2,temp_id,temp_pw,temp_email).get();
                        if(result.equals("1")){
                            Toast.makeText(getApplicationContext(), "Thanks for sign up!",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "입력값이 유효하지 않습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });


    }

    public class duplicateCheck extends AsyncTask<String, Void, String> {

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

                if(params[2].equals("0"))
                    dos.writeBytes("UserID="+params[1]);
                if(params[2].equals("1"))
                    dos.writeBytes("UserEMAIL="+params[1]);
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
                Log.e("output:", output.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output.toString();
        }
    }

    public class SignUPMember extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {

            StringBuilder output = new StringBuilder();

            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();

                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);

                OutputStream os = con.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                bw.write("UserID="+params[1]+"&UserPW="+params[2]+"&UserEMAIL="+params[3]);
                bw.flush();
                bw.close();

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
