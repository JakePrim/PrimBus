package com.primbus;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/12 - 5:32 PM
 */
public class HandlerPoster extends Handler implements Poster {
    private Post post;

    private boolean handleActivite = false;

    @Override
    public void enqueue(Method method, Object Subscribe, Object[] realParams) {
        post = Post.obainPost(method, Subscribe, realParams);
        synchronized (this) {
            if (!handleActivite) {
                handleActivite = true;
                sendMessage(obtainMessage());
            }
        }
    }

    @Override
    public void handleMessage(Message msg) {
       if (post != null){
           try {
               post.invoke();
           } catch (InvocationTargetException e) {
               e.printStackTrace();
           } catch (IllegalAccessException e) {
               e.printStackTrace();
           }
       }
       handleActivite = false;
    }
}
