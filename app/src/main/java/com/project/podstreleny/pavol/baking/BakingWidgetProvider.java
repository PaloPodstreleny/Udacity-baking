package com.project.podstreleny.pavol.baking;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.project.podstreleny.pavol.baking.ui.recipieMaster.RecipeDetailActivity;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

//        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        //int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);

        // Construct the RemoteViews object
        RemoteViews views = getGardenGridRemoteViews(context);
        appWidgetManager.updateAppWidget(appWidgetId,views);
    }

    private static RemoteViews getGardenGridRemoteViews(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.baking_widget_provider);

        Intent intent = new Intent(context,GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_gv,intent);

        //OnCLick
        Intent appIntent = new Intent(context, RecipeDetailActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context,0,appIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_gv,appPendingIntent);

        views.setEmptyView(R.id.widget_gv,R.id.empty_view);

        return views;

    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        onUpdateWidget(context,appWidgetManager,appWidgetIds);
    }


    public static void onUpdateWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetsIds){
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetsIds) {
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
}

