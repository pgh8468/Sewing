package com.example.sewing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;

public class MainPage extends AppCompatActivity {

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    String logined_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE); //캡쳐 못하게 함

        Intent intent = getIntent();
        logined_id = intent.getExtras().getString("login_ID");

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Add Fragmnet
        viewPagerAdapter.AddFragment(new FragSavedInsta(logined_id,viewPager), "");
        viewPagerAdapter.AddFragment(new FragSavedPhoto(logined_id), "");
        viewPagerAdapter.AddFragment(new FragSavedShop(), "");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.star);
        tabLayout.getTabAt(1).setIcon(R.drawable.photo);
        tabLayout.getTabAt(2).setIcon(R.drawable.shopping);
    }



}
