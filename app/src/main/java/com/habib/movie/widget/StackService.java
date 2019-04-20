package com.habib.movie.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class StackService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViews(this.getApplicationContext());
    }
}
