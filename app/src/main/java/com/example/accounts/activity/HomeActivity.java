package com.example.accounts.activity;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.accounts.R;
import com.example.accounts.fragment.AddFragment;
import com.example.accounts.fragment.LookFragment;
import com.example.accounts.util.SPUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {



    private LinearLayout contentContainer;
    private BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String name = (String) SPUtils.get(getApplicationContext(),"name","");
        Bundle bundle = new Bundle();
        bundle.putSerializable("name",name);  //被传递的对象一定要实现Serializable接口
        final LookFragment lookFragment = new LookFragment();
        lookFragment.setArguments(bundle);
        bottomBar = findViewById(R.id.bottomBar);
        contentContainer = findViewById(R.id.contentContainer);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Object ob=null;
                switch (tabId) {
                    case R.id.home:
                        ob  =  lookFragment;
                        break;
                    case R.id.baobiao:
                        ob  = new AddFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,(Fragment) ob).commit();
            }
        });

    }
}