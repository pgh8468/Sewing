package com.example.sewing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragSavedShop extends Fragment {

    private View view;
    private RecyclerView shop_recyclerview;
    private List<Item_shop> item_shops;

    public static FragSavedShop newinstance() {
        FragSavedShop fragSavedShop = new FragSavedShop();
        return fragSavedShop;
    }

    //frag_saved_shop이랑 연동
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_saved_shopping, container, false);

        shop_recyclerview = view.findViewById(R.id.shop_recyclerview);
        ShopRecyclerViewAdapter shopRecyclerViewAdapter = new ShopRecyclerViewAdapter(getContext(), item_shops);
        shop_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        shop_recyclerview.setAdapter(shopRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        item_shops = new ArrayList<>();
        item_shops.add(new Item_shop(R.drawable.shopping, "무신사"));
    }
}
