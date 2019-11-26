package com.example.sewing;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PhotoRecyclerViewAdapter extends RecyclerView.Adapter<PhotoRecyclerViewAdapter.MyVIewHolder> {

    private Context context;
    private List<Item_Photo> photo_data;
    RequestOptions option;
    String login_id;

    public PhotoRecyclerViewAdapter(Context context, List<Item_Photo> photo_data, String login_id) {
        this.context = context;
        this.photo_data = photo_data;
        this.login_id = login_id;
        Log.d(Integer.toString(photo_data.size()), "test check/adapter");
        Log.e("photorecyclerviewadapter :", login_id);

        //request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.internet).error(R.drawable.internet);

    }

    @NonNull
    @Override
    public MyVIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View  view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        view = layoutInflater.inflate(R.layout.cardview_photo, parent, false);

        return new MyVIewHolder(view,login_id);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVIewHolder holder, final int position) {
//        holder.save_photo.setImageResource(photo_data.get(position).getPhoto());

        Glide.with(context).load(photo_data.get(position).getPhoto()).into(holder.save_photo);

        Log.e("photorecyclerviewadapter2 :", login_id);

        holder.save_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(context,context.getClass().getSimpleName().trim(),Toast.LENGTH_SHORT).show();

                if(context.getClass().getSimpleName().trim().equals("Photo_Activity")){

                    //Toast.makeText(context, login_id,Toast.LENGTH_SHORT).show();
                    //Toast.makeText(context,"main",Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("사진 저장").setMessage("해당 사진을 개인 사진함에 저장하시겠습니까?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String src = photo_data.get(position).getPhoto();
                            Log.e("src", src);
                            URL_make url_make = new URL_make("save_selected_image");
                            String inputURL = url_make.make_url();
                            String results = "";
                            try {
                                results = new SaveSelectedImage().execute(inputURL, login_id, src).get();

                                if(results.equals("0")){
                                    Toast.makeText(context,"저장이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                                }
                                else if (results.equals("1")){
                                    Toast.makeText(context,"사진 저장에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                }
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

//                           Toast.makeText(context,photo_data.get(position).getPhoto(),Toast.LENGTH_SHORT).show();
//                            Toast.makeText(context,login_id,Toast.LENGTH_SHORT).show();


                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context,"No 버튼을 눌렀습니다.",Toast.LENGTH_SHORT).show();

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
                else{
                    //Toast.makeText(context,"hello",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return photo_data.size();
    }

    public static class MyVIewHolder extends RecyclerView.ViewHolder {

        ImageView save_photo;
        String login_id;

        public MyVIewHolder(@NonNull View itemView,String login_id) {

            super(itemView);
            this.login_id = login_id;
            save_photo = itemView.findViewById(R.id.photo);
        }
    }

    public class SaveSelectedImage extends AsyncTask<String, Void, String>{

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
                params[2] = params[2].replace("&","*");
                dos.writeBytes("image_src="+params[2]+"&login_id="+params[1]);
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
