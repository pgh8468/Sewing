package com.example.sewing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Photo_Activity extends AppCompatActivity {

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    String insta_id;
    String login_id;
    private List<Item_Photo> item_photos;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainPage.class);
        intent.putExtra("login_ID",login_id);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_);
        Intent intent = getIntent();
        login_id = intent.getExtras().getString("login_id");
        insta_id = intent.getExtras().getString("insta_id");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE); //캡쳐 못하게 함
        Toast.makeText(getApplicationContext(),insta_id, Toast.LENGTH_SHORT);
        item_photos = new ArrayList<>();

        URL_make url_make = new URL_make("print_insta_ID");
        String inputURL = url_make.make_url();
        String response = "";

        try {
            response = new Show_Selected_Image().execute(inputURL, insta_id).get();

            if(response.equals("0")){
                Toast.makeText(getApplicationContext(),"선택된 계정에서 사람으로 분류된 사진이 존재하지 않습니다 ;ㅁ;",Toast.LENGTH_SHORT).show();

            }
            else if(response.equals("5")){
                Toast.makeText(getApplicationContext(),"게시글이 존재하지 않는 계정입니다.",Toast.LENGTH_SHORT).show();
            }
            else {
                JSONArray jsonArray = new JSONArray(response);

                for( int i =0 ; i <jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.e("array",jsonObject.get("src").toString());
                    if(!jsonObject.get("src").equals(null)){
                        //가져온 이미지가 null일때의 이미지 처리
                        Item_Photo ip = new Item_Photo();
                        ip.setPhoto(jsonObject.get("src").toString());
                        item_photos.add(ip);

                    }


                }

            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        viewPager = findViewById(R.id.viewPagerbyPhoto);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        FragSavedPhoto fragSavedPhoto = new FragSavedPhoto(item_photos, insta_id);
        fragSavedPhoto.setCheck(login_id);
        viewPagerAdapter.AddFragment(fragSavedPhoto, "");
        viewPager.setAdapter(viewPagerAdapter);

    }


    public class Show_Selected_Image extends AsyncTask<String, Void, String>{

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
                dos.writeBytes("insta_id="+params[1]);
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

                con.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output.toString();
        }
    }



}
