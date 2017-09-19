package application;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {

    private static BaseApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    /**
     * @return
     */
    public static Context getContext() {
        return mApplication.getApplicationContext();
    }
}