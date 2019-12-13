package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;
import java.util.List;

public class SellerActivity extends AppCompatActivity {
    private ViewPager view_pager;
    private TabLayout tab_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        view_pager = findViewById(R.id.view_pager);
        tab_layout = findViewById(R.id.tab_layout);
        initComponent();
    }
    private void initComponent() {
        setupViewPager(view_pager);

        tab_layout.setupWithViewPager(view_pager);
    }
    private void setupViewPager(ViewPager viewPager) {
        SellerSectionPagerAdapter adapter = new SellerSectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SellerProductFragment(),"Home");
        adapter.addFragment(new HomeFragment(),"products");
        adapter.addFragment(new HomeFragment(),"More");

        viewPager.setAdapter(adapter);
    }
    private class SellerSectionPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SellerSectionPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFragment(Fragment fragment,String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);

        }
    }
}
