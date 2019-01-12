package com.primbus.event;

import com.primbus.ThreadMethod;

import java.lang.reflect.Method;

public class SubscribeMethod {

    //标签
    private String tag;

    //方法
    private Method method;

    //参数类型
    private Class[] paramClass;

    private ThreadMethod threadMethod;

    public SubscribeMethod(String label, Method method, Class[] paramClass, ThreadMethod threadMethod) {
        this.tag = label;
        this.method = method;
        this.paramClass = paramClass;
        this.threadMethod = threadMethod;
    }

    public ThreadMethod getThreadMethod() {
        return threadMethod;
    }

    public void setThreadMethod(ThreadMethod threadMethod) {
        this.threadMethod = threadMethod;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class[] getParamClass() {
        return paramClass;
    }

    public void setParamClass(Class[] paramClass) {
        this.paramClass = paramClass;
    }
}
