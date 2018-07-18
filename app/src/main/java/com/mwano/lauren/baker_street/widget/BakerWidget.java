package com.mwano.lauren.baker_street.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import com.mwano.lauren.baker_street.R;

import static android.content.Context.MODE_PRIVATE;
import static com.mwano.lauren.baker_street.ui.master.MasterRecipePagerActivity.RECIPE_NAME;
import static com.mwano.lauren.baker_street.ui.master.MasterRecipePagerActivity.SHARED_PREFS;


/**
 * Implementation of App Widget functionality.
 */
public class BakerWidget extends AppWidgetProvider {

    private static final String TAG = BakerWidget.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences mPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        String mRecipeName = mPreferences.getString(RECIPE_NAME, "Nutella Pie");
        Log.d(TAG, "Received recipe name = " + mRecipeName);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baker_widget);
        // Set recipe name
        views.setTextViewText(R.id.widget_recipe_name, mRecipeName);
        // Set adapter for ingredients
        Intent serviceIntent = new Intent(context, BakerWidgetService.class);
        views.setRemoteAdapter(R.id.widget_listview,serviceIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);


        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_recipe_name);
        Log.d(TAG, "Notify changed Name");
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_listview);
        Log.d(TAG, "Notify list changed");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            Log.d(TAG, "Widget updated");
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
}

