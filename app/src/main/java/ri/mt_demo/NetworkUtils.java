package ri.mt_demo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import java.util.List;

public class NetworkUtils {

    public static String getGatewayMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
            int gatewayIp = dhcpInfo.gateway;

            if (gatewayIp != 0) {
                String gatewayIpString = String.format(
                        "%d.%d.%d.%d",
                        (gatewayIp & 0xff),
                        (gatewayIp >> 8 & 0xff),
                        (gatewayIp >> 16 & 0xff),
                        (gatewayIp >> 24 & 0xff)
                );

                return getMacAddress(gatewayIpString);
            }
        }
        return "00:00:00:00:00:00";
    }

    private static String getMacAddress(String ipAddress) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" +");
                if (parts.length >= 4 && parts[0].equals(ipAddress)) {
                    return parts[3];
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "00:00:00:00:00:00";
    }
}