package com.udacity.stockhawk.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.stockhawk.R;

public class DetailsFragment extends Fragment {
    TextView price,absolute,percentage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_details, container, false);
        price=(TextView)view.findViewById(R.id.price);
        absolute=(TextView)view.findViewById(R.id.absolute);
        percentage=(TextView)view.findViewById(R.id.percentage);
        Bundle bundle=getArguments();
        price.setText("Price : "+bundle.getString("price"));
        absolute.setText("Absolute Change : "+bundle.getString("Absolute"));
        percentage.setText("Percentage Change : "+bundle.getString("Percentage")+" %");
        return view;
    }

}
