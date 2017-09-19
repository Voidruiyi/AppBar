/**
 *
 */
package utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import application.BaseApplication;

/**
 * 常用单位转换的辅助类
 *
 * Created by ruiyi on 2016/9/19.
 */
public class DensityUtils {
    /**
     *
     */
    private DensityUtils() {
    }


    /**
     * dp转px
     *
     * @param dpVal
     * @return
     */
    public static int dip2px(float dpVal) {
        final float scale = getScreenDensity(BaseApplication.getContext());
        return (int) (dpVal * scale + 0.5);
    }

    /**
     * dp转px
     *
     * @param context
     * @return
     */
    public static int dip2px(Context context, float dpVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpVal * scale + 0.5);
    }

    /**
     * sp转px
     *
     * @param context
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources()
                .getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static int getScreenWidth() {
        return getScreenWidth(BaseApplication.getContext());
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight() {
        return getScreenHeight(BaseApplication.getContext());
    }

    /**
     * 获取系统屏幕高度
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * 获取Activity 屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight2(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取Activity 屏幕可视高度
     *
     * @return
     */
    public static int getScreenShowHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        return rect.bottom - rect.top;
    }

    /**
     * �õ��豸���ܶ�
     */
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * ���ܶ�ת��Ϊ����
     */
    public static int dp2px(Context context, float px) {
        final float scale = getScreenDensity(context);
        return (int) (px * scale + 0.5);
    }

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Activity activity) {
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            statusBarHeight = activity.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            statusBarHeight = frame.top;
        }
        return statusBarHeight;
    }

    public static void hideStatusBar(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);
    }

    public static void showStatusBar(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);
    }

    /**
     * 获取虚拟按键高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        try {
            Resources rs = context.getResources();
            int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
            if (id > 0 && checkDeviceHasNavigationBar(context)) {
                navigationBarHeight = rs.getDimensionPixelSize(id);
            }
        } catch (Exception e) {
        }
        return navigationBarHeight;
    }

    /**
     * 判断有没有虚拟按键
     *
     * @param context
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }

        return hasNavigationBar;

    }

    public static int getMeasuredHeight(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredHeight();
    }

    public static int getMeasuredWidth(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredWidth();
    }
}
