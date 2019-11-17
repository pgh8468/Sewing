package com.example.sewing;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class InstaRecyclerViewAdapter extends RecyclerView.Adapter<InstaRecyclerViewAdapter.InstaViewHolder> {

    Context context;
    List<Item_insta> insta_data; //item_insta의 아이템을 가르킴

    public InstaRecyclerViewAdapter(Context context, List<Item_insta> insta_data) {
        this.context = context;
        this.insta_data = insta_data;
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
    public void onBindViewHolder(@NonNull InstaViewHolder holder, final int position) {

        holder.save_id.setText(insta_data.get(position).getId());
        holder.save_follow.setText(insta_data.get(position).getFollow());
        holder.save_profile.setImageResource(insta_data.get(position).getProfile());

        //원하는 ID 클릭 시 인스타그램에서 사진 가져오도록 함
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FragSavedPhoto.class);

                Toast.makeText(context, "사진 가져오기 제발 ㅠㅠ"+position, Toast.LENGTH_SHORT).show();
            }
        });

        //ID 삭제
        holder.delete_Id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insta_data.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, insta_data.size());

                Toast.makeText(context, "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //검색 구현
    @Override
    public int getItemCount() {
        return insta_data.size();
    }

    public static class InstaViewHolder extends RecyclerView.ViewHolder {

        private ImageView save_profile;
        private TextView save_id;
        private TextView save_follow;
        private ImageButton delete_Id;

        public InstaViewHolder(@NonNull View itemView) {
            super(itemView);

            save_profile = itemView.findViewById(R.id.Img_profile);
            save_id = itemView.findViewById(R.id.Insta_id);
            save_follow = itemView.findViewById(R.id.Insta_follow);
            delete_Id = itemView.findViewById(R.id.Del_insta_btn);
        }
    }
}
