package androidthanatos.pw.lolmobile;

import android.app.Application;

import pw.androidthanatos.irouter.IRouter;

/**
 * Created on 2017/3/8.
 * 作者：by thanatos
 * 作用：
 */

public class ModelApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        IRouter.register("lol","lol");
    }
}
