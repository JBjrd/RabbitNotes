package bjrd.rabbitnotes
import android.appwidget.AppWidgetManager
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class EditNote : AppCompatActivity() {
    private lateinit var editText: EditText
    private var appWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_note)

        editText = findViewById(R.id.edit_text)

        // Get the app widget ID from the intent
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        // Load the text from the text file
        val file = File(this.filesDir, "widget_text_$appWidgetId")
        editText.setText(file.readText())
    }

    override fun onStop() {
        super.onStop()

        // Save the text to the text file
        val file = File(this.filesDir, "widget_text_$appWidgetId")
        file.writeText(editText.text.toString())

        // Update the widget with the new text
        val appWidgetManager = AppWidgetManager.getInstance(this)
        updateAppWidget(this, appWidgetManager, appWidgetId)
        // Exit activity
        finish()
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        // Read the text from the widget's file
        val file = File(this.filesDir, "widget_text_$appWidgetId")
        val text = if (file.exists()) file.readText() else ""

        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.note_widget)
        views.setTextViewText(R.id.note_text, text)

        // Update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}