package ri.mt_demo

import android.Manifest
import android.app.ActivityManager
import android.app.ActivityManager.MemoryInfo
import android.app.Application.ACTIVITY_SERVICE
import android.app.Application.CONNECTIVITY_SERVICE
import android.app.Application.USAGE_STATS_SERVICE
import android.app.Application.WIFI_SERVICE
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Im
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import com.dmyk.android.mt.IDeviceForMt
import com.fasterxml.jackson.databind.cfg.ContextAttributes.Impl
import com.roco.custom.RocoCustomApi
import java.net.NetworkInterface
import java.util.Collections
import ri.mt_demo.NetworkUtils;

class DeviceForMt(ctx: Context) : IDeviceForMt {


    var wifiManager: WifiManager
    var tsm: SubscriptionManager
    var ts: TelephonyManager
    var activityManager: ActivityManager
    var cm: ConnectivityManager
    var context: Context
    var bm: BatteryManager
    var impl: ImplDm
    var xtapi: RocoCustomApi

    var _imei: String? = null
    var _model = ""
    var _tid = ""

    init {
        this.context = ctx.applicationContext
        wifiManager = context.getSystemService(WIFI_SERVICE) as WifiManager
        tsm = context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
        ts = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        impl = ImplDm(context)
        xtapi = RocoCustomApi(context)


        _imei = "119018810000011"
        _model = "云视讯B22CM01"
        _tid = "TY000020"

//        _imei = "119018810000012"
//        _model = "云视讯B22CM01"
//        _tid = "TY000020"

    }



    override fun getImei1(): String {
        if (_imei != null) {
            return _imei!!
        }
        var s1: String? = ts.getImei(0)
        if (s1 == null) {
            Log.e("MTSDK", "imei:" + s1)
            s1 = ts.imei
        }
        if (s1 == null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                Log.e("MTSDK", "imei:" + s1)
                s1 = ts.primaryImei
            }
        }
        if (s1 == null) {
            Log.e("MTSDK", "imei:" + s1)
            s1 = impl.imei1
        }
        if (s1 == null) {
            Log.e("MTSDK", "imei:" + s1)
            s1 = xtapi.imei1
        }
        Log.e("MTSDK", "imei:" + s1)
        if (s1 == null) {
            return "None"
        }
        return s1
    }

    override fun getImei2(): String {
        return xtapi.imei2
        val s1 = ts.getImei(1)
        Log.e("MTSDK", "getImei2:" + s1)
        return "None"
//                return impl.imei2
    }

    override fun getImsi(): String {
        return xtapi.imsi1
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val info = tsm.getActiveSubscriptionInfoForSimSlotIndex(0)

            return "${info.subscriptionId}"
        }
        return "None"
//                return impl.imsi1
    }

    override fun getImsi2(): String {
        return xtapi.imsi2
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val info = tsm.getActiveSubscriptionInfoForSimSlotIndex(1)

            return "${info.subscriptionId}"
        }
        return "None"
