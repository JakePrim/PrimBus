package com.primbus;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrimBus {
    private Map<Class, List<SubscribeMethod>> METHOD_CACHE;

    private static volatile PrimBus instance;

    public static PrimBus getInstance() {
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
    }

    /**
     * 注册订阅者
     */
    public void register(Object object) {
        Class<?> subscribeClass = object.getClass();
        //找到对应类中，所有的订阅者函数，并缓存到Map中。
        List<SubscribeMethod> subscribeMethods = findSubscribe(subscribeClass);
        //缓存要执行的方法
        for (SubscribeMethod subscribeMethod : subscribeMethods) {
            String label = subscribeMethod.getLabel();


            subscribeMethod.getParamClass();

        }
    }

    /**
     * 注销订阅者
     */
    public void unregister(Object object) {
        Class<?> subscribeClass = object.getClass();

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
