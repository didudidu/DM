package ri.mt_demo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.richinfo.mthttp.service.MTService

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.e("BroadcastReceiver", "onReceive")
        Intent(context, MyService::class.java).let {
            context.startService(it)
        }
    }
}