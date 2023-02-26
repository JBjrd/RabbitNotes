package bjrd.rabbitnotes
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService

class NoteRemoteViewsService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        Log.d("debug1", "onGetViewFactory")
        return NoteRemoteViewsFactory(this.applicationContext, intent)
    }

    class NoteRemoteViewsFactory(private val context: Context, intent: Intent) : RemoteViewsFactory {
        private val noteText = intent.getStringExtra("Text")

        override fun onCreate() {}

        override fun onDataSetChanged() {}

        override fun onDestroy() {}

        override fun getCount(): Int {
            return 1
        }

        override fun getViewAt(position: Int): RemoteViews {
            Log.d("debug1", "getViewAt")
            val views = RemoteViews(context.packageName, R.layout.note_widget)
            views.setTextViewText(R.id.note_text, noteText)

            return views
        }

        override fun getLoadingView(): RemoteViews? {
            return null
        }

        override fun getViewTypeCount(): Int {
            return 1
        }

        override fun getItemId(position: Int): Long {
            return 1
        }

        override fun hasStableIds(): Boolean {
            return true
        }
    }
}