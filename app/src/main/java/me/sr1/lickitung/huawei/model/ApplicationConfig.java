package me.sr1.lickitung.huawei.model;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 应用配置信息
 * @author SR1
 */

public class ApplicationConfig {

    /* package */ static ApplicationConfig get(String packageName, SharedPreferences sharedPreferences) {
        if (sharedPreferences.contains(packageName)) {
            String configSerial = sharedPreferences.getString(packageName, "");
            if (TextUtils.isEmpty(configSerial)) {
                return null;
            }

            ApplicationConfig config = createGson().fromJson(configSerial, ApplicationConfig.class);
            config.setComponent(sharedPreferences);

            return config;
        } else {
            return null;
        }
    }

    @Expose
    @SerializedName("PackageName")
    public final String mPackageName;

    @Expose
    @SerializedName("AppId")
    public final String mAppId;

    @Expose
    @SerializedName("AppSecret")
    public final String mAppSecret;

    @Expose
    @SerializedName("Token")
    private String mToken;

    @Expose
    @SerializedName("Expires")
    private long mExpires;

    private SharedPreferences mSharePreferences;

    public ApplicationConfig(String packageName, String appId, String appSecret) {
        mPackageName = packageName;
        mAppId = appId;
        mAppSecret = appSecret;
    }

    /* package */ void setComponent(SharedPreferences sharePreference) {
        mSharePreferences = sharePreference;
    }

    public synchronized String getToken() {
        return mToken;
    }

    public synchronized void updateToken(String token, long expiresPeriod) {
        long expiresTime = System.currentTimeMillis() + expiresPeriod - 5 * 60 * 1000;
        updateTokenNoCheck(token, expiresTime);
    }

    public synchronized boolean isTokenValid() {
        if (System.currentTimeMillis() < mExpires) {
            return !TextUtils.isEmpty(mToken);
        } else {
            clearToken();
            return false;
        }
    }

    private void clearToken() {
        if (TextUtils.isEmpty(mToken) && mExpires == 0) return;
        updateTokenNoCheck("", 0);
    }

    private void updateTokenNoCheck(String token, long expiresTime) {
        mToken = token;
        mExpires = expiresTime;

        mSharePreferences.edit()
                .putString(mPackageName, createGson().toJson(this))
                .apply();
    }

    private static Gson createGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }
}
