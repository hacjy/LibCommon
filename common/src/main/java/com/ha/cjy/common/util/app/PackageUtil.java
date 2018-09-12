package com.ha.cjy.common.util.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 包管理工具
 * Created by cjy on 2018/7/9.
 */

public class PackageUtil {
    /**
     * 获取包名
     * @param context
     * @return
     */
    public static String getPackageName(Context context){
        return context.getPackageName();
    }

    /**
     * 获取当前应用程序的包名
     * @param context 上下文对象
     * @return 返回包名
     */
    public static String getAppProcessName(Context context) {
        //当前应用pid
        int pid = android.os.Process.myPid();
        //任务管理类
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == pid)//得到当前应用
                return info.processName;//返回包名
        }
        return "";
    }
    /**
     * 获取程序 图标
     * @param context
     * @param packname 应用包名
     * @return
     */
    public Drawable getAppIcon(Context context, String packname){
        try {
            //包管理操作管理类
            PackageManager pm = context.getPackageManager();
            //获取到应用信息
            ApplicationInfo info = pm.getApplicationInfo(packname, 0);
            return info.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取程序的版本号
     * @param context
     * @param packname
     * @return
     */
    public String getAppVersion(Context context,String packname){
        //包管理操作管理类
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packinfo = pm.getPackageInfo(packname, 0);
            return packinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        return packname;
    }


    /**
     * 获取程序的名字
     * @param context
     * @param packname
     * @return
     */
    public String getAppName(Context context,String packname){
        //包管理操作管理类
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo info = pm.getApplicationInfo(packname, 0);
            return info.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return packname;
    }
    /*
     * 获取程序的权限
     */
    public String[] getAllPermissions(Context context,String packname){
        try {
            //包管理操作管理类
            PackageManager pm = context.getPackageManager();
            PackageInfo packinfo =    pm.getPackageInfo(packname, PackageManager.GET_PERMISSIONS);
            //获取到所有的权限
            return packinfo.requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        return null;
    }


    /**
     * 获取程序的签名
     * @param context
     * @param packname
     * @return
     */
    public static String getAppSignature(Context context,String packname){
        try {
            //包管理操作管理类
            PackageManager pm = context.getPackageManager();
            PackageInfo packinfo = pm.getPackageInfo(packname, PackageManager.GET_SIGNATURES);
            //获取当前应用签名
            return packinfo.signatures[0].toCharsString();

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        return packname;
    }
    /**
     * 获取当前展示 的Activity名称
     * @return
     */
    private static String getCurrentActivityName(Context context){
        ActivityManager activityManager=(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }

    public static boolean isRoot() {
        boolean isRoot = false;
        String sys = System.getenv("PATH");
        ArrayList<String> commands = new ArrayList();
        String[] path = sys.split(":");

        String response;
        for(int i = 0; i < path.length; ++i) {
            response = "ls -l " + path[i] + "/su";
            commands.add(response);
            System.out.println("commod : " + response);
        }

        ArrayList<String> res = run("/system/bin/sh", commands);
        response = "";

        int inavailableCount;
        for(inavailableCount = 0; inavailableCount < res.size(); ++inavailableCount) {
            response = response + (String)res.get(inavailableCount);
        }

        inavailableCount = 0;
        String root = "-rwsr-sr-x root root";

        for(int i = 0; i < res.size(); ++i) {
            if(((String)res.get(i)).contains("No such file or directory") || ((String)res.get(i)).contains("Permission denied")) {
                ++inavailableCount;
            }
        }

        return inavailableCount < res.size();
    }

    private static ArrayList run(String shell, ArrayList<String> commands) {
        ArrayList output = new ArrayList();
        Process process = null;

        try {
            process = Runtime.getRuntime().exec(shell);
            BufferedOutputStream shellInput = new BufferedOutputStream(process.getOutputStream());
            BufferedReader shellOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            Iterator var6 = commands.iterator();

            while(var6.hasNext()) {
                String command = (String)var6.next();
                shellInput.write((command + " 2>&1\n").getBytes());
            }

            shellInput.write("exit\n".getBytes());
            shellInput.flush();

            String line;
            while((line = shellOutput.readLine()) != null) {
                output.add(line);
            }

            process.waitFor();
        } catch (IOException var12) {
            var12.printStackTrace();
        } catch (InterruptedException var13) {
            var13.printStackTrace();
        } finally {
            process.destroy();
        }

        return output;
    }


    public static void installPackage(final String packagePath) {
        (new Thread() {
            public void run() {
                Process process = null;
                OutputStream out = null;
                InputStream in = null;

                try {
                    process = Runtime.getRuntime().exec("su");
                    out = process.getOutputStream();
                    out.write(("pm install -r " + packagePath + "\n").getBytes());
                    out.flush();
                    in = process.getInputStream();
                    int len = 0;
                    byte[] bs = new byte[256];

                    int lenx;
                    while(-1 != (lenx = in.read(bs))) {
                        String state = new String(bs, 0, lenx);
                        if(state.equals("Success\n")) {
                            ;
                        }
                    }
                } catch (IOException var17) {
                    var17.printStackTrace();
                } catch (Exception var18) {
                    var18.printStackTrace();
                } finally {
                    try {
                        if(out != null) {
                            out.flush();
                            out.close();
                        }

                        if(in != null) {
                            in.close();
                        }
                    } catch (IOException var16) {
                        var16.printStackTrace();
                    }

                }

            }
        }).start();
    }

    public static boolean installApplicationNormal(Context ctx, String path) {
        return installApplicationNormal(ctx, new File(path));
    }

    public static boolean installApplicationNormal(Context ctx, File mainFile) {
        try {
            Uri data = Uri.fromFile(mainFile);
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(data, "application/vnd.android.package-archive");
            ctx.startActivity(intent);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return true;
    }

    public static int uninstallPackage(String packageName) {
        int result = -1;
        DataOutputStream dos = null;

        byte var4;
        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            dos.writeBytes("pm uninstall " + packageName + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            p.waitFor();
            result = p.exitValue();
            return result;
        } catch (Exception var14) {
            var14.printStackTrace();
            var4 = (byte) result;
        } finally {
            if(dos != null) {
                try {
                    dos.close();
                } catch (IOException var13) {
                    var13.printStackTrace();
                }
            }

        }

        return var4;
    }

    /**
     * 闪光灯判断
     * @param pm
     * @return 是否有闪光灯
     */
    public static boolean isSupportCameraLedFlash(PackageManager pm) {
        if (pm != null) {
            FeatureInfo[] features = pm.getSystemAvailableFeatures();
            if (features != null) {
                for (FeatureInfo f : features) {
                    if (f != null && PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 是否安装应用了
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isPackageInstalled(Context context, String packageName) {
        if(packageName == null) {
            return false;
        } else {
            PackageInfo packageInfo;
            try {
                packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            } catch (PackageManager.NameNotFoundException var4) {
                packageInfo = null;
                var4.printStackTrace();
            }

            return packageInfo != null;
        }
    }
}
