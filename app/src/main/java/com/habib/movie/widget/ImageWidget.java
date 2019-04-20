package com.habib.movie.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.habib.movie.R;

/**
 * Implementation of App Widget functionality.
 */
public class ImageWidget extends AppWidgetProvider {
    private static final String TOAST_ACTION = "com.habib.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.habib.EXTRA_ITEM";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, StackService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.image_widget);
        views.setRemoteAdapter(R.id.stack_view,intent);
        views.setEmptyView(R.id.stack_view,R.id.empty_view);

        Intent toast = new Intent(context,ImageWidget.class);
        toast.setAction(ImageWidget.TOAST_ACTION);
        toast.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,toast,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view,pendingIntent);

//        Intent in = new Intent(context,ImageWidget.class);
//        in.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//        int[] ids = AppWidgetManager.getInstance(context)
//                .getAppWidgetIds(new ComponentName(context,ImageWidget.class));
//        in.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
//        context.sendBroadcast(in);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null){
            if (intent.getAction().equals(TOAST_ACTION)){
                int index = intent.getIntExtra(EXTRA_ITEM,0);
                Toast.makeText(context," Touched view "+index,Toast.LENGTH_SHORT).show();
            }
        }
    }
}

