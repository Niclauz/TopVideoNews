package cn.tinker;

import cn.com.ichile.topvideonews.util.Logger;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/1/13 - 17:21.
 */

public class SimpleUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "SIMPLE_UNCAUGHT_EXCEPTION";
    private final Thread.UncaughtExceptionHandler ueh;

    public SimpleUncaughtExceptionHandler() {
        ueh = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Logger.e(TAG, "uncaughtException:" + ex.getMessage());
//        tinkerFastCrashProtect();
//        tinkerPreVerifiedCrashHandler(ex);
        ueh.uncaughtException(thread, ex);
    }

//    /**
//     * Such as Xposed, if it try to load some class before we load from patch files.
//     * With dalvik, it will crash with "Class ref in pre-verified class resolved to unexpected implementation".
//     * With art, it may crash at some times. But we can't know the actual crash type.
//     * If it use Xposed, we can just clean patch or mention user to uninstall it.
//     */
//    private void tinkerPreVerifiedCrashHandler(Throwable ex) {
//        if (Utils.isXposedExists(ex)) {
//            //method 1
//            ApplicationLike applicationLike = TinkerManager.getTinkerApplicationLike();
//            if (applicationLike == null || applicationLike.getApplication() == null) {
//                return;
//            }
//
//            if (!TinkerApplicationHelper.isTinkerLoadSuccess(applicationLike)) {
//                return;
//            }
//            boolean isCausedByXposed = false;
//            //for art, we can't know the actually crash type
//            //just ignore art
//            if (ex instanceof IllegalAccessError && ex.getMessage().contains(DALVIK_XPOSED_CRASH)) {
//                //for dalvik, we know the actual crash type
//                isCausedByXposed = true;
//            }
//
//            if (isCausedByXposed) {
//                SampleTinkerReport.onXposedCrash();
//                TinkerLog.e(TAG, "have xposed: just clean tinker");
//                //kill all other process to ensure that all process's code is the same.
//                ShareTinkerInternals.killAllOtherProcess(applicationLike.getApplication());
//
//                TinkerApplicationHelper.cleanPatch(applicationLike);
//                ShareTinkerInternals.setTinkerDisableWithSharedPreferences(applicationLike.getApplication());
//            }
//        }
//    }
//
//    /**
//     * if tinker is load, and it crash more than MAX_CRASH_COUNT, then we just clean patch.
//     */
//    private boolean tinkerFastCrashProtect() {
//        ApplicationLike applicationLike = TinkerManager.getTinkerApplicationLike();
//
//        if (applicationLike == null || applicationLike.getApplication() == null) {
//            return false;
//        }
//        if (!TinkerApplicationHelper.isTinkerLoadSuccess(applicationLike)) {
//            return false;
//        }
//
//        final long elapsedTime = SystemClock.elapsedRealtime() - applicationLike.getApplicationStartElapsedTime();
//        //this process may not install tinker, so we use TinkerApplicationHelper api
//        if (elapsedTime < QUICK_CRASH_ELAPSE) {
//            String currentVersion = TinkerApplicationHelper.getCurrentVersion(applicationLike);
//            if (ShareTinkerInternals.isNullOrNil(currentVersion)) {
//                return false;
//            }
//
//            SharedPreferences sp = applicationLike.getApplication().getSharedPreferences(ShareConstants.TINKER_SHARE_PREFERENCE_CONFIG, Context.MODE_MULTI_PROCESS);
//            int fastCrashCount = sp.getInt(currentVersion, 0) + 1;
//            if (fastCrashCount >= MAX_CRASH_COUNT) {
//                SampleTinkerReport.onFastCrashProtect();
//                TinkerApplicationHelper.cleanPatch(applicationLike);
//                TinkerLog.e(TAG, "tinker has fast crash more than %d, we just clean patch!", fastCrashCount);
//                return true;
//            } else {
//                sp.edit().putInt(currentVersion, fastCrashCount).commit();
//                TinkerLog.e(TAG, "tinker has fast crash %d times", fastCrashCount);
//            }
//        }
//
//        return false;
//    }
}
