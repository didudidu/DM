/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.meiai.aidl;

import android.content.ComponentName;

/**
 *  {@hide}
 */
interface IgetMessage {
    /**
     * @method getImei1()
	 * @description 卡1 终端唯一标识
	 * @return String imei1 = "119018821000001";（后6位从000001~999999自然递增）
     */
    String getImei1();
    /**
     * @method getImei2()
     * @description 卡2 终端唯一标识
     * @return String imei2 = "None";固定返回值为None
     */
    String getImei2();
    /**
     * @method getImsi1()
     * @description 卡1 imsi
     * @return String imsi = "None";固定返回值为None
     */
    String getImsi1();
    /**
     * @method getImsi2()
     * @description 卡2 imsi
     * @return String imsi2 = "None";固定返回值为None
     */
    String getImsi2();
    /**
     * @method getAppKey()
     * @description 厂商标识
     * @return String appKey = "M100000052";此为固定值  
     */
    String getAppKey();
    /**
     * @method getWifiMacAddress()
     * @description mac地址
     * @return String mac = "6c:d1:99:69:97:b5";
     */
    String getWifiMacAddress();
    /**
     * @method getInternalStorage()
     * @description rom信息
     * @return String rom = "8GB";
     */
    String getInternalStorage();
    /**
     * @method getRunningMemory()
     * @description ram信息
     * @return String ram = "1GB";
     */
    String getRunningMemory();
    /**
     * @method getCPUType() 
     * @description cpu信息
     * @return String cpu = "MT6896Z";
     */
    String getCPUType();
    /**
     * @method getAndroidVersion()
     * @description 操作系统版本号
     * @return String sysVersion = Build.VERSION.SDK_INT + "";
     */
    String getAndroidVersion();
    /**
     * @method getVolte()
     * @description volte开关状态 0:开，1:关，-1:未知
     * @return String volte = "None";固定返回值为None
     */ 
    String getVolte();
    /**
     * @method getNetType()
     * @description 当前网络类型  WI-FI（4G、5G等）
     * @return String netType = "WI-FI";固定返回值为WI-FI
     */
    String getNetType();
    /**
     * @method getPhoneNumber()
     * @description 手机号
     * @return String phoneNumber = "None";固定返回值为None
     */
    String getPhoneNumber();
    /**
     * @method getRouterMac()
     * @description 路由Mac地址
     * @return String routerMac = "None";固定返回值为None
     */
    String getRouterMac();
    /**
     * @method getSerial()
     * @description 设备唯一标识
     * @return String sn = "GGBN1K300245";出货的板卡要根据我们提供的规则写入SN，返回此SN值
     */
    String getSerial();
    /**
     * @method getBrand()
     * @description 品牌
     * @return String brand = "yunshixun"; 固定返回值为yunshixun
     */
    String getBrand();
    /**
     * @method getDeviceModle()
     * @description 型号
     * @return String model = "云视讯B32CM02";
     */
    String getDeviceModle();
    /**
     * @method getGpu()
     * @description GPU信息
     * @return String gpu = "Adreno 200";
     */
    String getGpu();
    /**
     * @method getBoard()
     * @description 主板信息
     * @return String board = Build.BOARD;此返回值不能体现任何厂家信息
     */
    String getBoard();
    /**
     * @method getResolution()
     * @description 屏幕分辨率
     * @return String resolution = "1920*1080";
     */
    String getResolution();
    /**
     * @method getTemplateId()
     * @description 模板id
     * String templateId = "KW000002";固定返回值为KW000002
     */
    String getTemplateId();
    /**
     * @method getBluetoothMac()
     * @description 蓝牙Mac地址
     * @return String bluetoothMac = "D0:DA:D7:F1:2E:28";
     */
    String getBluetoothMac();
    /**
     * @method getSoftwareVer()
     * @description 固件版本号
     * @return String softwareVer = "1";
     */
    String getSoftwareVer();
    /**
     * @method getSoftwareName()
     * @description softwareName
     * @return String softwareName = "V2238B";
     */
    String getSoftwareName();
    /**
     * @method getBatteryCapacity()
     * @description 电池容量
     * @return String batteryCapacity = "10000mAh";
     */
    String getBatteryCapacity();
    /**
     * @method getScreenSize()
     * @description 屏幕尺寸
     * @return String screenSize = "32寸";
     */
    String getScreenSize();
    /**
     * @method getNetworkStatus()
     * @description 网络状态 1:连接，0:未连接
     * @return String networkStatus = "1";
     */
    String getNetworkStatus();
    /**
     * @method getWearingStatus()
     * @description 佩戴状态 1:佩戴，0:未佩戴
     * @return String wearingStatus = "None";固定返回值为None
     */
    String getWearingStatus();
    /**
     * @method getAppInfo()
     * @description App信息
     * @return String appInfo = "unknown|unknown|unknown|unknown\r\n";这部分照着写就可以
     */
    String getAppInfo();
}
