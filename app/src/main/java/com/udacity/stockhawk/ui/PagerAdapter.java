package com.udacity.stockhawk.ui;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.udacity.stockhawk.R;

/**
 * Created by Mostafa on 4/5/2017.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private String[] Titels;
    private final Fragment fragment[]=new Fragment[2];
    Context context;
    public PagerAdapter(FragmentManager fm,String H,String P,String Percentage,String Ab,String S,Context con) {
        super(fm);
        Titels= new String[]{con.getResources().getString(R.string.stock_graph), con.getResources().getString(R.string.stock_details)};
        Bundle bundle=new Bundle();
        bundle.putString("history",H);
        bundle.putString("price",P);
        bundle.putString("Percentage",Percentage);
        bundle.putString("Absolute",Ab);
        bundle.putString("price",P);
        bundle.putString("stock",S);
        fragment[0]=new StockGraphFragment();
        fragment[0].setArguments(bundle);
        fragment[1]=new DetailsFragment();
        fragment[1].setArguments(bundle);
    }
    @Override
    public Fragment getItem(int position) {
        return fragment[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titels[position];
    }

    @Override
    public int getCount() {
        return fragment.length;
    }
}
