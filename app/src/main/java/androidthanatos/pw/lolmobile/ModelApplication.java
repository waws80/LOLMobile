package androidthanatos.pw.lolmobile;

import android.util.Log;

import pw.androidthanatos.irouter.BaseApplication;
import pw.androidthanatos.irouter.IRouter;

/**
 * Created on 2017/3/8.
 * 作者：by thanatos
 * 作用：
 */

public class ModelApplication extends BaseApplication {
    private static final String TAG = "ModelApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        IRouter.register("lol","lol");
    }

    /**
     * 程序崩溃后的后续处理
     */
    @Override
    public void onCrashed() {
        Log.w(TAG, "onCrashed: 居然崩溃了------"+Thread.currentThread().getId() );
        exit();
        System.exit(1);

    }

    /**
     * 程序崩溃后对用户的提醒
     *
     * @param ex
     */
    @Override
    public void onCrashedAlertDialog(Throwable ex) {
        Log.w(TAG, "onCrashedAlertDialog: 居然崩溃了------"+Thread.currentThread().getId() );
        Log.w(TAG, "onCrashedAlertDialog: "+ex.toString() );
    }

    /**
     * 保存或上传错误信息
     *
     * @param ex
     */
    @Override
    public void onCrashedPostInfo(Throwable ex) {
        Log.w(TAG, "onCrashedPostInfo: 居然崩溃了------"+Thread.currentThread().getId() );
        Log.w(TAG, "onCrashedPostInfo: "+ex.toString() );
    }
}
