package com.primbus;

import java.lang.reflect.Method;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/12 - 5:28 PM
 */
public interface Poster {
    void enqueue(Method method, Object Subscribe, Object[] realParams);

}
