package com.udacity.stockhawk.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.sync.QuoteSyncJob;
import com.udacity.stockhawk.ui.MainActivity;

import timber.log.Timber;

/**
 * Created by Mostafa on 4/7/2017.
 */

public class StockWidgetProvider extends AppWidgetProvider {
    RemoteViews remoteViews;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(QuoteSyncJob.ACTION_WIDGET_UPDATED.equals(intent.getAction())){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] IDs = appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, StockWidgetProvider.class));
            appWidgetManager.notifyAppWidgetViewDataChanged(IDs, R.id.WidgetStocks_List);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int WidID : appWidgetIds) {
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.home_widget);
            Intent intent=new Intent(context, MainActivity.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);
            remoteViews.setOnClickPendingIntent(R.id.StockWidget,pendingIntent);
            Intent StockService=new Intent(context,ListServiceWidget.class);
            remoteViews.setRemoteAdapter(R.id.WidgetStocks_List,StockService);
            remoteViews.setInt(R.id.WidgetStocks_List, "setBackgroundResource", R.color.material_grey_850);
            appWidgetManager.updateAppWidget(WidID,remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}
