package com.eton.hookalipay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

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
            hookMainCaptureActivity(lpparam.classLoader);
//            hookPhotoSelectActivity(lpparam.classLoader);
//            hookPhotoContext(lpparam.classLoader);
            hookPayeeQRPayFormActivity(lpparam.classLoader);
            hookLogger(lpparam.classLoader);
            hookCodeRouteActivity(lpparam.classLoader);
//            hookBaseActivity(lpparam.classLoader);
//            hookSpmHelper(lpparam.classLoader);
//            hookC24532b(lpparam.classLoader);
            hookPayeeApp(lpparam.classLoader);
//            hookGetIntent(lpparam.classLoader);
        }
    }

    public void hookMainCaptureActivity(ClassLoader classLoader) {
        try {
            final Class<?> MainCaptureActivity = classLoader.loadClass("com.alipay.mobile.scan.as.main.MainCaptureActivity");
            XposedHelpers.findAndHookMethod(MainCaptureActivity
                    , "onCreate", Bundle.class
                    , new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("MainCaptureActivity hook onCreate 成功");
                            callmo107238aMethod(classLoader, param.thisObject);
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
//            XposedHelpers.findAndHookMethod(MainCaptureActivity
//                    , "a" // mo107238a
//                    , "com.alipay.mobile.mascanengine.MaScanResult"
//                    , new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            super.beforeHookedMethod(param);
//                            XposedBridge.log("MainCaptureActivity hook a MaScanResult beforeHookedMethod 成功");
//                            Class<?> MaScanResult = (Class<?>) param.args[0];
//                    String maScanResult = MaScanResult.text();
//                    XposedBridge.log("com.alipay.mobile.mascanengine.MaScanResult = " + maScanResult);
//                            Field[] fields = MaScanResult.getFields();
//                            for (Field f : fields) {
//                                XposedBridge.log("他的屬性有什麼呢 = " + f.getName());
//                            }
//                        }
//
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                            super.afterHookedMethod(param);
//
//                            XposedBridge.log("MainCaptureActivity hook a MaScanResult afterHookedMethod 成功");
//                        }
//                    });

            // 目前沒有 Hook 到
//            XposedHelpers.findAndHookMethod(MainCaptureActivity
//                    , "b" // mo107241b
//                    , "com.alipay.mobile.mascanengine.MaScanResult"
//                    , new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            super.beforeHookedMethod(param);
//                            XposedBridge.log("MainCaptureActivity hook b MaScanResult beforeHookedMethod 成功");
//                            Class<?> MaScanResult = (Class<?>) param.args[0];
////                    String maScanResult = MaScanResult.text();
////                    XposedBridge.log("com.alipay.mobile.mascanengine.MaScanResult = " + maScanResult);
//                            Field[] fields = MaScanResult.getFields();
//                            for (Field f : fields) {
//                                XposedBridge.log("他的屬性有什麼呢 = " + f.getName());
//                            }
//                        }
//
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                            super.afterHookedMethod(param);
//
//                            XposedBridge.log("MainCaptureActivity hook b MaScanResult afterHookedMethod 成功");
//                        }
//                    });

//            XposedHelpers.findAndHookMethod(MainCaptureActivity
//                    , "onActivityResult"
//                    , int.class, int.class, Intent.class
//                    , new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            super.beforeHookedMethod(param);
//                            XposedBridge.log("MainCaptureActivity hook onActivityResult beforeHookedMethod 成功");
//                        }
//
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                            super.afterHookedMethod(param);
//                            XposedBridge.log("MainCaptureActivity hook onActivityResult afterHookedMethod 成功");
//                        }
//                    });

            // hook __onActivityResult_stub_private
//            Method privateMethod = XposedHelpers.findMethodExact(MainCaptureActivity
//                    , "__onActivityResult_stub_private"
//                    , int.class, int.class, Intent.class);
//            privateMethod.setAccessible(true);
//            XposedHelpers.findAndHookMethod(MainCaptureActivity
//                    , "__onActivityResult_stub_private"
//                    , int.class, int.class, Intent.class
//                    , new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            super.beforeHookedMethod(param);
//                            XposedBridge.log("MainCaptureActivity hook __onActivityResult_stub_private beforeHookedMethod 成功");
//                        }
//
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                            super.afterHookedMethod(param);
//                            XposedBridge.log("MainCaptureActivity hook __onActivityResult_stub_private afterHookedMethod 成功");
//                        }
//                    });

            // Hook m49527b
            XposedHelpers.findAndHookMethod("com.alipay.mobile.scan.ui.ma.MaScanTopView", classLoader
                    , "b", "com.alipay.mobile.bqcscanservice.BQCScanResult"
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("MaScanTopView hook m49527b beforeHookedMethod 成功");
                        }
                    });

            // Hook mo107238a
            XposedHelpers.findAndHookMethod(MainCaptureActivity, "a"
                    , "com.alipay.mobile.mascanengine.MaScanResult"
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("MainCaptureActivity hook mo107238a beforeHookedMethod 成功");
                            XposedBridge.log("MainCaptureActivity hook mo107238a param 0 = " + param.args[0]);
                            // 取得參數的 class 方式
                            Class<?> MaScanResult = param.args[0].getClass();
                            Field[] fields = MaScanResult.getFields();
                            for (Field f : fields) {
                                XposedBridge.log("他的屬性有什麼呢 = " + f.getName());
                                XposedBridge.log("value = " + XposedHelpers.getObjectField(param.args[0], f.getName()));
                                if (f.getName().equals("rawData")) {
                                    StringBuilder sb = new StringBuilder();
                                    byte[] bytes = (byte[]) XposedHelpers.getObjectField(param.args[0], f.getName());
                                    for (byte b : bytes) {
                                        sb.append(String.format("0x%02X ", b));
                                    }
                                    XposedBridge.log("rawData value = " + sb);

                                }
                            }
//                            XposedHelpers.setObjectField(param.args[0], "text", "https://qr.alipay.com/fkx19699yf1mjdmiyi1jjc7");
                            XposedBridge.log("new text value = " + XposedHelpers.getObjectField(param.args[0], "text"));
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("MainCaptureActivity hook mo107238a afterHookedMethod 成功");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            XposedBridge.log("hook MainCaptureActivity exception = " + e.getMessage());
        }
    }

    public void callmo107238aMethod(ClassLoader classLoader, Object object) {
        // 呼叫有參數方法，回傳 void
        try {
            XposedBridge.log("call mo107238a method");
            Class<?> cls = XposedHelpers.findClassIfExists("com.alipay.mobile.mascanengine.MaScanResult", classLoader);
            Object MaScanResult = XposedHelpers.newInstance(cls);
            Field bitErrors = cls.getDeclaredField("bitErrors");
            bitErrors.set(MaScanResult, 34);
            Field charset = cls.getDeclaredField("charset");
            charset.set(MaScanResult, "UTF8");
            Field codeProportion = cls.getDeclaredField("codeProportion");
            codeProportion.set(MaScanResult, 0.19596706f);
            Field ecLevel = cls.getDeclaredField("ecLevel");
            ecLevel.set(MaScanResult, 'Q');
            Field errPercent = cls.getDeclaredField("errPercent");
            errPercent.set(MaScanResult, 0.44444445f);
            Field qrSize = cls.getDeclaredField("qrSize");
            qrSize.set(MaScanResult, 74l);
            Field rawData = cls.getDeclaredField("rawData");
            rawData.set(MaScanResult, new byte[]{0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F, 0x71, 0x72, 0x2E, 0x61, 0x6C, 0x69, 0x70, 0x61, 0x79, 0x2E, 0x63, 0x6F, 0x6D, 0x2F, 0x66, 0x6B, 0x78, 0x31, 0x37, 0x31, 0x33, 0x31, 0x74, 0x37, 0x75, 0x63, 0x6D, 0x6C, 0x35, 0x65, 0x77, 0x73, 0x34, 0x71, 0x78, 0x37, 0x34, 0x3F, 0x74, 0x3D, 0x31, 0x35, 0x38, 0x36, 0x35, 0x30, 0x37, 0x34, 0x31, 0x32, 0x38, 0x33, 0x35});
            Field recognizedPerformance = cls.getDeclaredField("recognizedPerformance");
            recognizedPerformance.set(MaScanResult, null);
            Field rect = cls.getDeclaredField("rect");
            rect.set(MaScanResult, new Rect(822, 217, 978, 379));
            Field strategy = cls.getDeclaredField("strategy");
            strategy.set(MaScanResult, 512);
            Field text = cls.getDeclaredField("text");
            text.set(MaScanResult, "https://qr.alipay.com/fkx11610ffw88fueppfky39?t=1587539225274");
            Field totalEngineCpuTime = cls.getDeclaredField("totalEngineCpuTime");
            totalEngineCpuTime.set(MaScanResult, "444959");
            Field totalEngineTime = cls.getDeclaredField("totalEngineTime");
            totalEngineTime.set(MaScanResult, "750797");
            Field totalScanTime = cls.getDeclaredField("totalScanTime");
            totalScanTime.set(MaScanResult, "356640");
            Field type = cls.getDeclaredField("type");
            Class<?> MaScanType = classLoader.loadClass("com.alipay.mobile.mascanengine.MaScanType");
            Object[] enums = MaScanType.getEnumConstants();
            for (Object obj : enums) {
                XposedBridge.log("MaScanType QR = " + obj);
                if ("QR".equals(obj.toString())) {
                    type.set(MaScanResult, obj);
                }
            }
            Field typeName = cls.getDeclaredField("typeName");
            typeName.set(MaScanResult, null);
            Field version = cls.getDeclaredField("version");
            version.set(MaScanResult, 5);

            XposedHelpers.callMethod(object, "a", MaScanResult);
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

    /**
     * hook 此方法會導致支付寶在掃一掃的相簿裡選取照片，無法跳轉至轉帳頁面
     *
     * @param classLoader
     */
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
            XposedHelpers.findAndHookMethod(Logger, "d" //m47951d
                    , String.class, Object[].class
                    , new XC_MethodHook() {
                        boolean isMTBizReporter = false;

                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("Logger d arg0 = " + param.args[0]);
                            Object[] obArray = (Object[]) param.args[1];
                            for (Object ob : obArray) {
                                XposedBridge.log("Logger d arg1 = " + ob);
                            }

                            if (param.args[0].toString().contains("MTBizReporter")) {
                                XposedBridge.log("==========");
                                isMTBizReporter = true;
                            } else {
                                isMTBizReporter = false;
                            }
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            if (isMTBizReporter) {
                                XposedBridge.log("Logger afterHookedMethod");
                            }
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

    private void hookCodeRouteActivity(final ClassLoader classLoader) {
        try {
            final Class<?> CodeRouteActivity = classLoader.loadClass("com.alipay.mobile.scan.as.router.CodeRouteActivity");
            XposedHelpers.findAndHookMethod(CodeRouteActivity
                    , "onCreate"
                    , Bundle.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("CodeRouteActivity hook onCreate beforeHookedMethod 成功");
                            hookC24532b(classLoader);
                        }
                    });

            Method privateMethod = XposedHelpers.findMethodExact(CodeRouteActivity, "__onCreate_stub_private", Bundle.class);
            privateMethod.setAccessible(true);
            XposedHelpers.findAndHookMethod(CodeRouteActivity
                    , privateMethod.getName()
                    , Bundle.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("CodeRouteActivity hook __onCreate_stub_private beforeHookedMethod 成功");
//                            hookGetIntent(CodeRouteActivity.getName(), classLoader);
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("CodeRouteActivity hook __onCreate_stub_private afterHookedMethod 成功");
//                            hookGetIntent(CodeRouteActivity.getName(), classLoader);
                        }
                    });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void hookSpmHelper(ClassLoader classLoader) {
        try {
            Class<?> SpmHelperClass = XposedHelpers.findClassIfExists("com.alipay.mobile.payee.util.SpmHelper$Monitor", classLoader);
            if (SpmHelperClass == null) {
                XposedBridge.log("SpmHelper = null");
            } else {
                XposedBridge.log("SpmHelper != null");
//                hookAllMethod(SpmHelperClass);
            }

            final Class<?> SpmHelper = classLoader.loadClass("com.alipay.mobile.payee.util.SpmHelper$Monitor");
            XposedHelpers.findAndHookMethod(SpmHelper
                    , "a" // m6144a
                    , boolean.class, "com.alipay.transferprod.rpc.result.RPCResponse", Activity.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("SpmHelper hook a beforeHookedMethod 成功");
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("SpmHelper hook a afterHookedMethod 成功");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hookC24532b(ClassLoader classLoader) {
        try {
            final Class<?> C24532b = classLoader.loadClass("com.alipay.phone.scancode.x.b");
            // Hook m51501a
            XposedHelpers.findAndHookMethod(C24532b
                    , "a"
                    , String.class, Map.class, String.class, int.class, String.class, boolean.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("C24532b hook m51501a beforeHookedMethod 成功");
                            XposedBridge.log("C24532b m51501a param 0 = " + param.args[0]);
                            XposedBridge.log("C24532b m51501a param 1 = " + param.args[1]);
                            XposedBridge.log("C24532b m51501a param 2 = " + param.args[2]);
                            XposedBridge.log("C24532b m51501a param 3 = " + param.args[3]);
                            XposedBridge.log("C24532b m51501a param 4 = " + param.args[4]);
                            XposedBridge.log("C24532b m51501a param 5 is z = " + param.args[5]);
                            param.args[5] = false;
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("C24532b hook m51501a afterHookedMethod 成功");
                        }
                    });

            // Hook m51494a
            XposedHelpers.findAndHookMethod(C24532b
                    , "a"
                    , C24532b, String.class, Map.class, String.class, int.class, String.class, boolean.class, "com.alipay.android.phone.scan.bizcache.db.CacheItem", boolean.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("C24532b hook m51494a beforeHookedMethod 成功");
                            XposedBridge.log("C24532b m51494a param 0 C24532b = " + param.args[0]);
                            XposedBridge.log("C24532b m51494a param 1 = " + param.args[1]);
                            XposedBridge.log("C24532b m51494a param 2 = " + param.args[2]);
                            XposedBridge.log("C24532b m51494a param 3 = " + param.args[3]);
                            XposedBridge.log("C24532b m51494a param 4 = " + param.args[4]);
                            XposedBridge.log("C24532b m51494a param 5 = " + param.args[5]);
                            XposedBridge.log("C24532b m51494a param 6 = " + param.args[6]);
                            XposedBridge.log("C24532b m51494a param 7 CacheItem = " + param.args[7]);
                            XposedBridge.log("C24532b m51494a param 8 = " + param.args[8]);
                            hookC23751a(classLoader);
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("C24532b hook m51494a afterHookedMethod 成功");
                        }
                    });

            // Hook m51502a
            XposedHelpers.findAndHookMethod(C24532b
                    , "a"
                    , String.class, boolean.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("C24532b hook m51502a beforeHookedMethod 成功");
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("C24532b hook m51502a afterHookedMethod 成功");
                        }
                    });

            // Hook m51509a
            XposedHelpers.findAndHookMethod(C24532b
                    , "a"
                    , String.class, Map.class, String.class, String.class, int.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("C24532b hook m51509a beforeHookedMethod 成功");
                            XposedBridge.log("C24532b m51509a param 0 = " + param.args[0]);
                            XposedBridge.log("C24532b m51509a param 1 = " + param.args[1]);
                            XposedBridge.log("C24532b m51509a param 2 = " + param.args[2]);
                            XposedBridge.log("C24532b m51509a param 3 = " + param.args[3]);
                            XposedBridge.log("C24532b m51509a param 4 = " + param.args[4]);
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("C24532b hook m51509a afterHookedMethod 成功");
                            // hook f86657J field
                            Class<?> f86657J = (Class<?>) XposedHelpers.getObjectField(param.thisObject, "J");
                            XposedBridge.log("C24532b hook f86657J field = " + f86657J);
                            // hook f86656I field
                            Field f = C24532b.getDeclaredField("I"); //NoSuchFieldException
                            f.setAccessible(true);
                            Class<?> f86656I = (Class<?>) XposedHelpers.getObjectField(param.thisObject, f.getName());
                            XposedBridge.log("C24532b hook f86656I field = " + f86656I);


                        }
                    });

            // Hook m51516b
            Method private_b = XposedHelpers.findMethodExact(C24532b
                    , "b"
                    , String.class, Map.class);
            private_b.setAccessible(true);
            XposedHelpers.findAndHookMethod(C24532b
                    , private_b.getName()
                    , String.class, Map.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("C24532b hook m51516b beforeHookedMethod 成功");
                            XposedBridge.log("C24532b m51516b param 0 = " + param.args[0]);
                            XposedBridge.log("C24532b m51516b param 1 = " + param.args[1]);
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("C24532b hook m51516b afterHookedMethod 成功");
                        }
                    });


            // Hook IFLCommonApi logEnvInfo()
            final Class<?> IFLCommonApi = classLoader.loadClass("com.alipay.android.phone.fulllinktracker.internal.b.e");
            XposedHelpers.findAndHookMethod(IFLCommonApi
                    , "logEnvInfo"
                    , String.class, String.class, String.class, String.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("IFLCommonApi hook logEnvInfo beforeHookedMethod 成功");
                            XposedBridge.log("IFLCommonApi param 0 = " + param.args[0]);
                            XposedBridge.log("IFLCommonApi param 1 = " + param.args[1]);
                            XposedBridge.log("IFLCommonApi param 2 = " + param.args[2]);
                            XposedBridge.log("IFLCommonApi param 3 = " + param.args[3]);
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("IFLCommonApi hook logEnvInfo afterHookedMethod 成功");
                        }
                    });

            hookC23760f(classLoader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hookC23751a(ClassLoader classLoader) {
        try {
            // Hook mo107283a()
            final Class<?> C23751a = classLoader.loadClass("com.alipay.mobile.scan.biz.a");
            XposedHelpers.findAndHookMethod(C23751a
                    , "a"
                    , String.class, String.class, Map.class, String.class, String.class, String.class, boolean.class, boolean.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("C23751a hook mo107283a beforeHookedMethod 成功");
                            XposedBridge.log("C23751a mo107283a param 7 z2 = " + param.args[7]);
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("C23751a hook mo107283a afterHookedMethod 成功");
                            XposedBridge.log("C23751a hook RouteRes= " + param.getResult().toString());
                            String memo = (String) XposedHelpers.getObjectField(param.getResult(), "memo");
                            XposedBridge.log("C23751a hook RouteRes memo = " + memo);
                        }
                    });


            // Hook private method m49260a()
            final Method private_a = XposedHelpers.findMethodExact(C23751a
                    , "a"
                    , String.class, String.class, Map.class, String.class, String.class, String.class, String.class, boolean.class, boolean.class, long.class);
            private_a.setAccessible(true);
            XposedHelpers.findAndHookMethod(C23751a
                    , private_a.getName()
                    , String.class, String.class, Map.class, String.class, String.class, String.class, String.class, boolean.class, boolean.class, long.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
//                            ((HashMap<String, String>) param.args[2]).put("code", "https://qr.alipay.com/fkx15333wmsxbsrkla5wzdb?t=1587524197818");
//                            ((HashMap<String, String>) param.args[2]).put("extra_scheme", "{\"startFromExternal\":\"true\",\"REALLY_STARTAPP\":\"true\",\"ap_framework_sceneId\":\"20000001\",\"saId\":\"10000007\",\"ap_framework_scheme\":\"alipays:\\/\\/platformapi\\/startapp?saId=10000007&qrcode=https%3A%2F%2Fqr.alipay.com%2Ffkx15333wmsxbsrkla5wzdb%3Ft%3D1587524197818\",\"INSTANT_STARTAPP\":\"true\",\"REALLY_DOSTARTAPP\":\"true\"}");
                            XposedBridge.log("C23751a hook m49260a beforeHookedMethod 成功");
                            XposedBridge.log("C23751a m49260a param 0 = " + param.args[0]);
                            XposedBridge.log("C23751a m49260a param 1 = " + param.args[1]);
                            XposedBridge.log("C23751a m49260a param 2 = " + param.args[2]);
                            XposedBridge.log("C23751a m49260a param 3 = " + param.args[3]);
                            XposedBridge.log("C23751a m49260a param 4 = " + param.args[4]);
                            XposedBridge.log("C23751a m49260a param 5 = " + param.args[5]);
                            XposedBridge.log("C23751a m49260a param 6 = " + param.args[6]);
                            XposedBridge.log("C23751a m49260a param 7 = " + param.args[7]);
                            XposedBridge.log("C23751a m49260a param 8 = " + param.args[8]);
                            XposedBridge.log("C23751a m49260a param 9 = " + param.args[9]);

                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("C23751a hook m49260a afterHookedMethod 成功");
                            long f81913e = (long) XposedHelpers.getObjectField(param.thisObject, "e");
                            long f81914f = (long) XposedHelpers.getObjectField(param.thisObject, "f");
                            XposedBridge.log("C23751a hook m49260a() f81913e = " + f81913e + "\t f81914f = " + f81914f);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hookC23760f(ClassLoader classLoader) {
        try {
            final Class<?> C23760f = classLoader.loadClass("com.alipay.mobile.scan.record.behavior.f");
            // Hook m49304a
            XposedHelpers.findAndHookMethod(C23760f
                    , "a"
                    , boolean.class, long.class, boolean.class, int.class, String.class, String.class, long.class, boolean.class, String.class, String.class, String.class, boolean.class, boolean.class, boolean.class, String.class, String.class, boolean.class, String.class, boolean.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("C23760f hook m49304a beforeHookedMethod 成功");
                            XposedBridge.log("C23760f m49304a param 0 = " + param.args[0]);
                            XposedBridge.log("C23760f m49304a param 1 = " + param.args[1]);
                            XposedBridge.log("C23760f m49304a param 2 = " + param.args[2]);
                            XposedBridge.log("C23760f m49304a param 3 = " + param.args[3]);
                            XposedBridge.log("C23760f m49304a param 4 = " + param.args[4]);
                            XposedBridge.log("C23760f m49304a param 5 = " + param.args[5]);
                            XposedBridge.log("C23760f m49304a param 6 = " + param.args[6]);
                            XposedBridge.log("C23760f m49304a param 7 = " + param.args[7]);
                            XposedBridge.log("C23760f m49304a param 8 = " + param.args[8]);
                            XposedBridge.log("C23760f m49304a param 9 = " + param.args[9]);
                            XposedBridge.log("C23760f m49304a param 10 = " + param.args[10]);
                            XposedBridge.log("C23760f m49304a param 11 = " + param.args[11]);
                            XposedBridge.log("C23760f m49304a param 12 = " + param.args[12]);
                            XposedBridge.log("C23760f m49304a param 13 = " + param.args[13]);
                            XposedBridge.log("C23760f m49304a param 14 = " + param.args[14]);
                            XposedBridge.log("C23760f m49304a param 15 = " + param.args[15]);
                            XposedBridge.log("C23760f m49304a param 16 = " + param.args[16]);
                            XposedBridge.log("C23760f m49304a param 17 = " + param.args[17]);
                            XposedBridge.log("C23760f m49304a param 18 = " + param.args[18]);
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("C23751a hook m49304a afterHookedMethod 成功");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hookPayeeApp(ClassLoader classLoader) {
        try {
            final Class<?> PayeeApp = classLoader.loadClass("com.alipay.mobile.payee.app.PayeeApp");
            XposedHelpers.findAndHookMethod(PayeeApp
                    , "route"
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("PayeeApp hook route beforeHookedMethod 成功");
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("PayeeApp hook route afterHookedMethod 成功");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hookGetIntent(final String className, ClassLoader classLoader) {
        try {
            XposedHelpers.findAndHookMethod("android.app.Activity", classLoader, "getIntent", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Intent intent = (Intent) param.getResult();
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        for (String key : bundle.keySet()) {
                            XposedBridge.log("getIntent class name = " + className +
                                    "Key=" + key + ", content=" + bundle.getString(key));
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hookAllMethod(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            XposedBridge.log("所有方法名稱：" + method.getName());
            if (method.getName().equals("方法")
                    && !Modifier.isAbstract(method.getModifiers())
                    && Modifier.isPublic(method.getModifiers())) {
                XposedBridge.hookMethod(method, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                });
            }
        }
    }
}
