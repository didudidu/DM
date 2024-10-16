package com.meiai;

import static android.content.Context.BIND_AUTO_CREATE;

import com.meiai.aidl.IgetMessage;
import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MyManager {
    private static final String TAG = "MeiAi_MyManager";
    private static MyManager myManager;
    private Context mContext;
    private ServiceConnectedInterface serviceConnectedInterface;
    private IgetMessage igetMessage;
    private ServiceConnection serviceConnection;

    private final String ApiVersion = "V1.5-20240319";

    private MyManager(final Context context) {
        mContext = context;
        serviceConnection = (ServiceConnection)new ServiceConnection() {
            public void onServiceConnected(final ComponentName componentName, final IBinder iBinder) {
                igetMessage = IgetMessage.Stub.asInterface(iBinder);
                if (serviceConnectedInterface != null) {
                    serviceConnectedInterface.onConnect();
                }
            }

            public void onServiceDisconnected(final ComponentName componentName) {
                igetMessage = null;
            }
        };
    }

    public static synchronized MyManager getInstance(final Context context) {
        if (myManager == null) {
            myManager = new MyManager(context);
        }
        return myManager;
    }

    public void bindAIDLService(final Context context) {
        final Intent intent = new Intent();
        intent.setAction(Constant.MEIAI_RECEIVER_SERVICE_ACTION);
        intent.setComponent(new ComponentName(Constant.MEIAI_RECEIVER_PACKAGE_NAME, Constant.MEIAI_RECEIVER_SERVICE_NAME));
        context.bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    public void unBindAIDLService(final Context context) {
        context.unbindService(serviceConnection);
    }

    public void setConnectClickInterface(final ServiceConnectedInterface ConnectedInterface) {
        serviceConnectedInterface = ConnectedInterface;
    }

    public interface ServiceConnectedInterface {
        void onConnect();
    }

    /**
     * @method getImei1()
     * @description 卡1 终端唯一标识
     * @return String imei1 = "119018821000001";（后6位从000001~999999自然递增）
     */
    public String getImei1(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getImei1();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getImei2()
     * @description 卡2 终端唯一标识
     * @return String imei2 = "None";固定返回值为None
     */
    public String getImei2(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getImei2();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getImsi1()
     * @description 卡1 imsi
     * @return String imsi = "None";固定返回值为None
     */
    public String getImsi1(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getImsi1();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getImsi2()
     * @description 卡2 imsi
     * @return String imsi2 = "None";固定返回值为None
     */
    public String getImsi2(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getImsi2();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getAppKey()
     * @description 厂商标识
     * @return String appKey = "M100000052";此为固定值  
     */
    public String getAppKey(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getAppKey();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getWifiMacAddress()
     * @description mac地址
     * @return String mac = "6c:d1:99:69:97:b5";
     */
    public String getWifiMacAddress(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getWifiMacAddress();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getInternalStorage()
     * @description rom信息
     * @return String rom = "8GB";
     */
    public String getInternalStorage(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getInternalStorage();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getRunningMemory()
     * @description ram信息
     * @return String ram = "1GB";
     */
    public String getRunningMemory(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getRunningMemory();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getCPUType() 
     * @description cpu信息
     * @return String cpu = "MT6896Z";
     */
    public String getCPUType(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getCPUType();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getAndroidVersion()
     * @description 操作系统版本号
     * @return String sysVersion = Build.VERSION.SDK_INT + "";
     */
    public String getAndroidVersion(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getAndroidVersion();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getVolte()
     * @description volte开关状态 0:开，1:关，-1:未知
     * @return String volte = "None";固定返回值为None
     */ 
    public String getVolte(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getVolte();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getNetType()
     * @description 当前网络类型  WI-FI（4G、5G等）
     * @return String netType = "WI-FI";固定返回值为WI-FI
     */
    public String getNetType(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getNetType();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getPhoneNumber()
     * @description 手机号
     * @return String phoneNumber = "None";固定返回值为None
     */
    public String getPhoneNumber(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getPhoneNumber();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getRouterMac()
     * @description 路由Mac地址
     * @return String routerMac = "None";固定返回值为None
     */
    public String getRouterMac(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getRouterMac();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getSerial()
     * @description 设备唯一标识
     * @return String sn = "GGBN1K300245";出货的板卡要根据我们提供的规则写入SN，返回此SN值
     */
    public String getSerial(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getSerial();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getBrand()
     * @description 品牌
     * @return String brand = "yunshixun"; 固定返回值为yunshixun
     */
    public String getBrand(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getBrand();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getDeviceModle()
     * @description 型号
     * @return String model = "云视讯B32CM02";
     */
    public String getDeviceModle(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getDeviceModle();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getGpu()
     * @description GPU信息
     * @return String gpu = "Adreno 200";
     */
    public String getGpu(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getGpu();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getBoard()
     * @description 主板信息
     * @return String board = Build.BOARD;此返回值不能体现任何厂家信息
     */
    public String getBoard(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getBoard();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getResolution()
     * @description 屏幕分辨率
     * @return String resolution = "1920*1080";
     */
    public String getResolution(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getResolution();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getTemplateId()
     * @description 模板id
     * String templateId = "KW000002";固定返回值为KW000002
     */
    public String getTemplateId(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getTemplateId();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getBluetoothMac()
     * @description 蓝牙Mac地址
     * @return String bluetoothMac = "D0:DA:D7:F1:2E:28";
     */
    public String getBluetoothMac(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getBluetoothMac();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getSoftwareVer()
     * @description 固件版本号
     * @return String softwareVer = "1";
     */
    public String getSoftwareVer(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getSoftwareVer();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getSoftwareName()
     * @description softwareName
     * @return String softwareName = "V2238B";
     */
    public String getSoftwareName(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getSoftwareName();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getBatteryCapacity()
     * @description 电池容量
     * @return String batteryCapacity = "10000mAh";
     */
    public String getBatteryCapacity(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getBatteryCapacity();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getScreenSize()
     * @description 屏幕尺寸
     * @return String screenSize = "32寸";
     */
    public String getScreenSize(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getScreenSize();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getNetworkStatus()
     * @description 网络状态 1:连接，0:未连接
     * @return String networkStatus = "1";
     */
    public String getNetworkStatus(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getNetworkStatus();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getWearingStatus()
     * @description 佩戴状态 1:佩戴，0:未佩戴
     * @return String wearingStatus = "None";固定返回值为None
     */
    public String getWearingStatus(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getWearingStatus();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * @method getAppInfo()
     * @description App信息
     * @return String appInfo = "unknown|unknown|unknown|unknown\r\n";这部分照着写就可以
     */
    public String getAppInfo(){
        String result = null;
        if (igetMessage != null) {
            try {
                result = igetMessage.getAppInfo();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    

}
