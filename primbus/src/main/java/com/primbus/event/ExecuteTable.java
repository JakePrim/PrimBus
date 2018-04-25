package com.primbus.event;

/**
 * 执行表
 */
public class ExecuteTable {
    /**
     * 订阅者执行的方法
     */
    private SubscribeMethod subscribeMethod;

    /**
     * 订阅者的对象，通过对象反射来执行方法
     */
    private Object subscribeObject;

    public ExecuteTable(SubscribeMethod subscribeMethod, Object subscribeObject) {
        this.subscribeMethod = subscribeMethod;
        this.subscribeObject = subscribeObject;
    }

    public SubscribeMethod getSubscribeMethod() {
        return subscribeMethod;
    }

    public Object getSubscribeObject() {
        return subscribeObject;
    }
}
