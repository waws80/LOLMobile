package pw.androidthanatos.irouter.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.util.Log;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lxf52 on 2017/3/6.
 * class 工具类
 */

public class ClassUtils {
    private static final String TAG = "ClassUtils";

    /**
     * 获取对应action的类集合
     * @param context 上下文
     * @param action  对应类在配置文件配置的action
     * @return  集合
     */
    public static List<Class> getClassList(Context context, String action) {
        List<String> classList = new ArrayList<>();
        Intent intent = new Intent(action);
        Log.w(TAG, "getClassList: "+action );
        for (ResolveInfo activities : context.getPackageManager().queryIntentActivities(intent, 0)) {
            classList.add(activities.activityInfo.name);
        }
        for (ResolveInfo services : context.getPackageManager().queryIntentServices(intent, 0)) {
            classList.add(services.serviceInfo.name);
        }
        if (classList.isEmpty()) {
            return Collections.emptyList();
        } else {
            return string2Class(classList);
        }

    }

    /**
     * 将字符串转化为类对象
     *
     * @param list 字符串class 集合
     * @return 返回Class类型的集合
     */
    private static List<Class> string2Class(List<String> list) {
        List<Class> classList = new ArrayList<>();
        for (String s : list) {
            try {
                Class<?> name = Class.forName(s);
                if (name != null) {
                    classList.add(name);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        if (classList.isEmpty()) {
            return Collections.emptyList();
        } else {
            return classList;
        }
    }
}
