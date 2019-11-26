package com.example.sewing;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class FragSavedShop extends Fragment {

    private View view;
    private com.google.android.material.textfield.TextInputEditText Edit_Insert_shopUrl;
    private Button Add_shop_btn;
    private TextInputLayout textInputLayout;
    String url;

    public static FragSavedShop newinstance() {
        FragSavedShop fragSavedShop = new FragSavedShop();
        return fragSavedShop;
    }

    //frag_saved_shop이랑 연동
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_saved_shopping, container, false);

        final WebView webView = view.findViewById(R.id.webView);
        Edit_Insert_shopUrl = view.findViewById(R.id.Edit_Insert_shopUrl);
        Add_shop_btn = view.findViewById(R.id.Add_shop_btn);
        textInputLayout = view.findViewById(R.id.textInputLayout_Shop_Url);

        Edit_Insert_shopUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Add_shop_btn.setText("URL을 입력하여 인터넷을 사용");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if( !(s.toString().contains(".com")) || !(s.toString().contains("www."))){
                    textInputLayout.setError("잘못된 url 입니다.");
                }
                else{
                    textInputLayout.setError(null);
                }

            }
        });


        Add_shop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                url = Edit_Insert_shopUrl.getText().toString();
                url = "https://"+ url;

                if(url.equals("")) {
                    Activity act = getActivity();

                    Toast.makeText(act, "http://를 포함한 url입력하시오.;", Toast.LENGTH_SHORT).show();

                } else {
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setWebViewClient(new WebViewClient());
                    webView.loadUrl(url);
                    webView.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
