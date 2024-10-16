package ri.mt_demo;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.richinfo.mthttp.Constant;
import com.richinfo.mthttp.HttpRequestAgent;
import com.richinfo.mthttp.HttpRequestCallback;
import com.richinfo.mthttp.MTSDK;
import com.richinfo.mthttp.utils.SPUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * @author Bingo
 */
public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    int MODEL;

    //日志列表，用于测试
    private LogView lv;
    //接受后台服务发送的日志广播，用于测试
    UIReceiver uiReceiver = new UIReceiver();

    public static final MediaType JSON = MediaType.get("application/json");
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MODEL = getIntent().getIntExtra(Constant.SERVER_MODEL, MTSDK.MODEL_LWM2M);

        //这里由于demo展示，所以在页面选择之后初始化与启动，正式需要放在Application里进行初始化，以保证程序的正常运行。
        MTSDK.getInstance().init(getApplication(), MODEL);
        //如需设置地址，请在startClient之前传入对应MODEL的协议地址
//        IOTSDK.getInstance().setUrl("http://103.208.14.194:39097/uat/multi");
        bindView();
        registerLogReceiver();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
        }
        hasPermissionToReadNetworkStats();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NotNull String s) {
//                MTLog.d(s);
            }
        });
        //如果无
        MTSDK.setHeartChanel(MTSDK.HEART_CHANNEL_JOBSERVER);
        if (MTSDK.getDebugMode()) {
            interceptor.level(HttpLoggingInterceptor.Level.BODY);
        }
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        MTSDK.getInstance().setHttpRequestAgent(new HttpRequestAgent() {
            @Override
            public void request(String protocol, String url, String params, HttpRequestCallback callback) {
                /**
                 * @param protocol 协议 post或get
                 * @param url      请求地址
                 * @param params   请求参数
                 * @param callback 接口回调
                 */
                RequestBody body = RequestBody.create(params, JSON);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                new OkHttpClient.Builder()
                        .build()
                        .newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        callback.onResponse(response.code(), response.body().string());
                    }
                });
            }
        });
    }

    void netinit() {
        MTSDK.getInstance().setHttpRequestAgent(new HttpRequestAgent() {
            @Override
            public void request(String protocol, String url, String params, HttpRequestCallback callback) {
                RequestBody body = RequestBody.create(params, MediaType.get("application/json"));
                Request request = new Request.Builder().url(url).post(body).build();
                new OkHttpClient.Builder().build().newCall(request)
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e){
                                callback.onFailure(e.getMessage());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                callback.onResponse(response.code(), response.body().string());
                            }
                        });
            }
        });
    }

    //检测是否有 读取使用情况权限
    private boolean hasPermissionToReadNetworkStats() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        final AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getPackageName());
        if (mode == AppOpsManager.MODE_ALLOWED) {
            return true;
        }

        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
        return false;
    }

    // 打开“有权查看使用情况的应用”页面
    private void requestReadNetworkStats() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void registerLogReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.BROADCAST_UI);
        registerReceiver(uiReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(uiReceiver);
        super.onDestroy();
    }

    private class UIReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            String log = intent.getStringExtra("log");
            setLog(log);
        }
    }

    private void bindView() {
        findViewById(R.id.main_config).setOnClickListener(this);
        findViewById(R.id.main_start).setOnClickListener(this);
        findViewById(R.id.main_kill).setOnClickListener(this);
        findViewById(R.id.main_business).setOnClickListener(this);
        lv = findViewById(R.id.lv);
        setLog("当前使用SDK为：" + (MODEL == MTSDK.MODEL_HTTP ? "http" : "LwM2M"));
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.main_config) {
            Intent intent = new Intent(MainActivity.this, ConfigActivity.class);
            intent.putExtra(Constant.SERVER_MODEL, MODEL);
            startActivityForResult(intent, 0);
        } else if (id == R.id.main_start) {
            MTSDK.setDebugMode(true);
            //注入配置
            String deviceInfoJson = SPUtils.getTestDeviceInfo();
            SDKDeviceManagerTest sdkDeviceManagerTest = new SDKDeviceManagerTest();
            if (!TextUtils.isEmpty(deviceInfoJson)) {
                sdkDeviceManagerTest = new Gson().fromJson(deviceInfoJson, SDKDeviceManagerTest.class);
            }
            sdkDeviceManagerTest.setContext(this);
            MTSDK.getInstance().setDeviceManager(sdkDeviceManagerTest);
            MTSDK.getInstance().startClient();
        } else if (id == R.id.main_kill) {
            MTSDK.getInstance().killClient();
        } else if (id == R.id.main_business) {
            MTSDK.getInstance().businessReport();
        }
    }

    //设置回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String deviceInfoJson;
        //注入配置
        deviceInfoJson = SPUtils.getTestDeviceInfo();
        SDKDeviceManagerTest sdkDeviceManagerTest = new SDKDeviceManagerTest();
        if (!TextUtils.isEmpty(deviceInfoJson)) {
            sdkDeviceManagerTest = new Gson().fromJson(deviceInfoJson, SDKDeviceManagerTest.class);
        }
        sdkDeviceManagerTest.setContext(this);
        MTSDK.getInstance().setDeviceManager(sdkDeviceManagerTest);
    }

    private void setLog(String msg) {
        if (lv != null) {
            lv.setLog(msg);
        }
    }

}