//                return impl.imsi2
    }

    override fun getMac(): String {
        return xtapi.wifiMacAddress
//                return impl.wifiMacAddress
        return wifiManager.connectionInfo.macAddress
    }

    override fun getRom(): String {
        return xtapi.totalRom
        return impl.totalRom
//        val memoryInfo = MemoryInfo()
//        activityManager.getMemoryInfo(memoryInfo)
//        return "${memoryInfo.totalMem}"
    }

    override fun getRam(): String {
        return RocoCustomApi.getTotalRam()
        return ImplDm.totalRam
//        val memoryInfo = MemoryInfo()
//        activityManager.getMemoryInfo(memoryInfo)
//        return "${memoryInfo.availMem}"
    }

    override fun getCpu(): String {
        return xtapi.cpu
        return impl.cpu
//        return "${Build.HARDWARE}"
    }

    override fun getSysVersion(): String {
        return xtapi.osVersion
        return impl.osVersion
//        return "${Build.VERSION.SDK_INT}"
    }

    override fun getSoftwareVer(): String {
        return xtapi.softwareVer
        val pm = context.packageManager
        val pi = pm.getPackageInfo(context.packageName, 0)
        return "${pi.versionCode}"
    }

    override fun getSoftwareName(): String {
        return xtapi.softwareName
        return impl.softwareName
//        val pm = context.packageManager
//        val pi = pm.getPackageInfo(context.packageName, 0)
//        return "${pi.versionName}"
    }

    override fun getVolte(): String {
        return xtapi.volte
        return impl.volte
//        return "None"
    }

    override fun getNetType(): String {
        return xtapi.netType
        return impl.netType
//        cm.activeNetworkInfo?.let {
//            if (it.type == ConnectivityManager.TYPE_MOBILE) {
//                return "MOBILE"
//            } else if (it.type == ConnectivityManager.TYPE_WIFI) {
//                return "WI-FI"
//            }
//        }
//        return "WI-FI"
    }

    override fun getPhoneNumber(): String {
        return xtapi.phoneNumber
        return impl.phoneNumber
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.READ_PHONE_STATE
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            val info = tsm.getActiveSubscriptionInfoForSimSlotIndex(1)
//            return "${info.number}"
//        }
//        return "None"
    }

    override fun getAppKey(): String {
//                return impl.appKey
        return xtapi.appKey
        return "M100000052"
    }

    override fun getBluetoothMac(): String {
        return xtapi.bluetoothMacAddress
        return impl.bluetoothMacAddress
//        try {
//            return BluetoothAdapter.getDefaultAdapter().address
//        } catch (e: Exception) {}
//        return "None"
    }

    override fun getRouterMac(): String {
        var mac = NetworkUtils.getGatewayMacAddress(this.context);
        //var mac = NetworkUtils.getConnectedWifiMacAddress(this.context) ;
        if (mac != null)
        {
            Log.i("MTSDK", "routemac:" + mac)
            return mac;
        }
        else {
            Log.i("MTSDK", "routemac: null" )
            return "00:00:00:00:00:00"
        }
        //return NetworkUtils.getConnectedWifiMacAddress(this.context);
        return xtapi.routerMac
        return impl.routerMac
//        var Mac = "None"
//        try {
//            val all: List<NetworkInterface> =
//                Collections.list(NetworkInterface.getNetworkInterfaces())
//            for (nif in all) {
//                if (!nif.name.equals("wlan0", ignoreCase = true)) continue
//
//                val macBytes = nif.hardwareAddress ?: break
//                val res1 = StringBuilder()
//                for (b in macBytes) {
//                    res1.append(String.format("%02X:", b))
//                }
//
//                if (res1.length > 0) {
//                    res1.deleteCharAt(res1.length - 1)
//                    Mac = res1.toString()
//                }
//                return Mac!!
//            }
//        } catch (ex: java.lang.Exception) {
//        }
//        return Mac
    }

    override fun getSn(): String {
        return xtapi.sn
        return impl.sN
//        return "${Build.SERIAL}"
    }

    override fun getBrand(): String {
        return xtapi.brand
//                return impl.brand
//                return "${Build.BOARD}"
        return "yunshixun"
    }

    override fun getModel(): String {
//                return impl.model
//                return "${Build.MODEL}"
        return _model
    }

    override fun getGpu(): String {
        return xtapi.gpu
        return impl.gpu
//        return activityManager.deviceConfigurationInfo.glEsVersion
    }

    override fun getBoard(): String {
        return xtapi.board
        return impl.board
//        return "${Build.BOARD}"
    }

    override fun getResolution(): String {
        return xtapi.resolution
        return impl.resolution
//        context.resources.displayMetrics.apply {
//            return "${widthPixels}*${heightPixels}"
//        }
//        return "None"
    }

    override fun getTemplateId(): String {
//                return impl.templateId
        return _tid//"KW000002"
    }



    override fun getBatteryCapacity(): String {
        return xtapi.batteryCapcity
        return impl.batteryCapcity
//        val capacity = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
//        return "${capacity}"
    }

    override fun getScreenSize(): String {
        return xtapi.physicsScreenSize
        return impl.physicsScreenSize
//        context.resources.displayMetrics.apply {
//            return "${densityDpi}"
//        }
//        return "None"
    }

    override fun getNetworkStatus(): String {
        return xtapi.isNetworkConnected
        return impl.isNetworkConnected
//        cm.activeNetworkInfo?.let {
//            return "${it.state}"
//
//        }
//        return "None"
    }

    override fun getWearingStatus(): String {
        return xtapi.wearStatus
        return impl.wearStatus
//        return "None"
    }

    override fun getAppInfo(): String {
        return xtapi.appInfo
        return impl.appInfo
//        return "unknown|unknown|unknown|unknown\r\n"
//        var appInfo = ""
//        val map = getUsageStatsList(context)
//        if (map != null && !map.isEmpty()) {
//            val stringBuffer = StringBuffer()
//            val packageManager: PackageManager = context.getPackageManager()
//            var isException = false
//            for (u in map.values) {
//                //包名
//                val packageName = u.packageName
//                //应用名称
//                var appName: String? = "unknow"
//                if (u != null) {
//                    var appi: ApplicationInfo? = null
//                    try {
//                        appi = packageManager.getApplicationInfo(packageName, 0)
//                    } catch (e: PackageManager.NameNotFoundException) {
//                        //MTLog.e(TAG , ");;
//                        e.printStackTrace()
//                    }
//                    if (appi != null) {
//                        val realAppName = appi.loadLabel(packageManager).toString()
//                        appName =
//                            if (TextUtils.isEmpty(realAppName)) packageName else realAppName
//                    }
//                }
//
//                //使用时长
//
//                //使用次数
//                var launchCount = 0
//                try {
//                    val field = u.javaClass.getDeclaredField("mLaunchCount")
//                    launchCount = field.getInt(u)
//                } catch (e: Exception) {
//                    if (!isException) {
//                        isException = true
//                        Log.d(javaClass.simpleName, "反射获取次数失败")
//                    }
//                }
//                stringBuffer.append(appName).append("|").append(packageName).append("|")
//                    .append(u.totalTimeInForeground).append("|").append(launchCount)
//                    .append("\r\n")
//                //限制长度
//                if (stringBuffer.toString().length > 4000) {
//                    break
//                }
//            }
//            Log.d(javaClass.simpleName, "获取到数据->$stringBuffer")
//            appInfo = stringBuffer.toString()
//        }
//        return appInfo
    }

    fun getUsageStatsList(context: Context): Map<String, UsageStats>? {
        val usm = context.getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
        val startTime = TestSPUtils.getLastAppInfoReportTime(context)
        val endTime = System.currentTimeMillis()
        //        final List<UsageStats> queryUsageStats=usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);
        val stringUsageStatsMap = usm.queryAndAggregateUsageStats(startTime, endTime)
        Log.d(javaClass.simpleName, "获取到的应用数据：" + stringUsageStatsMap.size)
        TestSPUtils.setLastAppInfoReportTime(context, endTime)
        return stringUsageStatsMap
    }
}