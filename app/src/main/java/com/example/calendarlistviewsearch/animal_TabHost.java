package com.example.calendarlistviewsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;

public class animal_TabHost extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_tab_host);
        Intent intent = getIntent();

        ImageButton imgbtn1 = findViewById(R.id.imgbtn1);

        imgbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent11 = new Intent(animal_TabHost.this,animal_DetailInfo.class);
                startActivity(intent11);
            }
        });

        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpecFood = tabHost.newTabSpec("FOOD").setIndicator("음식");
        tabSpecFood.setContent(R.id.tabFood);
        tabHost.addTab(tabSpecFood);

        TabHost.TabSpec tabSpecSignal = tabHost.newTabSpec("SIGNAL").setIndicator("행동분석");
        tabSpecSignal.setContent(R.id.tabSignal);
        tabHost.addTab(tabSpecSignal);

        TabHost.TabSpec tabSpecTip = tabHost.newTabSpec("TIP").setIndicator("팁");
        tabSpecTip.setContent(R.id.tabTip);
        tabHost.addTab(tabSpecTip);

        tabHost.setCurrentTab(0);
    }
}