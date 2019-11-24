package com.example.sewing;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class InstaRecyclerViewAdapter extends RecyclerView.Adapter<InstaRecyclerViewAdapter.InstaViewHolder> {

    Context context;
    List<Item_insta> insta_data; //item_insta의 아이템을 가르킴
    String logined_ID;
    ViewPager vp;


    public interface OnListItemSelectedInterface{
        void onItemSelected(View v, int position);
    }

    private OnListItemSelectedInterface onListItemSelectedInterface;

    public InstaRecyclerViewAdapter(Context context, List<Item_insta> insta_data, String logined_ID, ViewPager vp) {
        this.context = context;
        this.insta_data = insta_data;
        this.logined_ID = logined_ID;
        this.vp = vp;

    }

    @NonNull
    @Override
    public InstaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        view = LayoutInflater.from(context).inflate(R.layout.item_insta, parent, false);
        InstaViewHolder instaholder = new InstaViewHolder(view);

        return instaholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final InstaViewHolder holder, final int position) {

        holder.save_id.setText(insta_data.get(position).getId());
        holder.save_follow.setText(insta_data.get(position).getFollow());
//        holder.save_profile.setImageResource(insta_data.get(position).getProfile());
        Glide.with(this.context).load(insta_data.get(position).getProfile()).into(holder.save_profile);

//        //원하는 ID 클릭 시 인스타그램에서 사진 가져오도록 함
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, FragSavedPhoto.class);
//
//                Toast.makeText(context, holder.save_id.getText(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(context,logined_ID, Toast.LENGTH_SHORT).show();
//
//                vp.setCurrentItem(vp.getCurrentItem()+1, true);
//
//
//            }
//        });

        //ID 삭제
        holder.delete_Id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                URL_make url_make = new URL_make("delete_instaID");
                String inputURL = url_make.make_url();
                try {
                    String result = new DeleteInstaID().execute(inputURL, holder.save_id.getText().toString(), logined_ID).get();

                    if(result.equals("0")){
                        Toast.makeText(context, "삭제에 실패하였습니다. 잠시후에 다시 시도해주세요 :)",Toast.LENGTH_SHORT).show();
                    }
                    else if(result.equals("1")){
                        Toast.makeText(context, "삭제 성공 !", Toast.LENGTH_SHORT).show();
                        insta_data.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, insta_data.size());
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


//                Toast.makeText(context, "사진 가져오기 제발 ㅠㅠ"+holder.save_id.getText(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(context,logined_ID, Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //검색 구현
    @Override
    public int getItemCount() {
        return insta_data.size();
    }

    public static class InstaViewHolder extends RecyclerView.ViewHolder{

        private ImageView save_profile;
        private TextView save_id;
        private TextView save_follow;
        private ImageButton delete_Id;



        public InstaViewHolder(@NonNull final View itemView) {
            super(itemView);

            save_profile = itemView.findViewById(R.id.Img_profile);
            save_id = itemView.findViewById(R.id.Insta_id);
            save_follow = itemView.findViewById(R.id.Insta_follow);
            delete_Id = itemView.findViewById(R.id.Del_insta_btn);


        }

    }

    public class DeleteInstaID extends AsyncTask<String, Void, String>{

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
                dos.writeBytes("insta_id="+params[1]+"&UserID="+params[2]);
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


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output.toString();
        }
    }
}
