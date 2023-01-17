package bjrd.rabbitnotes
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.io.File


class NoteWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // Set up each widget
        for (appWidgetId in appWidgetIds) {
            // Create text file for widget
            createTextFile(context, appWidgetId)

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.note_widget)

            // Set up the intent to open the EditNote activity when the widget is clicked
            val intent = Intent(context, EditNote::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            val pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_IMMUTABLE)
            views.setOnClickPendingIntent(R.id.text_box, pendingIntent)

            // Update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    private fun createTextFile(context: Context, appWidgetId: Int) {
        val file = File(context.filesDir, "widget_text_$appWidgetId")
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // Delete the text file for each deleted widget
        for (appWidgetId in appWidgetIds) {
            val file = File(context.filesDir, "widget_text_$appWidgetId")
            if (file.exists()) {
                file.delete()
            }
        }
    }
}