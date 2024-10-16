package ri.mt_demo

import android.app.ActivityManager
import android.bluetooth.BluetoothAdapter
import android.content.ContentResolver
import android.content.Context
import android.graphics.Point
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import android.view.WindowManager
import com.dmyk.android.mt.IDeviceForMt
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.io.InputStreamReader
import java.text.DecimalFormat
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.sqrt


/* loaded from: classes.jar:com/roco/custom/RocoCustomApi.class */
class ImplDm(private val mContext: Context) {
    private val TAG = "RocoCustomApi"
    private val wifiManager = mContext.getSystemService("wifi") as WifiManager

    private val cr: ContentResolver = mContext.contentResolver

    val wearStatus: String
        get() = "None"

    val appInfo: String
        get() = "unknown|unknown|unknown|unknown\r\n"

    val isNetworkConnected: String
        get() {
            val connectivityManager =
                mContext.getSystemService("connectivity") as ConnectivityManager
            if (connectivityManager != null) {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    return "1"
                }
                return "0"
            }
            return "0"
        }

    val imei1: String?
        get() {
            val tm = mContext.getSystemService("phone") as TelephonyManager
            val imei = tm.deviceId
            return imei
        }

    val imei2: String
        get() = "None"

    val imsi1: String
        get() = "None"

    val imsi2: String
        get() = "None"

    val appKey: String
        get() = "M100000052"

    val volte: String
        get() = "None"

    val netType: String
        get() = "WI-FI"

    val phoneNumber: String
        get() = "None"

    val routerMac: String
        get() = "None"

    val totalRom: String
        get() {
            val dataDir = Environment.getDataDirectory()
            val stat = StatFs(dataDir.path)
            val blockSize = stat.blockSizeLong
            val totalBlocks = stat.blockCountLong
            val size = totalBlocks * blockSize
            val deviceRomMemoryMap = longArrayOf(
                (2 * 1073741824).toLong(),
                (4 * 1073741824).toLong(),
                (8 * 1073741824).toLong(),
                (16 * 1073741824).toLong(),
                (32 * 1073741824).toLong(),
                (64 * 1073741824).toLong(),
                (128 * 1073741824).toLong(),
                (256 * 1073741824).toLong(),
                (512 * 1073741824).toLong(),
                (1024 * 1073741824).toLong(),
                (2048 * 1073741824).toLong()
            )
            val displayRomSize = arrayOf(
                "2GB",
                "4GB",
                "8GB",
                "16GB",
                "32GB",
                "64GB",
                "128GB",
                "256GB",
                "512GB",
                "1024GB",
                "2048GB"
            )
            var i = 0
            while (i < deviceRomMemoryMap.size && size > deviceRomMemoryMap[i]) {
                if (i == deviceRomMemoryMap.size) {
                    i--
                }
                i++
            }
            return displayRomSize[i]
        }

    val cpu: String
        get() {
            var cpuModel = ""
            try {
                val process = Runtime.getRuntime().exec("cat /proc/cpuinfo")
                val inputStream = process.inputStream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                while (true) {
                    val line = bufferedReader.readLine() ?: break
                    if (line.contains("Hardware")) {
                        cpuModel = line.split(":".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()[1].trim { it <= ' ' }
                        break
                    }
                }
                bufferedReader.close()
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return cpuModel
        }

    val osVersion: String
        get() = Build.VERSION.SDK_INT.toString() + ""

    val wifiMacAddress: String
        get() {
            val wifiInfo = wifiManager.connectionInfo
            val macAddress = wifiInfo.macAddress
            return macAddress
        }

    val sN: String
        get() {
            val tm = mContext.getSystemService("phone") as TelephonyManager
            val sn = tm.simSerialNumber
            return sn
        }

    val gpu: String
        get() {
            val gpuInfo: String
            if (Build.VERSION.SDK_INT >= 21) {
                val activityManager = mContext.getSystemService("activity") as ActivityManager
                val configurationInfo = activityManager.deviceConfigurationInfo
                gpuInfo = configurationInfo.glEsVersion
            } else {
                gpuInfo = "N/A"
            }
            return gpuInfo
        }

    val brand: String
        get() = "yunshixun"

    val model: String
        get() = "云视讯B32CM02"

    val board: String
        get() = Build.BOARD

    val templateId: String
        get() = "KW000001"

    val resolution: String
        get() {
            val windowManager = mContext.getSystemService("window") as WindowManager
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getRealMetrics(displayMetrics)
            val screenWidth = displayMetrics.widthPixels
            val screenHeight = displayMetrics.heightPixels
            val resolution = screenWidth.toString() + "X" + screenHeight
            return resolution
        }

    val bluetoothMacAddress: String
        get() {
            val bt = BluetoothAdapter.getDefaultAdapter()
            val addr = bt.address
            return addr
        }

    val softwareName: String
        get() = Build.DISPLAY

    val batteryCapcity: String
        get() = "5000mah"

    val physicsScreenSize: String
        get() {
            val manager = mContext.getSystemService("window") as WindowManager
            val point = Point()
            manager.defaultDisplay.getRealSize(point)
            val dm = mContext.resources.displayMetrics
            val i = dm.densityDpi
            val x: Float = (point.x / dm.xdpi).pow(2.0f)
            val y: Float = (point.y / dm.ydpi).pow(2.0f)
            val screenInches = sqrt(x + y)
            val df = DecimalFormat("#.#")
            val formattedValue = df.format(screenInches)
            val result = formattedValue + "px"
            return result
        }

    companion object {
        val totalRam: String
            get() {
                var ramMemorySize: String? = null
                var totalRam = 0
                try {
                    val fileReader = FileReader("/proc/meminfo")
                    val br = BufferedReader(fileReader, 4096)
                    ramMemorySize =
                        br.readLine().split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()[1]
                    br.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                if (ramMemorySize != null) {
                    totalRam = ceil(ramMemorySize.toFloat() / 1048576.0f.toDouble()).toInt()
                }
                return totalRam.toString() + "GB"
            }
    }
}