package com.example.sewing;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PhotoRecyclerViewAdapter extends RecyclerView.Adapter<PhotoRecyclerViewAdapter.MyVIewHolder> {

    private Context context;
    private List<Item_Photo> photo_data;

    public PhotoRecyclerViewAdapter(Context context, List<Item_Photo> photo_data) {
        this.context = context;
        this.photo_data = photo_data;


    }

    @NonNull
    @Override
    public MyVIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View  view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        view = layoutInflater.inflate(R.layout.cardview_photo, parent, false);

        return new MyVIewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVIewHolder holder, int position) {
//        holder.save_photo.setImageResource(photo_data.get(position).getPhoto());

        Glide.with(context).load(photo_data.get(position).getPhoto()).into(holder.save_photo);
    }

    @Override
    public int getItemCount() {
        return photo_data.size();
    }

    public static class MyVIewHolder extends RecyclerView.ViewHolder {

        ImageView save_photo;

        public MyVIewHolder(@NonNull View itemView) {

            super(itemView);
            save_photo = itemView.findViewById(R.id.photo);
        }
    }

}
