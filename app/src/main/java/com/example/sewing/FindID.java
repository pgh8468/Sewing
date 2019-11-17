package com.example.sewing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

public class FindID extends AppCompatActivity {

    ImageView imageview_findid;
    //차례대로 ID를 찾기위한 email, PW를 찾기위한 ID, Email
    TextInputLayout textInputLayout_email_findid, textInputLayout_id_findpw, textInputLayout_email_findpw;
    Button button_find_id, button_find_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        imageview_findid = findViewById(R.id.imageview_findid);
        textInputLayout_email_findid = findViewById(R.id.textInputLayout_email_findid);
        textInputLayout_id_findpw = findViewById(R.id.textInputLayout_id_findpw);
        textInputLayout_email_findpw = findViewById(R.id.textInputLayout_email_findpw);
        button_find_id = findViewById(R.id.button_find_id);
        button_find_pw = findViewById(R.id.button_find_pw);

    }
}
