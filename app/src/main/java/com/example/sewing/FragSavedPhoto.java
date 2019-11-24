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
    private String check;

    public FragSavedPhoto(){

    }

    public FragSavedPhoto(String check){
        this.check = check;
        Bundle args = new Bundle();
        args.putString("selected_id", check);
        this.setArguments(args);

    }

    public FragSavedPhoto(List<Item_Photo> item_photos){
        this.item_photo = item_photos;
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

        PhotoRecyclerViewAdapter photoRecyclerViewAdapter = new PhotoRecyclerViewAdapter(getContext(), item_photo);
        photo_recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        photo_recyclerview.setAdapter(photoRecyclerViewAdapter);

        //photo_recyclerview.setHasFixedSize(true);


        if(check != null){
            check = getArguments().getString("selected_id");
            Activity act = getActivity();
            Log.d(check, "test click/f");
            Toast.makeText(act,"흥민아 떳냐? "+ check, Toast.LENGTH_SHORT).show();
            URL_make url_make = new URL_make("print_insta_ID");
            String inputURL = url_make.make_url();
            String response = "";
            String url_1 = "https://icon-library.net/images/no-image-available-icon/no-image-available-icon-6.jpg";

            try {
                response = new get_image_resource().execute(inputURL).get();
                Image_Input_Into_List image_input_into_list = new Image_Input_Into_List();
                image_input_into_list.execute(response);
//                JSONArray jsonArray = new JSONArray(response);
//                int json_lenght = jsonArray.length();
//
//                for(int i=0; i<jsonArray.length(); i++){
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    //Log.e("ary", jsonObject.get("src").toString());
//
//                    if (jsonObject.get("src").toString() == null){
//                        url_1 = "https://icon-library.net/images/no-image-available-icon/no-image-available-icon-6.jpg";
//                    }
//                    Item_Photo ip = new Item_Photo();
//                    ip.setPhoto(jsonObject.get("src").toString());
//                    item_photo.add(ip);
//
//                    //url_1 = jsonObject.get("src").toString();
//                }

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.e("test","여기까지는 작업하냐?");
        return  view;

    }

    @SuppressLint("ResourceType")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if(check != null){
//            check = getArguments().getString("selected_id");
//            Activity act = getActivity();
//            Log.d(check, "test click/f");
//            Toast.makeText(act,"흥민아 떳냐? "+ check, Toast.LENGTH_SHORT).show();
//            URL_make url_make = new URL_make("print_insta_ID");
//            String inputURL = url_make.make_url();
//            String response = "";
//            String url_1 = "https://icon-library.net/images/no-image-available-icon/no-image-available-icon-6.jpg";
//
//            try {
//                response = new get_image_resource().execute(inputURL).get();
//                JSONArray jsonArray = new JSONArray(response);
//                //Log.e("ary", jsonArray.toString());
//
//                for(int i=0; i<jsonArray.length(); i++){
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    //Log.e("ary", jsonObject.get("src").toString());
//
//                    if (jsonObject.get("src").toString() == null){
//                        url_1 = "https://icon-library.net/images/no-image-available-icon/no-image-available-icon-6.jpg";
//                    }
//                    Item_Photo ip = new Item_Photo();
//                    ip.setPhoto(jsonObject.get("src").toString());
//                    item_photo.add(ip);
//
//                    //url_1 = jsonObject.get("src").toString();
//                }
//
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }

//        item_photo.add(new Item_Photo(R.drawable.tprud));
//        item_photo.add(new Item_Photo(R.drawable.ab));
//        item_photo.add(new Item_Photo(R.drawable.ac));
//        item_photo.add(new Item_Photo(R.drawable.ad));
//        item_photo.add(new Item_Photo(R.drawable.aw));

    }

    public class get_image_resource extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            StringBuilder output = new StringBuilder();

            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();

                con.setRequestMethod("POST");
                con.setDoOutput(true);
                con.setRequestProperty("Accept", "application/x-www-form-urlencoded;charset=UTF-8");

                String results = "";
                if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));

                    while((results = br.readLine()) != null){
                        output.append(results);
                    }
                    br.close();
                }
                else{
                    //404 error
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

    public class Image_Input_Into_List extends AsyncTask<String, Void, Void>{


        @Override
        protected Void doInBackground(String... params) {


            try {
                JSONArray jsonArray = new JSONArray(params[0]);

                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //Log.e("ary", jsonObject.get("src").toString());

                    if (jsonObject.get("src").toString() == null){
                        //url_1 = "https://icon-library.net/images/no-image-available-icon/no-image-available-icon-6.jpg";
                    }
                    Item_Photo ip = new Item_Photo();
                    ip.setPhoto(jsonObject.get("src").toString());
                    item_photo.add(ip);

                    //url_1 = jsonObject.get("src").toString();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
