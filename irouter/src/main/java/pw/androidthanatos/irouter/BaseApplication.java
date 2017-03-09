package pw.androidthanatos.irouter;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.util.LogWriter;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

/**
 * Created on 2017/3/9.
 * 作者：by thanatos
 * 作用：Application基类
 */

public abstract class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks{

    private static final String TAG = "BaseApplication";

    private LinkedList<ActivityInfo> activityInfoLinkedList=new LinkedList<>();
    private static volatile BaseApplication INSTANCE;

    public static synchronized BaseApplication init(){
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        INSTANCE=this;
        initCrash();


    }

    public void initCrash(){
        new ICrashHandler(this, new ICrashHandler.ErrorListener() {

            /**
             * 对程序进行重启等操作
             */
            @Override
            public void doThing() {
                onCrashed();
            }

            /**
             * 对用户进行提醒
             *
             * @param ex
             */
            @Override
            public void doUIThing(Throwable ex) {
                onCrashedAlertDialog(ex);
            }

            /**
             * 保存错误信息或发送错误信息到服务器
             *
             * @param ex
             */
            @Override
            public void postInfo(Throwable ex) {
                onCrashedPostInfo(ex);
            }
        });
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            addActivity(activity);
    }

    private void addActivity(Activity activity) {
        if (null!=activity){
           activityInfoLinkedList.offerFirst(new ActivityInfo(activity));
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityInfo info = findActivity(activity);
        activityInfoLinkedList.remove(info);
    }

    private ActivityInfo findActivity(Activity activity) {
        if (null==activity)return null;
        for (ActivityInfo info: activityInfoLinkedList) {
            if (activity.equals(info.mActivity)){
                return info;
            }
        }
        return null;

    }

    /**
     * 退出当前程序
     */
    public void exit(){
        unregisterActivityLifecycleCallbacks(this);
        for (ActivityInfo info: activityInfoLinkedList) {
            if (null!=info && null!=info.mActivity){
                info.mActivity.finish();
            }
        }
        activityInfoLinkedList.clear();
        registerActivityLifecycleCallbacks(this);
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    private class ActivityInfo {
        Activity mActivity;
        ActivityInfo(Activity activity) {
            mActivity = activity;
        }
    }

    /**
     * 程序崩溃后的后续处理
     */
    public abstract void onCrashed();

    /**
     * 程序崩溃后对用户的提醒
     */
    public abstract void onCrashedAlertDialog(Throwable ex);

    /**
     * 保存或上传错误信息
     * @param ex
     */
    public abstract void onCrashedPostInfo(Throwable ex);
}
