package me.sr1.lickitung.huawei.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.text.TextUtils;

import me.sr1.lickitung.base.Singleton;

/**
 * HuaweiAccessTokenManager
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

    private static String KEY_TOKEN = "KEY_TOKEN";

    private static String KEY_EXPIRES = "KEY_EXPIRES";

    private final SharedPreferences mSharedPreferences;

    private String mToken;

    private long mExpiresIn;

    private HuaweiAccessTokenManager(Context context) {
        mSharedPreferences =
                context.getSharedPreferences("HuaweiAccessTokenManager", Context.MODE_PRIVATE);
        mToken = mSharedPreferences.getString(KEY_TOKEN, null);
        mExpiresIn = mSharedPreferences.getLong(KEY_EXPIRES, 0);
    }

    public synchronized boolean isTokenValid() {
        return System.currentTimeMillis() < mExpiresIn && !TextUtils.isEmpty(mToken);
    }

    public synchronized String getToken() {
        return mToken;
    }

    public synchronized void updateToken(String token, long expireIn) {

        mToken = token;
        // 计算过期时间，过期时间比实际时间提前5分钟
        mExpiresIn = System.currentTimeMillis() + expireIn - 5 * 60 * 1000;

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_TOKEN, mToken);
        editor.putLong(KEY_EXPIRES, mExpiresIn);

        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

}
