package pw.androidthanatos.irouter;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


/**
 * Created on 2017/3/9.
 * 作者：by thanatos
 * 作用：
 */

@SuppressWarnings("WrongConstant")
public class ICrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "ICrashHandler";

    private ErrorListener mExit;


    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    private Application mApplication;


    public ICrashHandler(Application application, ErrorListener exit) {
        this.mApplication=application;
        this.mExit=exit;
        //获取系统默认的UncaughtException处理器
        mDefaultExceptionHandler=Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    /**
     * Method invoked when the given thread terminates due to the
     * given uncaught exception.
     * <p>Any exception thrown by this method will be ignored by the
     * Java Virtual Machine.
     *
     * @param t the thread
     * @param e the exception
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {

        if (handleException(e)) {
            mExit.doThing();
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex 异常信息
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
        mExit.doUIThing(ex);
        mExit.postInfo(ex);
        return true;
    }

    interface ErrorListener{
        /**
         * 对程序进行重启等操作
         */
        void doThing();

        /**
         * 对用户进行提醒
         * @param ex
         */
        void doUIThing(Throwable ex);

        /**
         * 保存错误信息或发送错误信息到服务器
         * @param ex
         */
        void postInfo(Throwable ex);
    }
}
