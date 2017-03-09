package pw.androidthanatos.irouter.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.util.List;

/**
 * Created by lxf52 on 2017/3/8.
 * 判断目标类（服务是否正在运行了）
 */

public class ServiceRunning {
    private static final String TAG = "ClassRunning";

   public static boolean isRunning(Context context,Class<?> clazz){

       ActivityManager am= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
       List<ActivityManager.RunningServiceInfo> runningServices = am.getRunningServices(100);
       for (ActivityManager.RunningServiceInfo service: runningServices ) {
           boolean started = service.started;
           String clazzName=clazz.getPackage().toString().replace(" ","").replace("package","")+"."+clazz
                   .getSimpleName();
           if (started && service.service.getClassName().equals(clazzName)){
               return true;
           }

       }


       return false;
   }
}
