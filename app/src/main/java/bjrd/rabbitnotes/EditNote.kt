package bjrd.rabbitnotes
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.net.Uri
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

        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        val file = File(this.filesDir, "widget_text_$appWidgetId")
        editText.setText(file.readText())
    }

    override fun onStop() {
        super.onStop()

        val noteText = editText.text.toString()
        val file = File(this.filesDir, "widget_text_$appWidgetId")
        file.writeText(noteText)

        // Set up remote adapter to put widget content into the row of list to allow scrolling
        val views = RemoteViews(this.packageName, R.layout.note_widget)
        val serviceIntent = Intent(this, NoteRemoteViewsService::class.java)
        serviceIntent.putExtra("NoteText", noteText)
        serviceIntent.data = Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME))
        views.setRemoteAdapter(R.id.holder_list_view, serviceIntent)

        val appWidgetManager = AppWidgetManager.getInstance(this)
        appWidgetManager.updateAppWidget(appWidgetId, views)
        finish()
    }
}