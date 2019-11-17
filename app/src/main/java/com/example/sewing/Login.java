package com.example.sewing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    ImageView imageView_Login; //로그인 화면 최상단 이미지뷰
    TextInputLayout textInputLayout_login_id; //ID입력
    TextInputLayout textInputLayout_login_pw; //PW입력
    TextInputEditText Edit_Login_Id, Edit_Login_Pw;
    Button button_signin, button_signup, button_find; //차례대로 회원가입, 로그인, ID/PW찾기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageView_Login = findViewById(R.id.imageView_Login);
        textInputLayout_login_id = findViewById(R.id.textInputLayout_login_id);
        textInputLayout_login_pw = findViewById(R.id.textInputLayout_pw);
        button_signin = findViewById(R.id.button_signin);
        button_signup = findViewById(R.id.button_signup);
        button_find = findViewById(R.id.button_find);
        Edit_Login_Id = findViewById(R.id.Edit_Login_Id);
        Edit_Login_Pw = findViewById(R.id.Edit_Login_Pw);

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

                Intent intent = new Intent(Login.this, MainPage.class);
                startActivity(intent);
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
}
