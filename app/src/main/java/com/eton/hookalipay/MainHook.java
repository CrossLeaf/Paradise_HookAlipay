package com.eton.hookalipay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
            hookPhotoSelectActivity(lpparam.classLoader);
            hookPhotoContext(lpparam.classLoader);
            hookPayeeQRPayFormActivity(lpparam.classLoader);
            hookLogger(lpparam.classLoader);
        }
    }

    public void hookRecommandAlipayUserLoginActivity(ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod("com.alipay.mobile.security.login.ui.RecommandAlipayUserLoginActivity"
                , classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);

                        XposedBridge.log("AlipayLogin hook onCreate 成功");
                    }
                });
    }

    public void hookLauncherActivity(ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod("com.alipay.mobile.quinox.LauncherActivity"
                , classLoader, "onResume", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);

                        XposedBridge.log("LauncherActivity hook onResume 成功");
                    }
                });
    }

    public void hookMainCaptureActivity(ClassLoader classLoader) {
        try {
            final Class<?> MainCaptureActivity = classLoader.loadClass("com.alipay.mobile.scan.as.main.MainCaptureActivity");
            XposedHelpers.findAndHookMethod(MainCaptureActivity
                    , "onCreate", Bundle.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);

                            XposedBridge.log("MainCaptureActivity hook onCreate 成功");
                        }
                    });

            XposedHelpers.findAndHookMethod(MainCaptureActivity
                    , "g"// m49199g
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
            XposedHelpers.findAndHookMethod(MainCaptureActivity
                    , "a" // mo107238a
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
            XposedHelpers.findAndHookMethod(MainCaptureActivity
                    , "b" // mo107241b
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

            XposedHelpers.findAndHookMethod(MainCaptureActivity
                    , "onActivityResult"
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

            // hook __onActivityResult_stub_private
            Method privateMethod = XposedHelpers.findMethodExact(MainCaptureActivity
                    , "__onActivityResult_stub_private"
                    , int.class, int.class, Intent.class);
            privateMethod.setAccessible(true);
            XposedHelpers.findAndHookMethod(MainCaptureActivity
                    , "__onActivityResult_stub_private"
                    , int.class, int.class, Intent.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("MainCaptureActivity hook __onActivityResult_stub_private beforeHookedMethod 成功");
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("MainCaptureActivity hook __onActivityResult_stub_private afterHookedMethod 成功");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hookPhotoSelectActivity(ClassLoader classLoader) {
        try {
            final Class<?> PhotoSelectActivity = classLoader.loadClass("com.alipay.mobile.beehive.photo.ui.PhotoSelectActivity");

            // hook __onActivityResult_stub_private
            Method privateMethod = XposedHelpers.findMethodExact(PhotoSelectActivity
                    , "__onActivityResult_stub_private"
                    , int.class, int.class, Intent.class);
            privateMethod.setAccessible(true);
            XposedHelpers.findAndHookMethod(PhotoSelectActivity
                    , "__onActivityResult_stub_private"
                    , int.class, int.class, Intent.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("PhotoSelectActivity hook __onActivityResult_stub_private beforeHookedMethod 成功");
                            // i = 8899
                            // i2 = -1
                            XposedBridge.log("i = " + param.args[0]);
                            XposedBridge.log("i2 = " + param.args[1]);
                            XposedBridge.log("intent string = " + ((Intent) param.args[2]).getDataString());
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("PhotoSelectActivity hook __onActivityResult_stub_private afterHookedMethod 成功");
                        }
                    });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void hookPhotoContext(ClassLoader classLoader) {
        try {
            final Class<?> PhotoContext = classLoader.loadClass("com.alipay.mobile.beehive.photo.data.PhotoContext");
            XposedHelpers.findAndHookMethod(PhotoContext
                    , "sendSelectedPhoto"
                    , Activity.class, float.class, int.class, Runnable.class, boolean.class, boolean.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("PhotoContext hook sendSelectedPhoto beforeHookedMethod 成功");
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
//                            Class<?> zString = (String) XposedHelpers.getObjectField(param.thisObject, "z");
                            XposedBridge.log("PhotoContext hook sendSelectedPhoto afterHookedMethod 成功");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hookPayeeQRPayFormActivity(final ClassLoader classLoader) {
        try {
            final Class<?> PayeeQRPayFormActivity = classLoader.loadClass("com.alipay.mobile.payee.ui.PayeeQRPayFormActivity");

            XposedHelpers.findAndHookMethod(PayeeQRPayFormActivity
                    , "onCreate", Bundle.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("PayeeQRPayFormActivity hook onCreate beforeHookedMethod 成功");
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("PayeeQRPayFormActivity hook onCreate afterHookedMethod 成功");
                        }
                    });

            Method privateMethod = XposedHelpers.findMethodExact(PayeeQRPayFormActivity, "__onCreate_stub_private", Bundle.class);
            privateMethod.setAccessible(true);
            XposedHelpers.findAndHookMethod(PayeeQRPayFormActivity
                    , "__onCreate_stub_private", Bundle.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("PayeeQRPayFormActivity hook __onCreate_stub_private beforeHookedMethod 成功");
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("PayeeQRPayFormActivity hook __onCreate_stub_private afterHookedMethod 成功");
                        }
                    });

            XposedHelpers.findAndHookMethod(PayeeQRPayFormActivity
                    , "onActivityResult", int.class, int.class, Intent.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("PayeeQRPayFormActivity hook onActivityResult beforeHookedMethod 成功");
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("PayeeQRPayFormActivity hook onActivityResult afterHookedMethod 成功");
                        }
                    });

            Method privateA = XposedHelpers.findMethodExact(PayeeQRPayFormActivity, "a", String.class);
            privateA.setAccessible(true);
            XposedHelpers.findAndHookMethod(PayeeQRPayFormActivity
                    , "a" // m6032a
                    , String.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("PayeeQRPayFormActivity hook private a beforeHookedMethod 成功");
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("PayeeQRPayFormActivity hook private a afterHookedMethod 成功");
                        }
                    });

            final Method privateC = XposedHelpers.findMethodExact(PayeeQRPayFormActivity, "c");
            privateC.setAccessible(true);
            XposedHelpers.findAndHookMethod(PayeeQRPayFormActivity
                    , "c" // m6034c
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("PayeeQRPayFormActivity hook private c beforeHookedMethod 成功");
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("PayeeQRPayFormActivity hook private c afterHookedMethod 成功");
                            XposedBridge.log("回傳:" + param.getResult());
                            String zString = (String) XposedHelpers.getObjectField(param.thisObject, "z");
                            try {
                                XposedBridge.log("支付寶轉帳金額 = " + zString);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void hookLogger(ClassLoader classLoader) {
        try {
            final Class<?> Logger = classLoader.loadClass("com.alipay.mobile.bqcscanservice.Logger");
            XposedHelpers.findAndHookMethod(Logger, "d"
                    , String.class, Object[].class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("Logger d arg0 = " + param.args[0]);
                            Object[] obArray = (Object[]) param.args[1];
                            for (Object ob : obArray) {
                                XposedBridge.log("Logger d arg0 = " + ob);
                            }
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                        }
                    });


            final Class<?> PhotoLogger = classLoader.loadClass("com.alipay.mobile.beehive.photo.util.PhotoLogger");
            XposedHelpers.findAndHookMethod(PhotoLogger, "debug", String.class, String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    XposedBridge.log("PhotoLogger hook debug beforeHookedMethod 成功");
                    XposedBridge.log("debug str = " + param.args[0] + "\t str2 = " + param.args[1]);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    XposedBridge.log("PhotoLogger hook debug afterHookedMethod 成功");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
