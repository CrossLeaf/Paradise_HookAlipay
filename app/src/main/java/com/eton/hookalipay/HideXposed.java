package com.eton.hookalipay;

import android.app.ActivityManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class HideXposed {
    void hide(ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", classLoader, "getInstalledApplications", int.class, new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam methodHookParam) {
                List<ApplicationInfo> list = (List<ApplicationInfo>) methodHookParam.getResult();
                List arrayList = new ArrayList();
                for (ApplicationInfo applicationInfo : list) {
                    String str = applicationInfo.packageName;

                    if (isXposed(str)) {
                        XposedBridge.log("Hid package: " + str);
                    } else {
                        arrayList.add(applicationInfo);
                    }
                }
                methodHookParam.setResult(arrayList);
            }
        });
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", classLoader, "getInstalledPackages", int.class, new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam methodHookParam) {
                List<PackageInfo> list = (List<PackageInfo>) methodHookParam.getResult();
                List arrayList = new ArrayList();
                for (PackageInfo packageInfo : list) {
                    String str = packageInfo.packageName;

                    if (isXposed(str)) {
                        XposedBridge.log("Hid package: " + str);
                    } else {
                        arrayList.add(packageInfo);
                    }
                }
                methodHookParam.setResult(arrayList);
            }
        });
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", classLoader, "getPackageInfo", String.class, int.class, new XC_MethodHook() {
            protected void beforeHookedMethod(MethodHookParam methodHookParam) {
                String str = (String) methodHookParam.args[0];
                if (isXposed(str)) {
                    XposedBridge.log("Fake package: " + str);
                }
            }
        });
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", classLoader, "getApplicationInfo", String.class, int.class, new XC_MethodHook() {
            protected void beforeHookedMethod(MethodHookParam methodHookParam) {
                String str = (String) methodHookParam.args[0];
                if (isXposed(str)) {
                    XposedBridge.log("Fake package: " + str);
                }
            }
        });
        XposedHelpers.findAndHookMethod("android.app.ActivityManager", classLoader, "getRunningServices", int.class, new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam methodHookParam) {
                List<ActivityManager.RunningServiceInfo> list = (List<ActivityManager.RunningServiceInfo>) methodHookParam.getResult();
                List arrayList = new ArrayList();
                for (ActivityManager.RunningServiceInfo runningServiceInfo : list) {
                    String str = runningServiceInfo.process;

                    if (isXposed(str)) {
                        XposedBridge.log("Hid service: " + str);
                    } else {
                        arrayList.add(runningServiceInfo);
                    }
                }
                methodHookParam.setResult(arrayList);
            }
        });
        XposedHelpers.findAndHookMethod("android.app.ActivityManager", classLoader, "getRunningTasks", int.class, new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam methodHookParam) {
                List<ActivityManager.RunningTaskInfo> list = (List<ActivityManager.RunningTaskInfo>) methodHookParam.getResult();
                List arrayList = new ArrayList();
                for (ActivityManager.RunningTaskInfo runningTaskInfo : list) {
                    String flattenToString = runningTaskInfo.baseActivity.flattenToString();

                    if (isXposed(flattenToString)) {
                        XposedBridge.log("Hid task: " + flattenToString);
                    } else {
                        arrayList.add(runningTaskInfo);
                    }
                }
                methodHookParam.setResult(arrayList);
            }
        });
        XposedHelpers.findAndHookMethod("android.app.ActivityManager", classLoader, "getRunningAppProcesses", new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam methodHookParam) {
                if (methodHookParam.getResult() != null) {
                    List<ActivityManager.RunningAppProcessInfo> list = (List<ActivityManager.RunningAppProcessInfo>) methodHookParam.getResult();
                    List arrayList = new ArrayList();
                    for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : list) {
                        String str = runningAppProcessInfo.processName;

                        if (isXposed(str)) {
                            XposedBridge.log("Hid process: " + str);
                        } else {
                            arrayList.add(runningAppProcessInfo);
                        }
                    }
                    methodHookParam.setResult(arrayList);
                }
            }
        });
    }

    private boolean isXposed(String str) {
        return str.contains("hookalipay") || str.contains("xposed") || str.contains("exposed");
    }
}
