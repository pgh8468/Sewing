package com.example.sewing;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FragSavedInsta extends Fragment {

    private TextInputLayout textInputLayout_Insta_id;
    private TextInputEditText Edit_Insert_Insta_id;
    private Button btnPlus;
    private ImageButton btnDel;
    private View view;
    private RecyclerView insta_recyclerview;
    private List<Item_insta> item_instas;
    String logined_id;
    ViewPager vp;


    public FragSavedInsta (){

    }

    public FragSavedInsta(String logined_id, ViewPager vp){
        this.logined_id = logined_id;
        this.vp = vp;

    }

    public FragSavedInsta (List<Item_insta> item_instas){
        this.item_instas = item_instas;

    }

    public static FragSavedInsta newinstance() {
        FragSavedInsta fragSavedInsta = new FragSavedInsta();

        return fragSavedInsta;
    }
    
    //frag_saved_insta랑 연동
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_saved_insta, container, false);

        textInputLayout_Insta_id = (TextInputLayout)view.findViewById(R.id.textInputLayout_Insta_id);
        Edit_Insert_Insta_id = (TextInputEditText)view.findViewById(R.id.Edit_Insert_Insta_id);
        insta_recyclerview = view.findViewById(R.id.insta_recyclerview);
        btnPlus = (Button)view.findViewById(R.id.btnPlus);
        btnDel = (ImageButton)view.findViewById(R.id.Del_insta_btn);
        final InstaRecyclerViewAdapter instaRecyclerViewAdapter = new InstaRecyclerViewAdapter(getContext(), item_instas, logined_id, vp);
        insta_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        insta_recyclerview.setAdapter(instaRecyclerViewAdapter);

        //사용자가 추가한 목록 출력
        URL_make url_make = new URL_make("saved_instaID");
        String inputURL = url_make.make_url();
        String response = "";
        try {
            response = new showAddedInsta().execute(inputURL, logined_id).get();
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Item_insta ii = new Item_insta(jsonObject.get("sInstaID").toString(), jsonObject.get("sFollower").toString(), jsonObject.get("sUrl").toString());
                item_instas.add(ii);
                instaRecyclerViewAdapter.notifyDataSetChanged();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //인스타그램 아이디를 입력하여 fragment와 db에 추가
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                URL_make url_make = new URL_make("find_insta_id");
                String inputURL = url_make.make_url();
                String response = "";
                try {
                    response = new Add_Insta_User().execute(inputURL,Edit_Insert_Insta_id.getText().toString(), logined_id).get();
                    if(response.equals("0")){
                        Activity act = getActivity();
                        Toast.makeText(act, "비공개 계정이거나 없는 계정을 입력하셨습니다.", Toast.LENGTH_SHORT).show();
                    }
//                    Log.e("checkplease",Edit_Insert_Insta_id.getText().toString());
//                    Log.e("checkplease",response);

                    else if(response.equals("1")){
                        Activity act = getActivity();
                        Toast.makeText(act, "입력하신 사용자는 이미 즐겨찾기 내역에 존재합니다.", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        JSONObject jsonObject = new JSONObject(response);
                        Item_insta ii = new Item_insta(jsonObject.get("ID").toString(), jsonObject.get("followers").toString(),jsonObject.get("url").toString());
                        item_instas.add(ii);

                        instaRecyclerViewAdapter.notifyDataSetChanged();
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        instaRecyclerViewAdapter.setItemClick(new InstaRecyclerViewAdapter.ItemClick() {
            @Override
            public void OnClick(View v, int position) {

                String insta_id = item_instas.get(position).getId();
                Log.e("insta_frag_test :", insta_id+position);
                Activity act = getActivity();
                Intent intent = new Intent(act, Photo_Activity.class);
                intent.putExtra("insta_id", insta_id);
                intent.putExtra("login_id",logined_id);
                startActivity(intent);

            }
        });


//        insta_recyclerview.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//
//                View child = rv.findChildViewUnder(e.getX(), e.getY());
//
//                if( child != null){
//                    int position = rv.getChildAdapterPosition(child);
//                    String insta_id = item_instas.get(position).getId();
//                    Log.d(insta_id, "test click");
////                    FragSavedPhoto fragSavedPhoto = new FragSavedPhoto(insta_id);
////                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
////                    transaction.replace(R.id.testID, new FragSavedPhoto(insta_id));
////                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
////                    transaction.addToBackStack(null);
////                    transaction.commit();
//
////                    vp.setCurrentItem(vp.getCurrentItem()+1, true);
//
//
//                }
//
//                return true;
//            }
//
//            @Override
//            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        item_instas = new ArrayList<>();

    }

    public class Add_Insta_User extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {

            StringBuilder output = new StringBuilder();

            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();

                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);
                //con.setRequestProperty("Accept", "application/x-www-form-urlencoede;charset=UTF-8");

                DataOutputStream dos = new DataOutputStream(con.getOutputStream());
                dos.writeBytes("input_insta_id="+params[1]+"&UserID="+params[2]);
                dos.flush();
                dos.close();

                InputStreamReader is = new InputStreamReader(con.getInputStream());
                BufferedReader reader = new BufferedReader(is);
                String results = "";

                while (true){
                    results = reader.readLine();
                    if(results == null){
                        break;
                    }output.append(results);
                }
                Log.e("checkplease_server",output.toString());

                con.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e){
                e.printStackTrace();
                return "0";
            } catch (IOException e) {
                e.printStackTrace();
            }

            return output.toString();
        }
    }

    public class showAddedInsta extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {

            StringBuilder output  = new StringBuilder();

            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();

                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);

                DataOutputStream dos = new DataOutputStream(con.getOutputStream());
                dos.writeBytes("UserID="+params[1]);
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
