package com.habib.movie.widget;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.habib.movie.R;

public class UpdateWidget extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        ComponentName name = new ComponentName(this,ImageWidget.class);
        int[] ids = AppWidgetManager.getInstance(
                getApplicationContext()).
                getAppWidgetIds(new ComponentName(getApplicationContext(),ImageWidget.class)
        );

        Intent intent = new Intent(this, StackService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(getPackageName(), R.layout.image_widget);
        views.setRemoteAdapter(R.id.stack_view,intent);
        views.setEmptyView(R.id.stack_view,R.id.empty_view);
        manager.updateAppWidget(name,views);
        jobFinished(jobParameters,false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
