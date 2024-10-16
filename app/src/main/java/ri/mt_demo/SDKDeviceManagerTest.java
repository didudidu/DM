package ri.mt_demo;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.dmyk.android.mt.IDeviceForMt;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 写死的测试值
 */
public class SDKDeviceManagerTest implements IDeviceForMt {

    Context context;

    /**
     * 卡1 终端唯一标识
     */
    String imei1 = "861231252339886";
    /**
     * 卡2 终端唯一标识
     */
    String imei2 = "861231252339885";
    /**
     * 卡1 imsi
     */
    String imsi = "786312344356369";
    /**
     * 卡2 imsi
     */
    String imsi2 = "786312344356361";
    /**
     * 厂商标识
     */
    String appKey = "M0002341";
    /**
     * mac地址
     */
    String mac = "6c:d1:99:69:97:b5";
    /**
     * rom信息
     */
    String rom = "8GB";
    /**
     * ram信息
     */
    String ram = "1GB";
    /**
     * cpu信息
     */
    String cpu = "MT6896Z";
    /**
     * 操作系统版本号
     */
    String sysVersion = Build.VERSION.SDK_INT + "";
    /**
     * volte开关状态 0:开，1:关，-1:未知
     */
    String volte = "0";
    /**
     * 当前网络类型  WI-FI（4G、5G等）
     */
    String netType = "4G";
    /**
     * 手机号
     */
    String phoneNumber = "18111111111";
    /**
     * 路由Mac地址
     */
    String routerMac = "D0:DA:D7:EC:1B:C8";
    /**
     * 设备唯一标识
     */
    String sn = "GGBN1K300245";
    /**
     * 品牌
     */
    String brand = "xiaomi";
    /**
     * 型号
     */
    String model = "P30";
    /**
     * GPU信息
     */
    String gpu = "Adreno 200";
    /**
     * 主板信息
     */
    String board = Build.BOARD;
    /**
     * 屏幕分辨率
     */
    String resolution = "1920*1080";
    /**
     * 模板id
     */
    String templateId = "TY000025";
    /**
     * 蓝牙Mac地址
     */
    String bluetoothMac = "D0:DA:D7:F1:2E:28";
    /**
     * 固件版本号
     */
    String softwareVer = "1";
    /**
     * softwareName
     */
    String softwareName = "V2238B";
    /**
     * 电池容量
     */
    String batteryCapacity = "800mah";
    /**
     * 屏幕尺寸
     */
    String screenSize = "1.5英寸";
    /**
     * 网络状态 1:连接，0:未连接
     */
    String networkStatus = "1";
    /**
     * 佩戴状态 1:佩戴，0:未佩戴
     */
    String wearingStatus = "0";
    /**
     * App信息
     */
    String appInfo = "unknown|unknown|unknown|unknown\r\n";

    public void setContext(Context context) {
        this.context = context;
    }


    @Override
    public String getImei1() {
        return imei1;
    }

    @Override
    public String getImei2() {
        return imei2;
    }

    @Override
    public String getImsi() {
        return imsi;
    }

    @Override
    public String getImsi2() {
        return imsi2;
    }

    public void setImsi2(String imsi2) {
        this.imsi2 = imsi2;
    }

    @Override
    public String getAppKey() {
        return appKey;
    }

    @Override
    public String getMac() {
        return mac;
    }

    @Override
    public String getRom() {
        return rom;
    }

    @Override
    public String getRam() {
        return ram;
    }

    @Override
    public String getCpu() {
        return cpu;
    }

    @Override
    public String getSysVersion() {
        return sysVersion;
    }

