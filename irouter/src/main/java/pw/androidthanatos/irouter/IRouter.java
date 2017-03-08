package pw.androidthanatos.irouter;

import android.content.Context;
import android.os.Bundle;


import java.lang.ref.WeakReference;

import pw.androidthanatos.irouter.control.Interceptor;
import pw.androidthanatos.irouter.control.RouterLoader;

/**
 * Created by lxf52 on 2017/3/5.
 * 路由框架
 */

public class IRouter {

    private static final String DEFAULT_ACTION="test";
    private static final String DEFAULT_HOST="android";
    private static WeakReference<Context> contextWeakReference;
    private static String mAction;
    private RouterLoader mLoader;
    private Bundle mBundle;
    private Bundle mOptions;
    private int requestCode=-1;
    private Interceptor mInterceptor;
    private Context mContext;
    private static String mHost;

    private IRouter(){
       mContext=contextWeakReference.get();
    }

    public static class Builder{

        public static IRouter build( Context context){
            contextWeakReference=new WeakReference<>(context);
            return new IRouter();
        }
    }

    /**
     * 注册路由框架
     * @param action 在注册文件对目标类的标记
     *               eg: <action android:name="test"/>
     */
    public static void register( String action){
        if (action.isEmpty()){
            mAction=DEFAULT_ACTION;
        }else {
            mAction=action;
        }
        mHost=DEFAULT_HOST;

    }

    /**
     * 注册路由框架
     * @param action 在注册文件对目标类的标记
     *               eg: <action android:name="test"/>
     * @param host   用url进行跳转的host (可以使任意字母组成的字符串)
     *               eg: http 、 android 、 thanatos
     */
    public static void register( String action ,String host){
        if (action.isEmpty()){
            mAction=DEFAULT_ACTION;
        }else {
            mAction=action;
        }
        if (host.isEmpty()){
            mHost=DEFAULT_HOST;
        }else {
            mHost=host;
        }
    }

    /**
     * 打开对象界面或者服务
     * @param url  eg: android://activity/class/requestCode
     * @return
     * android://activity/class/requestCode
     * 其中：android： host
     *      activity： 类型
     *      class： 当前类名 也可以是别名(如果使用别名必须配置action)
     *              eg:类名：包名.Test    别名： test(只能指定给activity不能指定给service)
     *      requestCode： 可写在url后边也可以addRequestCode (访问其他界面的请求码)
     *
     */
    public IRouter openHref( String url){
        mLoader=new RouterLoader(mContext,mHost,mAction,mBundle,mOptions,mInterceptor);
        mLoader.openByHref(url);
        return this;
    }


    /**
     * 通过别名进行跳转 (用别名进行跳转的时候必须保证目标类在配置文件中设置了action)
     * @param name 别名
     * @return
     */
    public IRouter openName( String name){
        mLoader=new RouterLoader(mContext,mAction,mBundle,mOptions,requestCode,mInterceptor);
        mLoader.openByName(name);
        return this;
    }


    /**
     * 添加跳转到目标界面的参数
     * @param bundle
     * @return
     */
    public IRouter addBundle( Bundle bundle){
       this.mBundle=bundle;
        return this;
    }


    /**
     * android5.0以上添加跳转动画的共享元素等信息
     * @param option 共享元素等信息
     * @return
     */
    public IRouter addOption( Bundle option){
       this.mOptions=option;
        return this;
    }

    /**
     * 添加跳转到目标界面的 请求码
     * @param requestCode 请求码
     * @return
     */
    public IRouter addRequestCode( int requestCode){
       this.requestCode=requestCode;
        return this;
    }

    /**
     * 添加拦截器
     * @param interceptor 拦截器的实例
     * @return
     */
    public IRouter addInterrupter(Interceptor interceptor){
        this.mInterceptor=interceptor;
        return this;
    }


    /**
     * 解除路由框架注册
     */
    public static void unregister(){
        if (contextWeakReference!=null){
            if (contextWeakReference.get()!=null){
                contextWeakReference.clear();
            }
            contextWeakReference=null;
        }
    }

}
