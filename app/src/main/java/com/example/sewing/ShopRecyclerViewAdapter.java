package com.example.sewing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShopRecyclerViewAdapter extends RecyclerView.Adapter<ShopRecyclerViewAdapter.ShopViewHolder>{

    Context shopcontext;
    List<Item_shop> shop_data;

    public ShopRecyclerViewAdapter(Context shopcontext, List<Item_shop> shop_data) {
        this.shopcontext = shopcontext;
        this.shop_data = shop_data;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(shopcontext).inflate(R.layout.item_shop, parent, false);
        ShopViewHolder shopholder = new ShopViewHolder(view);

        return shopholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {

        holder.save_img_shop.setImageResource(shop_data.get(position).getImg_shop());
        holder.save_url.setText(shop_data.get(position).getShop_url());

    }

    @Override
    public int getItemCount() {
        return shop_data.size();
    }

    public static class ShopViewHolder extends RecyclerView.ViewHolder {

        ImageView save_img_shop;
        TextView save_url;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);

            save_img_shop = itemView.findViewById(R.id.img_shop);
            save_url = itemView.findViewById(R.id.Shop_url);

        }
    }
}
