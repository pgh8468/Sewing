package com.example.sewing;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class FragSavedPhoto extends Fragment {

    private View view;
    private RecyclerView photo_recyclerview;
    private List<Item_Photo> item_photo = new ArrayList<>();
    private String check; // 이거 로그인 된 아이디임
    String insta_id;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public FragSavedPhoto(){

    }

    public FragSavedPhoto(String check){
        this.check = check;
        Log.e("fragsavephoto : ",check);

    }

    public FragSavedPhoto(List<Item_Photo> item_photos, String insta_id){
        this.item_photo = item_photos;
        this.insta_id = insta_id;
    }

    public static FragSavedPhoto newinstance(String ID) {
        FragSavedPhoto fragSavedPhoto = new FragSavedPhoto();
        Bundle args = new Bundle();
        args.putString("selected_id", ID);
        fragSavedPhoto.setArguments(args);
        return fragSavedPhoto;
    }

    //frag_saved_photo랑 연동
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_saved_photo, container, false);

        photo_recyclerview = view.findViewById(R.id.photo_recyclerview);

        if(insta_id == null){

            URL_make url_make = new URL_make("show_saved_Image");
            String inputURL = url_make.make_url();
            String response = "";

            try {
                //check = logined_id
                response = new Take_Saved_Image().execute(inputURL, check).get();

                if(response.equals("0")){
                    Activity act = getActivity();
                    //Toast.makeText(act, "저장되어 있는 사진이 없습니다.", Toast.LENGTH_SHORT).show();
                }

                else{
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i =0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Item_Photo ip = new Item_Photo();
                        if(jsonObject.get("iURL").toString() != null){
                            //가져온 이미지가 null일 경우의 default image 처리
                            ip.setPhoto(jsonObject.get("iURL").toString());
                            item_photo.add(ip);
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

        }

        PhotoRecyclerViewAdapter photoRecyclerViewAdapter = new PhotoRecyclerViewAdapter(getContext(), item_photo, check);
        photo_recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        photo_recyclerview.setAdapter(photoRecyclerViewAdapter);

        return  view;

    }

    @SuppressLint("ResourceType")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public class Take_Saved_Image extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {

            StringBuilder output = new StringBuilder();

            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();

                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);
                //con.setRequestProperty("Accept", "application/x-www-form-urlencoded;charset=UTF-8");

                DataOutputStream dos = new DataOutputStream(con.getOutputStream());
                dos.writeBytes("login_id="+params[1]);
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
