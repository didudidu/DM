package ri.mt_demo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.richinfo.mthttp.MTSDK

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        MTSDK.getInstance().businessReport()
        Log.e("MTSDK", "businessReport")
        return super.onStartCommand(intent, flags, startId)
    }


}