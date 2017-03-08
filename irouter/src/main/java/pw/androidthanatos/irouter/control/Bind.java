package pw.androidthanatos.irouter.control;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import pw.androidthanatos.irouter.anotation.BindLayout;
import pw.androidthanatos.irouter.anotation.BindView;

/**
 * 绑定事件
 */

@SuppressWarnings("All")
public class Bind {

    public static void init(Context context){
        bindLayout(context);
        bindView(context);

    }

    /**
     * 初始化控件
     * @param context 上下文
     */
    private static void bindView(Context context) {
        Class<?> clazz = context.getClass();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field:fields) {
            if (field.isAnnotationPresent(BindView.class)){
                BindView bindView = field.getAnnotation(BindView.class);
                int viewId=bindView.value();
                try {
                    Method method = clazz.getMethod("findViewById", int.class);
                    View view = (View) method.invoke(context, viewId);
                    field.setAccessible(true);
                    field.set(context,view);
                } catch (NoSuchMethodException  e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     * 初始化布局
     * @param context 上下文
     */
    private static void bindLayout(Context context) {
        Class<?> clazz=context.getClass();
        if (clazz.isAnnotationPresent(BindLayout.class)){
            BindLayout annotation = clazz.getAnnotation(BindLayout.class);
            int resId = annotation.value();
            try {
                Method setContentView = clazz.getMethod("setContentView", int.class);
                setContentView.invoke(context,resId);
            } catch ( NoSuchMethodException  e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
