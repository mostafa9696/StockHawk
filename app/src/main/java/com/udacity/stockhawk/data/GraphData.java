package com.udacity.stockhawk.data;

/**
 * Created by Mostafa on 4/5/2017.
 */

public class GraphData {
    private Double price;
    private Double Xaxis;

    public GraphData(Double price, Double xaxis) {
        this.price = price;
        Xaxis = xaxis;
    }

    public Double getPrice() {
        return price;
    }

    public Double getXaxis() {
        return Xaxis;
    }
}
