package com.eton.hookalipay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {
    public static final String Alipay = "com.eg.android.AlipayGphone";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals(BuildConfig.APPLICATION_ID)) {
            XposedHelpers.findAndHookMethod(MainActivity.class.getName(), lpparam.classLoader, "isModuleActive", XC_MethodReplacement.returnConstant(true));
        }
        Log.d("MainHook", "packageName = " + lpparam.packageName);
        if (lpparam.packageName.contains(Alipay)) {
            Log.d("MainHook", "handleLoadPackage: ");
            new HideXposed().hide(lpparam.classLoader);
            hookRecommandAlipayUserLoginActivity(lpparam.classLoader);
            hookLauncherActivity(lpparam.classLoader);
            hookMainCaptureActivity(lpparam.classLoader);
        }
    }

    public void hookRecommandAlipayUserLoginActivity(ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod("com.alipay.mobile.security.login.ui.RecommandAlipayUserLoginActivity", classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                XposedBridge.log("AlipayLogin hook onCreate 成功");
            }
        });
    }

    public void hookLauncherActivity(ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod("com.alipay.mobile.quinox.LauncherActivity", classLoader, "onResume", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                XposedBridge.log("LauncherActivity hook onResume 成功");
            }
        });
    }

    public void hookMainCaptureActivity(ClassLoader classLoader) {
        try {
            XposedHelpers.findAndHookMethod("com.alipay.mobile.scan.as.main.MainCaptureActivity", classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);

                    XposedBridge.log("MainCaptureActivity hook onCreate 成功");
                }
            });

            XposedHelpers.findAndHookMethod("com.alipay.mobile.scan.as.main.MainCaptureActivity", classLoader, "g"// m49199g
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("MainCaptureActivity hook g beforeHookedMethod 成功");
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);

                            XposedBridge.log("MainCaptureActivity hook g afterHookedMethod 成功");
                        }
                    });

            // 目前沒有 Hook 到
            XposedHelpers.findAndHookMethod("com.alipay.mobile.scan.as.main.MainCaptureActivity", classLoader, "a" // mo107238a
                    , "com.alipay.mobile.mascanengine.MaScanResult"
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("MainCaptureActivity hook a MaScanResult beforeHookedMethod 成功");
                            Class<?> MaScanResult = (Class<?>) param.args[0];
//                    String maScanResult = MaScanResult.text();
//                    XposedBridge.log("com.alipay.mobile.mascanengine.MaScanResult = " + maScanResult);
                            Field[] fields = MaScanResult.getFields();
                            for (Field f : fields) {
                                XposedBridge.log("他的屬性有什麼呢 = " + f.getName());
                            }
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);

                            XposedBridge.log("MainCaptureActivity hook a MaScanResult afterHookedMethod 成功");
                        }
                    });

            // 目前沒有 Hook 到
            XposedHelpers.findAndHookMethod("com.alipay.mobile.scan.as.main.MainCaptureActivity", classLoader, "b" // mo107241b
                    , "com.alipay.mobile.mascanengine.MaScanResult"
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("MainCaptureActivity hook b MaScanResult beforeHookedMethod 成功");
                            Class<?> MaScanResult = (Class<?>) param.args[0];
//                    String maScanResult = MaScanResult.text();
//                    XposedBridge.log("com.alipay.mobile.mascanengine.MaScanResult = " + maScanResult);
                            Field[] fields = MaScanResult.getFields();
                            for (Field f : fields) {
                                XposedBridge.log("他的屬性有什麼呢 = " + f.getName());
                            }
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);

                            XposedBridge.log("MainCaptureActivity hook b MaScanResult afterHookedMethod 成功");
                        }
                    });

            XposedHelpers.findAndHookMethod("com.alipay.mobile.scan.as.main.MainCaptureActivity"
                    , classLoader, "onActivityResult"
                    , int.class, int.class, Intent.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("MainCaptureActivity hook onActivityResult beforeHookedMethod 成功");
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("MainCaptureActivity hook onActivityResult afterHookedMethod 成功");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}