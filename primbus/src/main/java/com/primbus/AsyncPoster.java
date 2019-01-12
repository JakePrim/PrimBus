package com.primbus;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/12 - 8:24 PM
 */
public class AsyncPoster implements Runnable, Poster {
    private ExecutorService executorService;

    private Post post;

    private static final String TAG = "AsyncPoster";

    public AsyncPoster() {
        executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void enqueue(Method method, Object Subscribe, Object[] realParams) {
        post = Post.obainPost(method, Subscribe, realParams);
        executorService.execute(this);
    }

    @Override
    public void run() {
        if (post != null) {
            try {
                post.invoke();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
