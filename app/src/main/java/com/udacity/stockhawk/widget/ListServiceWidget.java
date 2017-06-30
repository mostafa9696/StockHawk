package com.udacity.stockhawk.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.ui.MainActivity;
import com.udacity.stockhawk.ui.Stock_Details;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Mostafa on 4/6/2017.
 */

public class ListServiceWidget extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetViewFactory();
    }
    public class WidgetViewFactory implements RemoteViewsService.RemoteViewsFactory
    {
        Cursor WidgetData=null;

        @Override
        public void onCreate() {

        }
        @Override
        public void onDataSetChanged() {
            if (WidgetData != null) {
                WidgetData.close();
            }
            long Identify = Binder.clearCallingIdentity();
            WidgetData=getContentResolver().query(
              Contract.Quote.URI,Contract.Quote.QUOTE_COLUMNS,null,null,Contract.Quote.COLUMN_SYMBOL
            );
            Binder.restoreCallingIdentity(Identify);
        }

        @Override
        public void onDestroy() {
            if (WidgetData != null) {
                WidgetData.close();
                WidgetData=null;
            }
        }

        @Override
        public int getCount() {
            if(WidgetData==null)
                return 0;
            return WidgetData.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (WidgetData == null ||position == AdapterView.INVALID_POSITION|| !WidgetData.moveToPosition(position))
                return null;
            RemoteViews remoteViews=new RemoteViews(getPackageName(), R.layout.list_item_quote);
                String PHistory=WidgetData.getString(Contract.Quote.POSITION_HISTORY);
                String PSymbol=WidgetData.getString(Contract.Quote.POSITION_SYMBOL);
                Double p=WidgetData.getDouble(Contract.Quote.POSITION_PRICE),
                        Per= WidgetData.getDouble(Contract.Quote.POSITION_PERCENTAGE_CHANGE),
                        ab= WidgetData.getDouble(Contract.Quote.POSITION_ABSOLUTE_CHANGE);
                p=Math.round(p * 10000.0) / 10000.0;
                Per=Math.round(Per * 10000.0) / 10000.0;
                ab=Math.round(ab * 10000.0) / 10000.0;
                String Price=String.valueOf(p);
                String Absolute=String.valueOf(ab);
                String Percentage=String.valueOf(Per);

                if(ab > 0) {
                    remoteViews.setInt(R.id.change, "setBackgroundResource", R.drawable.percent_change_pill_red);
                }
                else {
                    remoteViews.setInt(R.id.change, "setBackgroundResource", R.drawable.percent_change_pill_green);
                }
            remoteViews.setInt(R.id.list_item, "setBackgroundResource", R.color.material_grey_850);
            remoteViews.setTextViewText(R.id.change, Percentage);
            remoteViews.setTextViewText(R.id.symbol, PSymbol);
            remoteViews.setTextViewText(R.id.price, Price);
                Intent DetailsIntent = new Intent(getApplicationContext(), Stock_Details.class);
                DetailsIntent.putExtra("history",PHistory);
                DetailsIntent.putExtra("symbol",PSymbol);
                DetailsIntent.putExtra("price",Price);
                DetailsIntent.putExtra("absolute",Absolute);
                DetailsIntent.putExtra("percentage",Percentage);

                remoteViews.setOnClickFillInIntent(R.id.list_itemQuote, DetailsIntent);
            return remoteViews;

        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position){
            return WidgetData.moveToPosition(position) ? WidgetData.getLong(Contract.Quote.POSITION_ID) : position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }


    }
}
