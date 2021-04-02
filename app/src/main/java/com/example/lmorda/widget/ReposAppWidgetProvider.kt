package com.example.lmorda.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetManager.EXTRA_CUSTOM_EXTRAS
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.lmorda.R
import com.example.lmorda.SORT_STARS
import com.example.lmorda.ServiceLocator
import com.example.lmorda.cache.RepoCache
import com.example.lmorda.data.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*

const val EXTRA_FORCE_REFRESH = "EXTRA_FORCE_REFRESH"

class ReposAppWidgetProvider : AppWidgetProvider() {

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val repos = ServiceLocator.provideReposRepository().getCachedRepos()

        // Update text values
        val timestamp = DateFormat.getTimeInstance(DateFormat.SHORT).format(Date())
        val widgetViews = RemoteViews(context.packageName, R.layout.app_widget)
        widgetViews.setTextViewText(R.id.tv_widget_id, appWidgetId.toString())
        widgetViews.setTextViewText(R.id.tv_widget_last_update_time,
            context.resources.getString(R.string.date_format, timestamp))
        widgetViews.setTextViewText(R.id.tv_widget_most_popular_repo_name, repos?.get(0)?.name)

        // Set the click behavior
        val onClickIntent = Intent(context, ReposAppWidgetProvider::class.java)
        onClickIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        onClickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(appWidgetId))
        onClickIntent.putExtra(EXTRA_FORCE_REFRESH, true)
        val onClickPendingIntent = PendingIntent.getBroadcast(
            context, appWidgetId, onClickIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        widgetViews.setOnClickPendingIntent(R.id.btn_update_widget, onClickPendingIntent)

        // Call the app widget manager's update app widget method
        appWidgetManager.updateAppWidget(appWidgetId, widgetViews)
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
}
