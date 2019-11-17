package com.example.sewing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class FragSavedInsta extends Fragment {

    private TextInputLayout textInputLayout_Insta_id;
    private TextInputEditText Edit_Insert_Instaid;
    private Button btnPlus;
    private View view;
    private RecyclerView insta_recyclerview;
    private List<Item_insta> item_instas;

    public static FragSavedInsta newinstance() {
        FragSavedInsta fragSavedInsta = new FragSavedInsta();
        return fragSavedInsta;
    }
    
    //frag_saved_insta랑 연동
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_saved_insta, container, false);

        insta_recyclerview = view.findViewById(R.id.insta_recyclerview);
        final InstaRecyclerViewAdapter instaRecyclerViewAdapter = new InstaRecyclerViewAdapter(getContext(), item_instas);
        insta_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        insta_recyclerview.setAdapter(instaRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        item_instas = new ArrayList<>();

        item_instas.add(new Item_insta("신세경", "30,000", R.drawable.tprud));
    }
}
