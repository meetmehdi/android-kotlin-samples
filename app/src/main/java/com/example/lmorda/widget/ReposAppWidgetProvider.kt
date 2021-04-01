package com.example.lmorda.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.lmorda.R
import java.text.DateFormat
import java.util.*

class ReposAppWidgetProvider : AppWidgetProvider() {

    companion object {
        private const val SHARED_PREF_FILE = "com.example.lmorda.prefs"
        private const val COUNT_KEY = "count"
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        // Update shared pref count
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_FILE, 0)
        var updateCount = sharedPreferences.getInt(COUNT_KEY + appWidgetId, 0)
        updateCount++
        sharedPreferences.edit().putInt(COUNT_KEY + appWidgetId, updateCount).apply()

        // Update text values
        val timestamp = DateFormat.getTimeInstance(DateFormat.SHORT).format(Date())
        val widgetViews = RemoteViews(context.packageName, R.layout.app_widget)
        widgetViews.setTextViewText(R.id.tv_widget_id, appWidgetId.toString())
        widgetViews.setTextViewText(R.id.tv_widget_last_update_time,
            context.resources.getString(R.string.date_count_format, updateCount, timestamp))

        // Set the click behavior
        val onClickIntent = Intent(context, ReposAppWidgetProvider::class.java)
        onClickIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        onClickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(appWidgetId))
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
