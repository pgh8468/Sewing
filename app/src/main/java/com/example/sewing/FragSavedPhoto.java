package com.example.sewing;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragSavedPhoto extends Fragment {

    private View view;
    private RecyclerView photo_recyclerview;
    private List<Item_Photo> item_photo = new ArrayList<>();

    public static FragSavedPhoto newinstance() {
        FragSavedPhoto fragSavedPhoto = new FragSavedPhoto();
        return fragSavedPhoto;
    }

    //frag_saved_photo랑 연동
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_saved_photo, container, false);

        photo_recyclerview = view.findViewById(R.id.photo_recyclerview);

        photo_recyclerview.setHasFixedSize(true);

        PhotoRecyclerViewAdapter photoRecyclerViewAdapter = new PhotoRecyclerViewAdapter(getContext(), item_photo);
        photo_recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        photo_recyclerview.setAdapter(photoRecyclerViewAdapter);

        return view;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        item_photo.add(new Item_Photo(R.drawable.tprud));
        item_photo.add(new Item_Photo(R.drawable.ab));
        item_photo.add(new Item_Photo(R.drawable.ac));
        item_photo.add(new Item_Photo(R.drawable.ad));
        item_photo.add(new Item_Photo(R.drawable.aw));

    }
}
