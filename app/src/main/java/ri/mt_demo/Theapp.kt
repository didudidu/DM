package ri.mt_demo

import android.Manifest
import android.app.ActivityManager
import android.app.ActivityManager.MemoryInfo
import android.app.Application
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.bluetooth.BluetoothAdapter
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import androidx.core.app.ActivityCompat
import com.dmyk.android.mt.IDeviceForMt
import com.meiai.aidl.IgetMessage
import com.richinfo.mthttp.HttpRequestAgent
import com.richinfo.mthttp.MTSDK
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import java.net.NetworkInterface
import java.util.Collections


class Theapp: Application() {


    override fun onCreate() {
        super.onCreate()
        Log.e("MTSDK", "onCreate")

        MTSDK.getInstance().init(this, MTSDK.MODEL_HTTP)
        MTSDK.getInstance().setUrl("https://b.fxltsbl.com/")
        //MTSDK.getInstance().setUrl("coap://b.fxltsbl.com:5683")

        MTSDK.setDebugMode(true)
        MTSDK.setHeartChanel(MTSDK.HEART_CHANNEL_JOBSERVER)
        MTSDK.getInstance().httpRequestAgent =
            HttpRequestAgent { protocol, url, params, callback ->
                val body: RequestBody = RequestBody.create("application/json".toMediaType(), params)

                val request: Request = Request.Builder().url(url).post(body).build()

                OkHttpClient.Builder().build().newCall(request)
                    .enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            callback.onFailure(e.message)
                        }

                        @Throws(IOException::class)
                        override fun onResponse(call: Call, response: Response) {
                            callback.onResponse(response.code, response.body.string())
                        }
                    })
            }

        MTSDK.getInstance().deviceManager = DeviceForMt(this)
        //MTSDK.getInstance().deviceManager = DeviceForGuimiji(this)

        MTSDK.getInstance().startClient()

    }

}