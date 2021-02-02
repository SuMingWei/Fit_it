package com.example.fitit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class NewHome extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        prepareViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void prepareViewPager(ViewPager viewPager) {
        // initialize main adapter
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager());
        // initialize fragment
        PetFragment petFragment = new PetFragment();
        ExerciseFragment exerciseFragment = new ExerciseFragment();
        ReportFragment reportFragment = new ReportFragment();

        adapter.addFragment(petFragment,"寵物");
        adapter.addFragment(exerciseFragment,"運動");
        adapter.addFragment(reportFragment,"週報");

        viewPager.setAdapter(adapter);
    }

    private class MainAdapter extends FragmentPagerAdapter {
        // initialize array list
        ArrayList<String> titleList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();

        // create constructor
        public void addFragment(Fragment fragment,String title){
            // add title
            titleList.add(title);
            // add fragment
            fragmentList.add(fragment);
        }

        public MainAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            // return fragment position
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            // return fragment list size
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            // return array list position
            return titleList.get(position);
        }
    }
}