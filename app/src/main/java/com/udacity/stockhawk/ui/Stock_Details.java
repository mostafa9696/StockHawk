package com.udacity.stockhawk.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.data.GraphData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;

public class Stock_Details extends AppCompatActivity {
    String PHistory,PSymbol,PPrice,PAbsoluteChange,PPercentageChange;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock__details);
        Intent intent=getIntent();
        PHistory=intent.getStringExtra("history");  // draw at graph
        PSymbol=intent.getStringExtra("symbol");
        PPrice= intent.getStringExtra("price");
        PAbsoluteChange=intent.getStringExtra("absolute");
        PPercentageChange=intent.getStringExtra("percentage");
        setTitle(PSymbol);
        viewPager=(ViewPager)findViewById(R.id.PagerID);
        pagerAdapter=new PagerAdapter(getSupportFragmentManager(),PHistory,PPrice,PPercentageChange,PAbsoluteChange,PSymbol,getApplicationContext());

        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
    }
}
