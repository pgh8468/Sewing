package com.example.sewing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

public class SignIn extends AppCompatActivity {

    ImageView imageview_signin; //회원가입 최상단의 이미지 뷰
    //차례대로 id, pw, pw 확인, email 입력
    TextInputLayout TextInputLayout_id,textInputLayout_pw,textInputLayout_pw_check,textInputLayout_email;
    Button btn_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }
}
