package me.sr1.lickitung;

import android.app.Application;
import android.content.Context;

/**
 * @author SR1
 */

public class LickitungApp extends Application {

    private static LickitungApp gApp;

    public static LickitungApp getApp() {
        return gApp;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        gApp = this;
    }
}
