package pw.androidthanatos.irouter.control;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import pw.androidthanatos.irouter.anotation.Alias;
import pw.androidthanatos.irouter.utils.ClassType;
import pw.androidthanatos.irouter.utils.ClassUtils;
import pw.androidthanatos.irouter.utils.ServiceRunning;

/**
 * 处理路由框架信息
 */

public class RouterLoader {

    private static final String TAG = "RouterLoader";
    private Context mContext;
    private String mHost;
    private Bundle mBundle;
    private Bundle mOptions;
    private String mAction;
    private int requestCode;
    private Interceptor mInterceptor;
    public RouterLoader(Context context,  String mHost,String action, Bundle mBundle, Bundle mOptions, Interceptor
            mInterceptor) {
        this.mContext=context;
        this.mBundle=mBundle;
        this.mOptions=mOptions;
        this.mInterceptor=mInterceptor;
        this.mHost=mHost;
        this.mAction=action;
    }

    public RouterLoader( Context mContext,  String mAction, Bundle mBundle, Bundle mOptions, int requestCode, Interceptor mInterceptor) {
        this.mContext=mContext;
        this.mBundle=mBundle;
        this.mOptions=mOptions;
        this.mInterceptor=mInterceptor;
        this.mAction=mAction;
        this.requestCode=requestCode;
    }

    /**
     * android://activity/class/requestCode
     * 其中：android： host
     *      activity： 类型
     *      class： 当前类名 也可以是别名
     *      requestCode： 可写在url后边也可以addRequestCode (访问其他界面的请求码)
     * @param url 跳转信息
     */
    public void openByHref( String url) {
        if (isInterceptor())return;
        parseUrl(url);

    }

    /**
     * 解析url信息
     * @param url 跳转信息
     */
    private void parseUrl( String url) {
        if (url.isEmpty()) throw new NullPointerException("the url can not null");
        if (!url.contains(mHost+"://")) throw new IllegalArgumentException("are you sure the host is"+mHost);
        String[] strings = url.split("://");
        String datas = strings[1];
        if (datas.isEmpty()) throw new IllegalArgumentException(" url error");
        String[] split = datas.split("/");
        String type=split[0];
        String targetClassName=split[1];
        try{
           requestCode=Integer.parseInt(split[2]);
        }catch (ArrayIndexOutOfBoundsException e){
            requestCode=-1;
        }
        if (targetClassName.isEmpty()) throw new IllegalArgumentException("target class error");
        try {
            Class<?> targetClass = Class.forName(targetClassName);
           jump(type,targetClass);
        } catch (ClassNotFoundException e) {
            openByName(targetClassName);
        }
    }

    /**
     * 通过别名进行跳转
     * @param name 别名
     */
    public void openByName( String name) {
        if (isInterceptor())return;
        Class<?> targetClazz=null;
        Log.w(TAG, "openByName: "+mAction );
        List<Class> classList = ClassUtils.getClassList(mContext, mAction);
        if (classList.isEmpty()){
            Toast.makeText(mContext, "请确认Action设置是否正确或目标是否存在", Toast.LENGTH_SHORT).show();
        }
        for (Class<?> clazz:classList) {
            String value = bindType(clazz);
            if (value.equals(name)){
                targetClazz=clazz;
                break;
            }
        }
        if (targetClazz==null){
            Toast.makeText(mContext, "请确认Action设置是否正确或目标是否存在", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent=new Intent(mContext,targetClazz);
            intent.putExtra("bundle",mBundle);
            ClassType.Type type = ClassType.checkClassType(targetClazz);
            Log.w(TAG, "openByName: "+type );
            switch (type){
                case Activity:
                    ((Activity) mContext).startActivityForResult(intent,requestCode,mOptions);
                    break;
                case Service:
                    if (ServiceRunning.isRunning(mContext,targetClazz)){
                        Toast.makeText(mContext, "服务已经启动", Toast.LENGTH_SHORT).show();
                    }else {
                        mContext.startService(intent);
                    }

                    break;
                case BroadcastReceiver:
                    Toast.makeText(mContext, "不支持启动广播", Toast.LENGTH_SHORT).show();
                    break;
                case Obj:
                    Toast.makeText(mContext, "不支持启动普通类", Toast.LENGTH_SHORT).show();
                    break;
            }

        }



    }

    /**
     * 跳转到目标类
     * @param type 目标类类型
     * @param targetClass 目标类
     */
    private void jump(String type, Class<?> targetClass){
        Intent intent=new Intent(mContext,targetClass);
        intent.putExtra("bundle",mBundle);
        if (type.isEmpty()) throw new IllegalArgumentException("the url target error");
        switch (type){
            case "activity":
                Activity activity= (Activity) mContext;
                activity.startActivityForResult(intent,requestCode,mOptions);
                break;
            case "service":
                mContext.startService(intent);
                break;
            default:
                throw new IllegalArgumentException("the url target error");
        }
    }

    /**
     * 获取当前类是否有别名
     * @param clazz 目标类
     * @return 返回目标类的名字
     */
    private static String bindType(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Alias.class)){
            Alias modelType = clazz.getAnnotation(Alias.class);
            return modelType.value();
        }else {
            return "";
        }
    }

    /**
     * 是否拦截跳转
     * @return 返回是否拦截
     * true： 拦截
     * false： 不拦截
     */
    private boolean isInterceptor() {
        return mInterceptor != null && mInterceptor.isInterrupter();
    }
}
