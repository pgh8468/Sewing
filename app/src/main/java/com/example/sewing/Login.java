package com.example.sewing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
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

public class Login extends AppCompatActivity {

    ImageView imageView_Login; //로그인 화면 최상단 이미지뷰
    TextInputLayout textInputLayout_login_id; //ID입력
    TextInputLayout textInputLayout_login_pw; //PW입력
    TextInputEditText Edit_Login_Id, Edit_Login_Pw;
    Button button_signin, button_signup, button_find; //차례대로 회원가입, 로그인, ID/PW찾기
    int web_print = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageView_Login = findViewById(R.id.imageView_Login);
        textInputLayout_login_id = findViewById(R.id.textInputLayout_login_id);
        textInputLayout_login_pw = findViewById(R.id.textInputLayout_login_pw);
        button_signin = findViewById(R.id.button_signin);
        button_signup = findViewById(R.id.button_signup);
        button_find = findViewById(R.id.button_find);
        Edit_Login_Id = findViewById(R.id.Edit_Login_Id);
        Edit_Login_Pw = findViewById(R.id.Edit_Login_Pw);

        textInputLayout_login_id.setCounterEnabled(true);
        textInputLayout_login_pw.setCounterEnabled(true);
        textInputLayout_login_id.setCounterMaxLength(30);
        textInputLayout_login_pw.setCounterMaxLength(50);

        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //버튼 클릭 시 색상 변경
                button_signin.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getAction() == MotionEvent.ACTION_DOWN) {
                            button_signin.setTextColor(Color.GREEN);
                        } else if(event.getActionButton() == MotionEvent.ACTION_UP){
                            button_signin.setTextColor(Color.WHITE);
                        }
                        return false;
                    }
                });

                Intent intent = new Intent(Login.this, SignIn.class);
                startActivity(intent);
            }
        });

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //버튼 클릭 시 색상 변경
                button_signup.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getAction() == MotionEvent.ACTION_DOWN) {
                            button_signup.setTextColor(Color.GREEN);
                        } else if(event.getActionButton() == MotionEvent.ACTION_UP){
                            button_signup.setTextColor(Color.WHITE);
                        }
                        return false;
                    }
                });

                App_login al = new App_login();
                URL_make url_make = new URL_make("app_login");
                String inputURL = url_make.make_url();
                al.execute(inputURL,Edit_Login_Id.getText().toString(), Edit_Login_Pw.getText().toString());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(web_print ==0)
                            Toast.makeText(getApplicationContext(),"아이디를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();

                        else if (web_print == 1){

                            Intent intent = new Intent(Login.this, MainPage.class);
                            Toast.makeText(getApplicationContext(),"로그인 성공!", Toast.LENGTH_SHORT).show();
                            intent.putExtra("login_ID", Edit_Login_Id.getText().toString());
                            Edit_Login_Id.setText("");
                            Edit_Login_Pw.setText("");
                            startActivity(intent);
                        }

                        else if (web_print == 2){
                            Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();

                        }

                        else{
                            Toast.makeText(getApplicationContext(),"값이 유효하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, 500);


            }
        });

        Edit_Login_Id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().length()>31){
                    textInputLayout_login_id.setError("입력값의 범위를 벗어나셨습니다.");
                }
                else{
                    textInputLayout_login_id.setError(null);
                }

            }
        });

        Edit_Login_Pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().length()>31){
                    textInputLayout_login_pw.setError("입력값의 범위를 벗어나셨습니다.");
                }
                else{
                    textInputLayout_login_pw.setError(null);
                }

            }
        });

        button_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button_find.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getAction() == MotionEvent.ACTION_DOWN) {
                            button_find.setTextColor(Color.GREEN);
                        } else if(event.getAction() == MotionEvent.ACTION_UP){
                            button_find.setTextColor(Color.WHITE);
                        }
                        return false;
                    }
                });

                Intent intent = new Intent(Login.this, FindID.class);
                startActivity(intent);

            }
        });

    }

    public class App_login extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {

            StringBuilder output = new StringBuilder();

            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();

                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);
                DataOutputStream dos = new DataOutputStream(con.getOutputStream());
                dos.writeBytes("UserID="+params[1]+"&UserPW="+params[2]);
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

                results = output.toString();
                web_print = Integer.parseInt(results);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
