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
 * 判断目标类（界面或者服务是否正在运行了）
 */

public class ClassRunning {
    private static final String TAG = "ClassRunning";

   public static boolean isRunning(Context context){

//       ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//       List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(Integer.MAX_VALUE/2);
//       for (ActivityManager.RunningTaskInfo run : runningTasks) {
//           Log.w(TAG, "isRunning: "+run.baseActivity.getClassName() );
//       }


       if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) { //For versions less than lollipop
           ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
           List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(10);
           for (ActivityManager.RunningTaskInfo run : taskInfo) {
                 Log.w(TAG, "isRunning: "+run.baseActivity.getClassName() );
             }
       }else{ //For versions Lollipop and above
           Intent intent = new Intent(
                   Settings.ACTION_USAGE_ACCESS_SETTINGS);
           context.startActivity(intent);
       }

       return false;
   }
}
