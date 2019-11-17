package com.example.sewing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;

public class MainPage extends AppCompatActivity {

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Add Fragmnet
        viewPagerAdapter.AddFragment(new FragSavedInsta(), "");
        viewPagerAdapter.AddFragment(new FragSavedPhoto(), "");
        viewPagerAdapter.AddFragment(new FragSavedShop(), "");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.star);
        tabLayout.getTabAt(1).setIcon(R.drawable.photo);
        tabLayout.getTabAt(2).setIcon(R.drawable.shopping);
    }
}
