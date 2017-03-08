package pw.androidthanatos.irouter.utils;

import android.util.Log;

/**
 * Created by lxf52 on 2017/3/8.
 * 判断类的基本类型
 */

public class ClassType {

    private static String TAG="ClassType";


    /**
     * 类的类型
     */
    public enum Type{

        Activity, /*当前类为Activity*/
        Service,  /*当前类为Service*/
        BroadcastReceiver, /*当前类为Receiver*/
        Obj /*当前类为普通类*/
    }
    private static Type type;
    public static Type checkClassType(Class<?> clazz){

        Class<?> superclass = clazz.getSuperclass();
        Log.w(TAG, "checkClassType: "+superclass );

        if (superclass.getSimpleName().equals("Activity")||superclass.getSimpleName().equals("Service")
                ||superclass.getSimpleName().equals("BroadcastReceiver")){

            switch (superclass.getSimpleName()){
                case "Activity":
                    type= Type.Activity;
                    break;
                case "Service":
                    type=Type.Service;
                    break;
                case "BroadcastReceiver":
                    type=Type.BroadcastReceiver;
                    break;
                default:
                    type=Type.Obj;
                    break;
            }
            Log.w(TAG, "checkClassType:------------ "+type );
            return type;

        }else {
            if (superclass.getSuperclass()!=null){
                checkClassType(superclass);
            }else {
                return Type.Obj;
            }

        }
        return type;

    }
}
