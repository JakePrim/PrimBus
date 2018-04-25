package com.primbus;

import android.util.Log;

import com.primbus.event.ExecuteTable;
import com.primbus.event.SubscribeMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PrimBus {

    private static final String TAG = "PrimBus";
    /**
     * 订阅的方法缓存
     * 缓存Activity或Fragment类中的 订阅的方法
     */
    private Map<Class, List<SubscribeMethod>> METHOD_CACHE;

    /**
     * 执行方法的缓存
     * 缓存订阅的标签和订阅的方法，一个订阅标签下可能有多个订阅的方法
     */
    private Map<String, List<ExecuteTable>> EXECUTE_CACHE;

    private Map<Class, List<String>> UNREGISTER_CACHE;

    private static volatile PrimBus instance;

    public static PrimBus getDefault() {
        if (instance == null) {
            synchronized (PrimBus.class) {
                if (instance == null) {
                    instance = new PrimBus();
                }
            }
        }
        return instance;
    }

    private PrimBus() {
        METHOD_CACHE = new HashMap<>();
        EXECUTE_CACHE = new HashMap<>();
        UNREGISTER_CACHE = new HashMap<>();
    }

    /**
     * 注册订阅者
     */
    public void register(Object object) {
        Class<?> subscribeClass = object.getClass();
        //找到对应类中，所有的订阅者函数，并缓存到Map中。
        List<SubscribeMethod> subscribeMethods = findSubscribe(subscribeClass);

        //判断注销表中是否存储了 存储对应的class的标签
        List<String> labels = UNREGISTER_CACHE.get(subscribeClass);
        if (null == labels) {//如果没有存储将其存储起来
            labels = new ArrayList<>();
            UNREGISTER_CACHE.put(subscribeClass, labels);
        }
        //缓存要执行的方法
        for (SubscribeMethod subscribeMethod : subscribeMethods) {
            //拿到订阅者的标签
            String label = subscribeMethod.getLabel();
            if (!labels.contains(label)) {//如果不包含此标签 添加到labels
                labels.add(label);
            }
            //是否存在该标签下的执行表
            List<ExecuteTable> executeTables = EXECUTE_CACHE.get(label);
            // 如果不存在new一个
            if (null == executeTables) {
                executeTables = new ArrayList<>();
            }
            //
            ExecuteTable executeTable = new ExecuteTable(subscribeMethod, object);
            executeTables.add(executeTable);
            EXECUTE_CACHE.put(label, executeTables);
        }
    }

    /**
     * 发送事件给订阅者
     *
     * @param label  订阅者 注解的标签
     * @param params 参数
     */
    public void post(String label, Object... params) {
        // 拿到该订阅标签的 执行方法的列表
        List<ExecuteTable> executeTables = EXECUTE_CACHE.get(label);
        if (null == executeTables) {
            Log.e(TAG, "post: executeTables is null,没有订阅");
            return;
        }
        for (ExecuteTable executeTable : executeTables) {
            //获取订阅者执行的方法包装类
            SubscribeMethod subscribeMethod = executeTable.getSubscribeMethod();
            //获取订阅者的对象
            Object subscribeObject = executeTable.getSubscribeObject();
            //获取订阅者方法
            Method method = subscribeMethod.getMethod();
            //获取订阅者方法的参数类型数组
            Class[] paramClass = subscribeMethod.getParamClass();
            //组装的参数
            Object[] realParams = new Object[paramClass.length];
            if (null != params) {
                for (int i = 0; i < paramClass.length; i++) {
                    if (i < params.length && paramClass[i].isInstance(params[i])) {// 防止传参数过多 参数的类型相同
                        realParams[i] = params[i];
                    } else {
                        realParams[i] = null;
                    }
                }
            }
            try {
                //执行该方法
                method.invoke(subscribeObject, realParams);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 注销订阅者
     */
    public void unregister(Object object) {
        Class<?> subscribeClass = object.getClass();
        //拿到对应对象的所有对应的标签
        List<String> labels = UNREGISTER_CACHE.get(subscribeClass);
        if (null != labels) {
            for (String label : labels) {
                //获取执行表中对应的label的所有函数
                List<ExecuteTable> executeTables = EXECUTE_CACHE.get(label);
                if (null != executeTables) {
                    Iterator<ExecuteTable> iterator = executeTables.iterator();
                    while (iterator.hasNext()) {
                        ExecuteTable executeTable = iterator.next();
                        //判断对象是否是同一个  是才注销
                        Object subscribeObject = executeTable.getSubscribeObject();
                        if (subscribeObject == object) {
                            iterator.remove();
                        }
                    }
                }
            }
        }
    }

    /**
     *
     */
    public void clear() {
        UNREGISTER_CACHE.clear();
        METHOD_CACHE.clear();
        EXECUTE_CACHE.clear();
    }

    /**
     * 查找订阅者 在Activity 或者 Fragment 中(通过@Subscribe 注解的方法，拿到value[] 方法名 参数类型)
     *
     * @param subscribeClass
     * @return
     */
    private List<SubscribeMethod> findSubscribe(Class<?> subscribeClass) {
        List<SubscribeMethod> subscribeMethods = METHOD_CACHE.get(subscribeClass);
        //如果没有缓存,则通过反射获取类中所有的方法
        if (null == subscribeMethods) {
            subscribeMethods = new ArrayList<>();
            Method[] methods = subscribeClass.getDeclaredMethods();
            for (Method method : methods) {
                //设置权限
                method.setAccessible(true);
                //查找方法上有(@Subscribe)的方法
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if (null != subscribe) {
                    String[] values = subscribe.value();//拿到注解上面的值tag
                    //获取方法中参数的类型
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    for (String value : values) {
                        // 将包装的订阅者数据，添加到subscribeMethods中去
                        subscribeMethods.add(new SubscribeMethod(value, method, parameterTypes));
                    }
                }
            }
            METHOD_CACHE.put(subscribeClass, subscribeMethods);
        }
        return subscribeMethods;
    }

}
