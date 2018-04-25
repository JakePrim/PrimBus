package com.primbus;

import java.lang.reflect.Method;

public class SubscribeMethod {

    //标签
    private String label;

    //方法
    private Method method;

    //参数类型
    private Class[] paramClass;

    public SubscribeMethod(String label, Method method, Class[] paramClass) {
        this.label = label;
        this.method = method;
        this.paramClass = paramClass;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