    @Override
    public String getSoftwareVer() {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode + "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String getSoftwareName() {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName + "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String getVolte() {
        return volte;
    }

    @Override
    public String getNetType() {
        return netType;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getBluetoothMac() {
        return bluetoothMac;
    }

    @Override
    public String getRouterMac() {
        return routerMac;
    }

    @Override
    public String getSn() {
        return sn;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public String getGpu() {
        return gpu;
    }

    @Override
    public String getBoard() {
        return board;
    }

    @Override
    public String getResolution() {
        return resolution;
    }


    @Override
    public String getTemplateId() {
        return templateId;
    }

    @Override
    public String getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(String batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    @Override
    public String getScreenSize() {
        return screenSize;
    }

    @Override
    public String getNetworkStatus() {
        return networkStatus;
    }

    @Override
    public String getWearingStatus() {
        return wearingStatus;
    }

    //获取两次时间间隔内的  应用名称|应用包名|使用时长|启动次数  多个应用\r\n进行分割
    //默认值为 unknown|unknown|unknown|unknown\r\n
    //以下为示例
    @Override
    public String getAppInfo() {
        Map<String, UsageStats> map = getUsageStatsList(context);
        if (map != null && !map.isEmpty()) {
            StringBuffer stringBuffer = new StringBuffer();
            PackageManager packageManager = context.getPackageManager();
            boolean isException = false;
            for (UsageStats u : map.values()) {
                //包名
                String packageName = u.getPackageName();
                //应用名称
                String appName = "unknow";
                if (u != null) {
                    ApplicationInfo appi = null;
                    try {
                        appi = packageManager.getApplicationInfo(packageName, 0);
                    } catch (PackageManager.NameNotFoundException e) {
                        //MTLog.e(TAG , ");;
                        e.printStackTrace();
                    }
                    if (appi != null) {
                        String realAppName = appi.loadLabel(packageManager).toString();
                        appName = TextUtils.isEmpty(realAppName) ? packageName : realAppName;
                    }
                }
                //使用时长

                //使用次数
                int launchCount = 0;
                try {
                    Field field = u.getClass().getDeclaredField("mLaunchCount");
                    launchCount = field.getInt(u);
                } catch (Exception e) {
                    if (!isException) {
                        isException = true;
                        Log.d(getClass().getSimpleName(), "反射获取次数失败");
                    }
                }
                stringBuffer.append(appName).append("|").append(packageName).append("|").append(u.getTotalTimeInForeground()).append("|").append(launchCount).append("\r\n");
                //限制长度
                if (stringBuffer.toString().length() > 4000) {
                    break;
                }
            }
            Log.d(getClass().getSimpleName(), "获取到数据->" + stringBuffer.toString());
            appInfo = stringBuffer.toString();
        }
        return appInfo;
    }

    public Map<String, UsageStats> getUsageStatsList(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return null;
        }
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        long startTime = TestSPUtils.getLastAppInfoReportTime(context);
        long endTime = System.currentTimeMillis();
//        final List<UsageStats> queryUsageStats=usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);
        Map<String, UsageStats> stringUsageStatsMap = usm.queryAndAggregateUsageStats(startTime, endTime);
        Log.d(getClass().getSimpleName(), "获取到的应用数据：" + stringUsageStatsMap.size());
        TestSPUtils.setLastAppInfoReportTime(context, endTime);
        return stringUsageStatsMap;
    }

    public void setAppInfo(String appInfo) {
        this.appInfo = appInfo;
    }

    public void setBluetoothMac(String bluetoothMac) {
        this.bluetoothMac = bluetoothMac;
    }

    public void setSoftwareVer(String softwareVer) {
        this.softwareVer = softwareVer;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public void setNetworkStatus(String networkStatus) {
        this.networkStatus = networkStatus;
    }

    public void setWearingStatus(String wearingStatus) {
        this.wearingStatus = wearingStatus;
    }

    public void setImei1(String imei1) {
        this.imei1 = imei1;
    }

    public void setImei2(String imei2) {
        this.imei2 = imei2;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }


    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }

    public void setVolte(String volte) {
        this.volte = volte;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRouterMac(String routerMac) {
        this.routerMac = routerMac;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }


    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    @Override
    public String toString() {
        return "SDKDeviceManagerTest{" +
                "context=" + context +
                ", imei1='" + imei1 + '\'' +
                ", imei2='" + imei2 + '\'' +
                ", imsi='" + imsi + '\'' +
                ", imsi2='" + imsi2 + '\'' +
                ", appKey='" + appKey + '\'' +
                ", mac='" + mac + '\'' +
                ", rom='" + rom + '\'' +
                ", ram='" + ram + '\'' +
                ", cpu='" + cpu + '\'' +
                ", sysVersion='" + sysVersion + '\'' +
                ", volte='" + volte + '\'' +
                ", netType='" + netType + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", routerMac='" + routerMac + '\'' +
                ", sn='" + sn + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", gpu='" + gpu + '\'' +
                ", board='" + board + '\'' +
                ", resolution='" + resolution + '\'' +
                ", templateId='" + templateId + '\'' +
                ", bluetoothMac='" + bluetoothMac + '\'' +
                ", softwareVer='" + softwareVer + '\'' +
                ", softwareName='" + softwareName + '\'' +
                ", batteryCapacity='" + batteryCapacity + '\'' +
                ", screenSize='" + screenSize + '\'' +
                ", networkStatus='" + networkStatus + '\'' +
                ", wearingStatus='" + wearingStatus + '\'' +
                ", appInfo='" + appInfo + '\'' +
                '}';
    }
}
