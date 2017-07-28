package com.caiyi.mycalendar.Utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.webkit.MimeTypeMap;

import com.caiyi.mycalendar.compant.MyApplication;

import java.io.File;

/**
 * @author Ren ZeQiang
 * @since 2017/7/26
 */
public class AppUtils {

    public AppUtils() {
    }

    /**
     * 封装App信息的Bean类
     */
    public static class AppInfo {
        private String name;
        private Drawable icon;
        private String packageName;
        private String packagePath;
        private String versionName;
        private int versionCode;

        public String getName() {
            return name;
        }

        public Drawable getIcon() {
            return icon;
        }

        public String getPackageName() {
            return packageName;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public String getVersionName() {
            return versionName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        private AppInfo() {
            this.name = "";
            this.icon = null;
            this.packageName = "";
            this.packagePath = "";
            this.versionName = "";
            this.versionCode = 0;
        }

        private AppInfo(String name, Drawable icon, String packageName, String packagePath,
                        String versionName, int versionCode) {
            this.name = name;
            this.icon = icon;
            this.packageName = packageName;
            this.packagePath = packagePath;
            this.versionName = versionName;
            this.versionCode = versionCode;
        }
    }

    /**
     * 获取app info包装类
     * @return
     */
    public static AppInfo getAppInfo() {
        PackageManager pm = MyApplication.getAppContext().getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(MyApplication.getAppContext().getPackageName(), 0);
            ApplicationInfo appInfo = packageInfo.applicationInfo;
            String name = appInfo.loadLabel(pm).toString();
            Drawable icon = appInfo.loadIcon(pm);
            return new AppInfo(name, icon, packageInfo.packageName, appInfo.sourceDir, packageInfo.versionName, packageInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
           return new AppInfo();
        }
    }

    /**
     * 根据VersionName检查更新.VersionName格式：1.3.0
     *
     * @param appVersion    安装版本
     * @param serverVersion 服务器版本
     * @return 是否更新
     */
    public static boolean checkAppUpdate(String appVersion, String serverVersion) {
        if (StringUtil.isNullOrEmpty(appVersion) || StringUtil.isNullOrEmpty(serverVersion)) {
            return false;
        }
        int appCode = NumberUtil.getInt(appVersion.trim().replaceAll("\\.", ""));
        int serverCode = NumberUtil.getInt(serverVersion.trim().replaceAll("\\.", ""));
        return appCode != -1 && serverCode != -1 && serverCode > appCode;
    }

    /**
     * 获取安装ApkIntent
     * @param file
     * @return
     */
    public static Intent getInstallAppIntent(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return null;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String type;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            type = "application/vnd.android.package-archive";
        } else {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension("aaa");
        }
        intent.setDataAndType(Uri.fromFile(file), type);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    /**
     * 安装apk（支持6.0）
     */
    public static void installApk(Context context, File file) {
        Intent intent = getInstallAppIntent(file);
        if (intent != null) {
            context.startActivity(intent);
        }
    }

    /**
     * 安装apk（支持6.0）
     */
    public static void installApk(Activity activity, File file, int requestCode) {
        Intent intent = getInstallAppIntent(file);
        if (intent != null) {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 使用浏览器更新app
     *
     * @param context       上下文
     * @param apkUpdateUrl  apk下载url
     */
    public static void updateWithIntent(Context context, String apkUpdateUrl) {
        if (context == null || StringUtil.isNullOrEmpty(apkUpdateUrl)) {
            return;
        }
        Uri uri = Uri.parse(apkUpdateUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开当前应用设置页面
     * @param context
     */
    public static void openAppSettings(Context context) {
        if (context == null) return;
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    /** 获取当前进程名称 */
    public static String getCurrentProgressName(Context context) {
        if (context == null) {return "";}
        int pid = Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : manager.getRunningAppProcesses()) {
            if (appProcessInfo.pid == pid) {
                return appProcessInfo.processName;
            }
        }
        return "";
    }

    public static Resources getResources() {
        return MyApplication.getAppContext().getResources();
    }

    public static DisplayMetrics getDisplayMetrics() {
        return getResources().getDisplayMetrics();
    }

    /** 获取屏幕宽度 */
    public static int getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    /** 获取屏幕高度 */
    public static int getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }


}
