package com.eton.hookalipay;

import android.content.Intent;
import android.os.BaseBundle;
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
            hookPayeeQRPayFormActivity(lpparam.classLoader);
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
            XposedHelpers.findAndHookMethod("com.alipay.mobile.scan.as.main.MainCaptureActivity"
                    , classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);

                            XposedBridge.log("MainCaptureActivity hook onCreate 成功");
                        }
                    });

            XposedHelpers.findAndHookMethod("com.alipay.mobile.scan.as.main.MainCaptureActivity"
                    , classLoader, "g"// m49199g
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
            XposedHelpers.findAndHookMethod("com.alipay.mobile.scan.as.main.MainCaptureActivity"
                    , classLoader, "a" // mo107238a
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
            XposedHelpers.findAndHookMethod("com.alipay.mobile.scan.as.main.MainCaptureActivity"
                    , classLoader, "b" // mo107241b
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
                            Field zField = XposedHelpers.findField(param.thisObject.getClass(),"z");
                            XposedBridge.log("field.toString = " + (String) zField.get(param.thisObject));
//                            XposedBridge.log("name = "+field.getName() +  ", field.PayeeQRPayFormActivity  = " + field.get(PayeeQRPayFormActivity));
//                            XposedHelpers.findAndHookMethod(BaseBundle.class
//                                    , "getString"
//                                    , String.class, new XC_MethodHook() {
//                                        @Override
//                                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                                            super.beforeHookedMethod(param);
//                                            XposedBridge.log("PayeeQRPayFormActivity hook beforeHookedMethod getString = " + param.getResult());
//                                        }
//
//                                        /**
//                                         * result = {"a":"397.00","c":"2004096651498161","s":"online","u":"2088731738163665","m":"我是帥哥"}
//                                         */
//                                        @Override
//                                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                            super.afterHookedMethod(param);
//                                            XposedBridge.log("PayeeQRPayFormActivity hook afterHookedMethod getString = " + param.getResult());
//                                        }
//                                    });
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("PayeeQRPayFormActivity hook private c afterHookedMethod 成功");
                            XposedBridge.log("回傳:" + param.getResult());
                        }
                    });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
