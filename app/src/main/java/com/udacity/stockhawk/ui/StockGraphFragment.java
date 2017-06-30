package com.udacity.stockhawk.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.GraphData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StockGraphFragment extends Fragment {
    String PHistory,PSymbol;
    String []Histories;
    GraphView graph;
    LineGraphSeries<DataPoint> lines;
    ArrayList<GraphData> graphData;
    int HighY;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_stock_graph, container, false);
        Bundle bundle=getArguments();
        PHistory=bundle.getString("history");
        PSymbol=bundle.getString("stock");
        graph=(GraphView)view.findViewById(R.id.StockGraph);
        graph.setTitle(PSymbol+" Stock ");
        Log.d("StockHistory",PHistory);
        Histories=PHistory.split("\\r?\\n");
        lines=new LineGraphSeries<DataPoint>();
        DrawGraph();
        return view;
    }
    public void DrawGraph()
    {
        graphData=new ArrayList<>();
        GraphData Point;
        String [] XY;
        DataPoint dataPoint;
        Double X,Y;
        Log.d("e11", "e1");
        for(int i=0 ; i<Histories.length ; i++)
        {
            XY=Histories[i].split(", ");
            Point=new GraphData(Double.valueOf(XY[1])  // price
                    ,Double.valueOf(XY[0])/100000000);
            graphData.add(Point);
        }
        Log.d("e11", "e2");
        Collections.sort(graphData, new Comparator<GraphData>() {
            @Override
            public int compare(GraphData graphData, GraphData t1) {
                return graphData.getXaxis().compareTo(t1.getXaxis());
            }
        });
        FindHigh();
        for(int i=0 ; i<graphData.size() ; i++)
        {
            Point= graphData.get(i);
            X=Point.getXaxis();
            Y=Point.getPrice();
            Log.d("e33", String.valueOf(X) + ",," + String.valueOf(Y));
            dataPoint=new DataPoint(X,Y);
            lines.appendData(dataPoint,true,HighY,false);
        }
        lines.setThickness(7);
        lines.setDrawBackground(true);
        lines.setBackgroundColor(getResources().getColor(R.color.graphBG));
        graph.addSeries(lines);
    }
    public void FindHigh()
    {
        int ch=10;
        ArrayList <Double>_Y=new ArrayList<>();
        Double price;
        Log.d("e11", "e3");
        for(int i=0 ; i<graphData.size() ; i++)
        {
            price=graphData.get(i).getPrice();
            _Y.add(price);
        }
        Log.d("e11", "e4");
        HighY= (int)Math.round(Collections.max(_Y));
        if(HighY>=1000)
            ch=1000;
        else if(HighY>=100)
            ch=100;
        if(HighY%ch!=0)
            HighY+=(ch-(HighY%ch));
        Log.d("e11", String.valueOf(HighY));
    }
}
