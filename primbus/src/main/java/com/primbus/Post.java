package com.primbus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/12 - 5:35 PM
 */
public class Post {
    private static final List<Post> POST_LIST = new ArrayList<>();
    public Method method;
    public Object subscribeObject;
    public Object[] realParams;

    public Post(Method method, Object subscribe, Object[] realParams) {
        this.method = method;
        this.subscribeObject = subscribe;
        this.realParams = realParams;
    }

    static Post obainPost(Method method, Object subscribe, Object[] realParams) {
        synchronized (POST_LIST) {
            int size = POST_LIST.size();
            if (size > 0) {
                Post pendingPost = POST_LIST.remove(size - 1);
                pendingPost.method = method;
                pendingPost.subscribeObject = subscribe;
                pendingPost.realParams = realParams;
                return pendingPost;
            }
        }
        return new Post(method, subscribe, realParams);
    }

    static void releasePendingPost(Post pendingPost) {
        pendingPost.method = null;
        pendingPost.subscribeObject = null;
        pendingPost.realParams = null;
        synchronized (POST_LIST) {
            // Don't let the pool grow indefinitely
            if (POST_LIST.size() < 10000) {
                POST_LIST.add(pendingPost);
            }
        }
    }

    public void invoke() throws InvocationTargetException, IllegalAccessException {
        if (method != null) {
            method.invoke(subscribeObject, realParams);
        }
    }
}
