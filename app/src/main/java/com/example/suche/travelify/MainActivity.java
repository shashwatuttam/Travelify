package com.example.suche.travelify;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.suche.travelify.Adapter.FragmentAdapter;


public class MainActivity extends AppCompatActivity {
    // Main Activity.

    private int type;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = getIntent();
        type = intent.getIntExtra("TYPE",0);


        // Set ViewPager.
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),this,type));

        // Set TabLayout.
        TabLayout tabLayout = (TabLayout) findViewById(R.id.panes);
        tabLayout.setupWithViewPager(viewPager);
    }
}
