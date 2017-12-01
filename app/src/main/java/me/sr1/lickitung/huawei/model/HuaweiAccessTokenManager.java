package me.sr1.lickitung.huawei.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.WeakHashMap;

import me.sr1.lickitung.base.Singleton;

/**
 * 华为推送应用配置信息管理器
 * @author SR1
 */

public class HuaweiAccessTokenManager {

    private static Singleton<Context, HuaweiAccessTokenManager> sInstance =
            new Singleton<Context, HuaweiAccessTokenManager>() {
                @Override
                public HuaweiAccessTokenManager create(Context context) {
                    return new HuaweiAccessTokenManager(context);
                }
            };

    public static HuaweiAccessTokenManager get(Context context) {
        return sInstance.get(context);
    }

    private final SharedPreferences mSharedPreferences;

    private final WeakHashMap<String, ApplicationConfig> mApplicationConfigCache = new WeakHashMap<>();

    private HuaweiAccessTokenManager(Context context) {
        mSharedPreferences =
                context.getSharedPreferences("HuaweiAccessTokenManager", Context.MODE_PRIVATE);
    }

    public synchronized ApplicationConfig addApplicationConfig(String packageName, String appId, String appSecret) {
        ApplicationConfig applicationConfig = new ApplicationConfig(packageName, appId, appSecret);
        applicationConfig.setComponent(mSharedPreferences);
        applicationConfig.updateToken("", 0);
        return applicationConfig;
    }

    public synchronized ApplicationConfig getApplicationConfig(String packageName) {
        return obtain(packageName);
    }

    private ApplicationConfig obtain(String packageName) {

        if (mApplicationConfigCache.containsKey(packageName)) {
            return mApplicationConfigCache.get(packageName);
        } else {
            if (mSharedPreferences.contains(packageName)) {
                return ApplicationConfig.get(packageName, mSharedPreferences);
            } else {
                return null;
            }
        }
    }
}
